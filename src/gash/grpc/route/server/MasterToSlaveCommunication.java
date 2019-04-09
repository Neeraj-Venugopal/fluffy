package gash.grpc.route.client;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
import fileservice.FileData;
import fileservice.FileInfo;
import fileservice.FileData.Builder;
import fileservice.FileService;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.UserInfo;
import fileservice.FileListResponse;

import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.lang.Byte;
import java.io.UnsupportedEncodingException;


public class MasterToSlaveCommunication{

 //   public static Boolean done1 = false;
    public MasterToSlaveCommunication(){
        System.out.println("Trying to make connection from Master Server to the Associated Slave Servers");
    }

    public static String uploadFiles(String fileName, String userName, String ip, int portNumber, int sequenceNumber, ByteString streamData) throws InterruptedException{
        System.out.println("Inside The upload Files of Master to Execute Slave Run");
        System.out.println("Upload File Called" + " | File Name: " + fileName + " | User Name: " + userName + " | Sequence Number: " + sequenceNumber + " | Destination IP: " + ip);
        
        final CountDownLatch done = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, portNumber).usePlaintext(true).build();
        //FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(channel);
        FileserviceGrpc.FileserviceStub stub1 = FileserviceGrpc.newStub(channel);

        ClientResponseObserver<FileData, ack> testObserver= new ClientResponseObserver<FileData, ack>() {
            ClientCallStreamObserver<FileData> requestStream;

            @Override
            public void beforeStart(ClientCallStreamObserver<FileData> requestStream) {
                this.requestStream = requestStream;
                requestStream.setOnReadyHandler(new Runnable() {

                    @Override
                    public void run() { 
                        System.out.println("Trying to Send to Slave Server");
                        while (requestStream.isReady()) {
                            FileData request = FileData.newBuilder()
                                                        .setFilename(fileName)
                                                        .setUsername(userName)
                                                        .setSeq(sequenceNumber)
                                                        .setData(streamData).build();
                            requestStream.onNext(request);

                            /*if(fis.available() > 0){
                                done = false;
                            }else{
                                done = true;
                            }*/
                       //     done1 = true;
                        }
                        requestStream.onCompleted();
                    }
                });
            }
            
            @Override
            public void onCompleted() {
                System.out.println("Completed Streaming To Slave for Sequence " + sequenceNumber);
                done.countDown();
            }

            @Override
            public void onError(Throwable t) {
              //  t.printStackTrace();
                done.countDown();
            }

            @Override
            public void onNext(ack value) {
                System.out.println("Reply From Slave Server is: " + value.getMessage());
                System.out.println("Completed Transfer to Slave" + " | File Name: " + fileName + " | User Name: " + userName + " | Sequence Number: " + sequenceNumber + " | Destination IP: " + ip);
                requestStream.request(1);
            }
        };
       
        fileservice.FileData.Builder bld = FileData.newBuilder();
        stub1.uploadFile(testObserver);
        done.await();
        done.await();
        done.await();
        //channel.shutdown();
       // channel.awaitTermination(1, TimeUnit.SECONDS);
        return "Message From Slave Server: Upload Files";
    }

    public static boolean fileSearchInSlave(String fileName, String userName, String ip, int portNumber) throws InterruptedException{
        System.out.println("Making a Request to Search in Slave Server From Master");
        ManagedChannel ch = ManagedChannelBuilder.forAddress(ip, portNumber).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);

        FileInfo.Builder bld = FileInfo.newBuilder();
        bld.setFilename(fileName);
        bld.setUsername(userName);

        ack acknowledgement = stub.fileSearch(bld.build());
        System.out.println("File Search Response From Slave Server:" + acknowledgement.getSuccess());
        return acknowledgement.getSuccess();
    }

    public static boolean fileDeleteInSlave(String fileName, String userName, String ip, int portNumber) throws InterruptedException{
        System.out.println("Making a Request to Delete in Slave Server From Master");
        ManagedChannel ch = ManagedChannelBuilder.forAddress(ip, portNumber).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);

        FileInfo.Builder bld = FileInfo.newBuilder();
        bld.setFilename(fileName);
        bld.setUsername(userName);

        ack acknowledgement = stub.fileDelete(bld.build());
        System.out.println("File Delete Response From Slave Server: " + acknowledgement.getSuccess());
        return acknowledgement.getSuccess();
    }

    public static String listAllFilesForAUser(String userName, String ip, int portNumber) throws InterruptedException{
        System.out.println("Making a Request to List All Files in Slave Server From Master");
        ManagedChannel ch = ManagedChannelBuilder.forAddress(ip, portNumber).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);

        UserInfo.Builder bld = UserInfo.newBuilder();
        bld.setUsername(userName);

        FileListResponse fileResponse = stub.fileList(bld.build());

        System.out.println("File Delete Response From Slave Server: " + fileResponse.getFilenames());
        return fileResponse.getFilenames();
    }

    public static ArrayList<ByteString> downloadFiles(String fileName, String userName, String ip, int portNumber) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, portNumber).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(channel);

        FileInfo bld = FileInfo.newBuilder().setUsername(userName).setFilename(fileName).build();
        ArrayList<ByteString> payload = new ArrayList<ByteString>();
        Iterator<FileData> features = stub.downloadFile(bld);
        while (features.hasNext()) {
          // process responses
          FileData filedat = features.next();
          String byteDataStr="";
          try{
            byteDataStr = new String(filedat.getData().toByteArray(), "UTF-8");
            payload.add(filedat.getData());
          }
          catch(UnsupportedEncodingException e){
            System.out.println("Unable to Read Payload");
          }
          System.out.println("reply: User Name: " + filedat.getUsername() + " | File Name: " + filedat.getFilename() + " | seq: " + filedat.getSeq() + " | Payload: " + byteDataStr);
        }
        channel.shutdown();
        return payload;
    }
}