package gash.grpc.route.server;

import fileservice.FileData;
import fileservice.FileInfo;
import fileservice.UserInfo;
import fileservice.FileListResponse;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.FileserviceGrpc.FileserviceImplBase;

import gash.grpc.route.client.MasterToSlaveCommunication;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Base64;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObservers;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.google.protobuf.ByteString;

import fileservice.ClusterInfo;
import fileservice.ClusterStats;
import fileservice.Empty;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.FileserviceGrpc.FileserviceImplBase;
import java.util.Map;

import java.net.InetAddress;
import java.util.Random;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import fileservice.FileListResponse;

public class FileOperationServer extends FileserviceImplBase{
    private static ManagedChannel ch;
    private static boolean checkLeader = MainServer.isLeader;

    public static ArrayList<HashMap<String, String>> slaveList = new  ArrayList<HashMap<String, String>>();
    private static HashMap<String,Double> ip_cpu = new HashMap<String,Double>();
  	private static TreeMap<String, Double> sorted_ip_cpu = new TreeMap<String, Double>();
	  private static int primaryindex = 0;
	  private static int replicatedindex = 1;
    public static ArrayList<HashMap<String, String>> metaData = new  ArrayList<HashMap<String, String>>();
    HashMap<String, String> fileMetaData = new HashMap<String,String>();
    HashMap<String, String> downloadFileMetaData = new HashMap<String,String>();
    private static boolean shard_replicate = true;
   
 @Override
 public StreamObserver<FileData> uploadFile(final StreamObserver<ack> responseObserver){
    System.out.println("Upload File Functionality called in File Operation Server");
    return new StreamObserver<FileData>(){
        boolean meta = false;
        String primaryip = "", replicatedip ="";
        
        @Override
        public void onNext(FileData filedata1) {
            String byteDataStr="";
            try{
                //If Leader Server
                if(checkLeader == true){
                  //For First Sequence Sort IPs w.r.t Min. Utilization and create fileMetaData HashMap
                  if(filedata1.getSeq()==1)
                  {
                      slaveList = MainServer.slaveServerStatusList;
                      System.out.println("SlaveListSize: "+slaveList.size());
                      for(int i = 0 ; i<slaveList.size(); i++)
                      {
                        System.out.println("Slave List IP : " +slaveList.get(i).get("ipAddress") );
                        ip_cpu.put(slaveList.get(i).get("ipAddress"), Double.parseDouble(slaveList.get(i).get("cpuUsage")));
                      } 

                      sorted_ip_cpu = new TreeMap<String, Double>(ip_cpu);
                      if(sorted_ip_cpu.size()<=1)
                      shard_replicate = false;
                      else
                      shard_replicate = true;

                      fileMetaData = new HashMap<String,String>();
                      
                      fileMetaData.put("username", filedata1.getUsername());
                      fileMetaData.put("filename", filedata1.getFilename());
                      meta = true;
                  }
                  //For every other sequence retrieve existing fileMetaData
                  else { 
                    for(int i = 0; i<metaData.size();i++)
                    {
                      if(metaData.get(i).get("username").equals(filedata1.getUsername()) && metaData.get(i).get("filename").equals(filedata1.getFilename()))
                      {
                        fileMetaData = metaData.get(i);
                        break;
                      }
                    }
                    //Sharding for files >= 500mb
                    //Set Primary/Replicated Index for Selecting Primary/Secondary IP for Current Sequence of File
                    if (shard_replicate && filedata1.getSeq()%250 ==0)
                    {
                        System.out.println("Sorted Array Size: " + sorted_ip_cpu.size());

                        if(primaryindex + 1 == sorted_ip_cpu.size())
                          primaryindex = 0;
                        else
                          primaryindex++;

                        System.out.println("Primary index: " + primaryindex);
                        if(replicatedindex + 1 == sorted_ip_cpu.size())
                          replicatedindex = 0;
                        else
                          replicatedindex++;
                        
                        meta = true;
    
                    }
                  }

                  //Set Primary, Replicated IP to send to
                
                  primaryip = sorted_ip_cpu.keySet().toArray()[primaryindex].toString();
                  System.out.println("Primary ip: " + primaryip);
                  if(shard_replicate)
                        replicatedip = sorted_ip_cpu.keySet().toArray()[replicatedindex].toString();
                  
                  //Set primary and secondary ip for chunk in metadata
                  //Edit meta for each new chunk
                  if(meta)
                  {
                    int id;
                    if(shard_replicate)
                      id = filedata1.getSeq() / 250 + 1;
                    else 
                      id = 1;
                    fileMetaData.put("primaryip_"+id,primaryip);

                    if(shard_replicate)
                      fileMetaData.put("replicatedip_"+id,replicatedip);
                  }

                  System.out.println("Primary IP: "+ primaryip);
                  String reachPrimarySlave = MasterToSlaveCommunication.uploadFiles(filedata1.getFilename(), filedata1.getUsername(), primaryip.split(":")[0], Integer.parseInt(primaryip.split(":")[1]), filedata1.getSeq(), filedata1.getData());
                  String reachReplicatedSlave = "";
                  //If more than 1 slave ip is active - send to replicated ip
                  if (shard_replicate)
                     reachReplicatedSlave = MasterToSlaveCommunication.uploadFiles(filedata1.getFilename(), filedata1.getUsername(), replicatedip.split(":")[0], Integer.parseInt(replicatedip.split(":")[1]), filedata1.getSeq(), filedata1.getData());
                  
                  // String reachSlave = MasterToSlaveCommunication.uploadFiles(filedata1.getFilename(), filedata1.getUsername(), "10.192.168.20", 1234, filedata1.getSeq(), filedata1.getData());
                }
                else{
                  System.out.println("Upload File Called" + " | File Name: " + filedata1.getFilename() + " | User Name: " + filedata1.getUsername() + " | Sequence Number: " + filedata1.getSeq() + " | Destination IP: " + "localhost");
                  byte[] tempByte = filedata1.getData().toByteArray();
                  // byteDataStr = new String(filedata1.getData().toByteArray(), "UTF-8");
                  byteDataStr = Base64.getEncoder().encodeToString(tempByte);
                  // Data Base Operation to happen here.
                  JedisImplementation jedis = new JedisImplementation(byteDataStr, filedata1.getFilename(), filedata1.getUsername(), filedata1.getSeq());
                  jedis.setData();
                  // throw new UnsupportedEncodingException("Exception");
                  }
            }
                // }catch(UnsupportedEncodingException e){
                // System.out.println(e);
                // }
            catch(InterruptedException e){
            e.printStackTrace();
            }
            //System.out.println(byteDataStr);
        }
 
      @Override
      public void onError(Throwable t) {
      
      }
      
      @Override
      public void onCompleted() {
          metaData.add(fileMetaData);
          responseObserver.onNext(ack.newBuilder().setMessage("This is a test").setSuccess(true).build());
          responseObserver.onCompleted();
          //responseObserver.onCompleted();
      }
    };
 }  

    // Downlod File
    @Override
    public void downloadFile(fileservice.FileInfo request, io.grpc.stub.StreamObserver<fileservice.FileData> responseObserver){
      fileservice.FileData.Builder fileDataBuilder = FileData.newBuilder();

      // System.out.println("User Name: " + request.getUsername());
      // System.out.println("File Name: " + request.getFilename());
      if(checkLeader != true){
        System.out.println("Trying to read Database in the Slave Server");
        int seq = 0;
        JedisImplementation jedis = new JedisImplementation(request.getFilename(), request.getUsername());
        ArrayList<String> payload = jedis.getData();
        
        try{
          for(int i = 0; i < payload.size(); i++) {
            byte[] data = payload.get(i).getBytes();
            byte[] decoded = Base64.getDecoder().decode(data);

            BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream("Sample_File.mp4", true));
            buffer.write(decoded);
            buffer.close();

            // identifying sequence number
            seq++;
            // routing/header information
            fileDataBuilder.setFilename(request.getFilename());
            fileDataBuilder.setSeq(seq);
            fileDataBuilder.setData(ByteString.copyFrom(decoded));
           // System.out.println("Trying to send File");
           fileservice.FileData rtn = fileDataBuilder.build();
            try{

              Thread.sleep(10);
            }
            catch(InterruptedException e){
              e.printStackTrace();
            }
            responseObserver.onNext(rtn);
        } 
      }
      catch(Exception e){
        System.out.println("Exception while sending the data to the Master Server.");
        e.printStackTrace();
      }
      finally{
        responseObserver.onCompleted();
      }
    }
    else{
   
        System.out.println("Got a Download Request from Client, Passing on to Server");
          //Read MetaData to get which ip(s) to forward request tobyteDataStr
      String primaryip = "", replicatedip = "", selectedip = "";
      boolean sendToPrimary = false;
        System.out.println("Current MetaData Size:"+metaData.size());
      for(int i = 0; i<metaData.size();i++)
        {
            if(metaData.get(i).get("username").equals(request.getUsername()) && metaData.get(i).get("filename").equals(request.getFilename()))
            {
              System.out.println("Found File MetaData");
                downloadFileMetaData = metaData.get(i);
                break;
            }             
        }
      System.out.println("Size:"+downloadFileMetaData.size());
      
      if(downloadFileMetaData.size() == 0){
        System.out.println("File not Present");
      }
      
       for(int j = 1;j<=downloadFileMetaData.size()-2; j++)
       {
          //In downloadfilemetadata, get primaryip and replciatedip of each chunk and retrieve all sequences from that ip
          primaryip = downloadFileMetaData.get("primaryip_"+j);
          System.out.println("Primary IP Set: "+primaryip);

          replicatedip = downloadFileMetaData.get("replicatedip_"+j);
          System.out.println("Replicated IP Set: "+replicatedip);
          
           if(!primaryip.equals(null))
           {
              //Check if primary ip is active 
              //If active send request to primary, if not send request to replicated

              for(int i = 0; i<MainServer.slaveServerStatusList.size(); i++)
              {
                  if(MainServer.slaveServerStatusList.get(i).get("ipAddress").equals(primaryip))
                  {
                    selectedip = primaryip;
                    System.out.println("Will send request to primary ip: "+ selectedip);
                    sendToPrimary = true;
                    break;
                  }
              }

              if(!sendToPrimary && !replicatedip.equals(null))
              {
                selectedip = replicatedip;
                System.out.println("Will send request to replicated ip: "+ selectedip);
              }
 
              try{
                  int seq = 0;
                  
                  ArrayList<ByteString> payload = MasterToSlaveCommunication.downloadFiles(request.getFilename(), request.getUsername(), selectedip.split(":")[0],Integer.parseInt(selectedip.split(":")[1]));
                  
                  for(int i = 0; i < payload.size(); i++) {
                     seq++;
                    fileDataBuilder.setFilename(request.getFilename());
                    fileDataBuilder.setSeq(seq);
                    fileDataBuilder.setData(payload.get(i));

                    fileservice.FileData rtn = fileDataBuilder.build();
                    try{
                      Thread.sleep(1000);
                    }
                    catch(InterruptedException e){
                      e.printStackTrace();
                    }
                    responseObserver.onNext(rtn);
                  }

              }
            catch(Exception e){
                 e.printStackTrace();
                 System.out.println("Exception while returning from the Slave Server Call to Download File");
            }
           finally{
                 responseObserver.onCompleted();
           }
           }
      }
    }
  }

  //File Search
  @Override
  public void fileSearch(fileservice.FileInfo request, io.grpc.stub.StreamObserver<fileservice.ack> responseObserver){
   // ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 2345).usePlaintext(true).build();
   fileservice.ack.Builder acknowledgement = ack.newBuilder();
     // filedata.FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(channel);
      System.out.println("Trying to do a File Search");
      Boolean isPresent = false;
      String fileName = "";
      
        for (int i = 0; i < metaData.size(); i++) {
          fileName = metaData.get(i).get("filename");

          if (fileName == request.getFilename()) {
            isPresent = true;
            break;
          }
      
    }
    if(isPresent){
      responseObserver.onNext(acknowledgement.setMessage("File is present").setSuccess(true).build());
    }
    else{
      responseObserver.onNext(acknowledgement.setMessage("File is present").setSuccess(true).build());
    }
    
  }

  //File Delete
  @Override
  public void fileDelete(fileservice.FileInfo request, io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
    fileservice.ack.Builder acknowledgement = ack.newBuilder();

    System.out.println(
        "Trying to do Delete a file for User: " + request.getUsername() + " | File Name: " + request.getFilename());
    try {
      if (checkLeader == false) {
        System.out.println("Making a Jedis call to check the files");
        JedisImplementation jedis = new JedisImplementation(request.getFilename(), request.getUsername());
        Boolean isDelete = jedis.deleteFile();
        if (isDelete) {
          System.out.println("File is Deleted at the Slave Server Side");
          acknowledgement.setSuccess(true).setMessage("The File is Deleted");
          responseObserver.onNext(acknowledgement.build());
        } else {
          System.out.println("Could not Identify the file at the Slave Server Side");
          acknowledgement.setSuccess(false).setMessage("The File is not Present");
          responseObserver.onNext(acknowledgement.build());
        }
      } else {
        System.out.println("Leader is tyring to make the RPC call to Slave Server");

        String ipAddress1 = "";
        String replicateIp = "";
        String fileName = "";
        for (int i = 0; i < metaData.size(); i++) {
          fileName = metaData.get(i).get("filename");

          if (fileName == request.getFilename()) {
            metaData.remove(i);
            ipAddress1 = metaData.get(i).get("primaryIP_1");
          }
        }
        Boolean isDelete = false;
        if (ipAddress1 != "") {
          isDelete = MasterToSlaveCommunication.fileDeleteInSlave(request.getFilename(), request.getUsername(),
              ipAddress1.split(":")[0], Integer.parseInt(ipAddress1.split(":")[1]));
        }
        if (isDelete) {
          System.out.println("File is Present, Passing the Information to Client From Master Server Side");
          acknowledgement.setSuccess(true).setMessage("The File is Present");
          responseObserver.onNext(acknowledgement.build());
        } else {
          System.out.println("File is not Present in meta data file");
          acknowledgement.setSuccess(false).setMessage("The File is not Present");
          responseObserver.onNext(acknowledgement.build());
        }
      }
    } catch (Exception e) {
      System.out.println("Exception Occurred");
      e.printStackTrace();
    } finally {
      responseObserver.onCompleted();
    }
  }

  //File List
  @Override
  public void fileList(fileservice.UserInfo request,
      io.grpc.stub.StreamObserver<fileservice.FileListResponse> responseObserver) {
        fileservice.FileListResponse.Builder fileListResponse = FileListResponse.newBuilder();

    System.out.println("Trying to do List all files for a User: " + request.getUsername());
    try {
      
      System.out.println("Leader is tyring to make the RPC call to Slave Server");

      String userName = "";
      String fileName = "";
      for (int i = 0; i < metaData.size(); i++) {
        if (userName == metaData.get(i).get("username")) {
          fileName += metaData.get(i).get("filename");
          fileName += ",";
        }
      }
      Boolean isList = false;
      if (fileName != "") {
        System.out.println("No Files for the User in meta data file");
        fileListResponse.setFilenames("");
        responseObserver.onNext(fileListResponse.build());
      } else {
        System.out.println("Files Present");
        fileListResponse.setFilenames(fileName);
        responseObserver.onNext(fileListResponse.build());
      }
      // }
    } catch (Exception e) {
      System.out.println("Exception Occurred");
      e.printStackTrace();
    } finally {
      responseObserver.onCompleted();
    }
  }


  //Cluster Stats

  @Override
  public void getClusterStats(fileservice.Empty request,
      io.grpc.stub.StreamObserver<fileservice.ClusterStats> responseObserver) {
    System.out.println("Providing Cluster Stats to the Super Node");

    SystemInfo sysInfo = new SystemInfo();
    Map<String, Double> systemInfo = sysInfo.getSystemInfo("localhost");

    fileservice.ClusterStats.Builder stat = ClusterStats.newBuilder();

    stat.setCpuUsage(Double.toString(systemInfo.get("cpuUtilization")));
    stat.setDiskSpace(Double.toString(systemInfo.get("diskSpace")));
    stat.setUsedMem(Double.toString(systemInfo.get("memoryUsed")));
    fileservice.ClusterStats rtn = stat.build();
    System.out.println("Sending Stats of Cluster");
    responseObserver.onNext(rtn);
    responseObserver.onCompleted();
  }

  // public void sendLeaderInfo() {
  //   System.out.println("Get Leader Info Called");

  //   InetAddress localhost;
  //   String ipAddress = "";
  //   try {
  //     localhost = InetAddress.getLocalHost();
  //     ipAddress = localhost.getHostAddress().trim();
  //   } catch (Exception e) {

  //   }

  //   ManagedChannel ch = ManagedChannelBuilder.forAddress("192.168.0.5", 9000).usePlaintext(true).build();
  //   FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);
  //   // HeartBeatGrpc.HeartBeatBlockingStub stub = HeartBeatGrpc.newBlockingStub(ch);
  //   // Heartbeat.NodeInfo.Builder k = Heartbeat.NodeInfo.newBuilder();

  //   ClusterInfo.Builder bld = ClusterInfo.newBuilder();
  //   // NodeInfo.Builder bld = NodeInfo.newBuilder();
  //   bld.setIp(ipAddress);
  //   bld.setPort("2345");

  //   int randomInt = new Random().nextInt(100);
  //   bld.setClusterName("Team_7_" + randomInt);

  //   ack acknowledgement = stub.getLeaderInfo(bld.build());
  //   System.out.println("Acknowledgment Success is: " + acknowledgement.getSuccess());
  //   System.out.println("Acknowledgement  message is: " + acknowledgement.getMessage());
  // }

  // Update Not Implemented
  @Override
  public void getLeaderInfo(fileservice.ClusterInfo request, io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
    System.out.println("Trying to call Super Node");
    // // System.out.println(request.get)
    //  filedata.ClusterInfo.Builder clusterNode = ClusterInfo.newBuilder();
    //  clusterNode.setIp("192.168.0.51");
    //  clusterNode.setClusterName("Team_7");
    //  clusterNode.setPort("2345");

    //  filedata.ClusterInfo acknowledgement = clusterNode.build();


    //  responseObserver.onNext(acknowledgement);
    //  responseObserver.onCompleted();
  }
}
