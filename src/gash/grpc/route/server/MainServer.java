package gash.grpc.route.server;

import java.io.IOException;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import io.grpc.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.BufferedReader; 
import java.io.FileReader;

import heart.HeartBeatGrpc;
import heart.Heartbeat;
import heart.NodeInfo;
import heart.Stats;

import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.ClusterInfo;

import java.net.InetAddress;
import java.util.Random;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import fileservice.FileListResponse;


public class MainServer{
    private Server svr;
    public static int serverId;
    private static int portNumber = 2345;
    private ArrayList <String> connections = new ArrayList<String>();
    private ArrayList <Integer> portList = new ArrayList<Integer>();
    private ArrayList <String> invalidConnections = new ArrayList<String>();
	private static long clientID = 501;
//	private static int port = 2345;
	private static int count = 0;
	private static byte [] data = new byte[1024];
	public static boolean isLeader = false;
	private static String leaderIp = "";
	private static ByteArrayOutputStream output = new ByteArrayOutputStream();
	private static byte [] test = new byte[1024];
	private static int countFile = 0;
	private static List<byte[]> filecontents = new ArrayList<byte[]>();
    private static String jedisKey = "";
    
    public static ArrayList<HashMap<String, String>> slaveServerStatusList = new ArrayList<HashMap<String, String>> ();

    public static void main(String[] args){
        try{
            if(args.length == 2){
                System.out.println("Setting Variables to Default Values");
                serverId = 1234;
            }

            else{
                System.out.println("Assigning Server Configuration");
                serverId = Integer.parseInt(args[0]);
                if(args[1].equals("t") || args[1].equals("true")){
                    isLeader = true;
                }
                if(!args[2].isEmpty())
                    portNumber = Integer.parseInt(args[2]);
               // System.out.println(isLeader);
            }

           // FileOperatioportListnServer fs = new FileOperationServer();

            System.out.println("Server ID: " + serverId + "\t | Port Number: " + portNumber + "\t | Is Leader: " + isLeader) ;

            final MainServer impl = new MainServer();
            impl.start();
            impl.blockUntilShutdown();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void start() throws Exception{

        System.out.println("Inside the Start Method of the Server");

        // Logic to frequently check for a file present in root directory conf/serverIPAddress.txt
        // When There is a any change it checks if the data attained is a valid IP Address.
        Timer timer = new Timer();
		TimerTask task = new NetworkFileChecker(new File("conf/serverIPAddressPool.txt")){
			protected void fileChanged(File file){
				if(isLeader){
					System.out.println("File serverIPAddressPool has changed, Hence Trigerring a new Server Connection");
					try{
						BufferedReader reader = new BufferedReader(new FileReader(file));
						String line = "";
						//Check if its empty
						while((line = reader.readLine())!=null){
							if(!connections.contains(line) && !invalidConnections.contains(line)){
                                String temp = line;
                                String parts[] = line.split("\\.");
                               // System.out.println(parts.length);
                                
                                
                                connections.add(temp);
                              
                                // if(parts.length == 4){
                                //     System.out.println("Added IP Address to the Connections List: " + temp);
                                //     connections.add(temp);
                                // }
                                // else{
                                //     System.out.println("Invalid Ip Address: " + temp);
                                //     invalidConnections.add(temp);
                                // }
							}
						}
						reader.close();
					}
					catch(Exception e){
						System.out.println("Exception");
						e.printStackTrace();
					}
                }
                
                if(connections.size() == 0){
                    System.out.println("There are no Slave Servers Connected to the Leader Server");
                }
			}
		};
		timer.schedule(task,0,1000);
        task.run();
        
        Timer timer1 = new Timer();
        TimerTask task1 = new CheckHeartBeat(){
        
            @Override
            protected void runHeartBeat() {
                if(isLeader == true){
                    if(connections.size() > 0 ){
                    loop1:  for(int i = 0; i < connections.size(); i++){
                            try{
                                ManagedChannel ch = ManagedChannelBuilder.forAddress(connections.get(i).split(":")[0], Integer.parseInt(connections.get(i).split(":")[1])).usePlaintext(true).build();
                                HeartBeatGrpc.HeartBeatBlockingStub stub = HeartBeatGrpc.newBlockingStub(ch);
                                NodeInfo.Builder bld = NodeInfo.newBuilder();
                                bld.setIp(connections.get(i).split(":")[0]);
                                bld.setPort(Integer.toString(portNumber));
                                 bld.setPort(connections.get(i).split(":")[1]);
                                Stats testStats = stub.isAlive(bld.build());
                                System.out.println("From IP Address: " + connections.get(i)  +
                                                "CPU Usage: " + testStats.getCpuUsage()   + 
                                                "Disk Space: " + testStats.getDiskSpace() +
                                                "RAM Memory: " + testStats.getUsedMem());
                                HashMap<String, String> newHashMap = new HashMap<String, String>();
                                newHashMap.put("ipAddress", connections.get(i));
                                newHashMap.put("diskSpace", testStats.getCpuUsage());
                                newHashMap.put("cpuUsage", testStats.getDiskSpace());
                                newHashMap.put("ramMemory", testStats.getUsedMem());

                                if(slaveServerStatusList.size() == 0){
                                    slaveServerStatusList.add(newHashMap);
                                }
                                else{
                                    boolean checkLoop = false;
                        loop2:      for(int k = 0; k < slaveServerStatusList.size(); k++){
                                        if( slaveServerStatusList.get(k).get("ipAddress") == newHashMap.get("ipAddress")){
                                            System.out.println("Updating Slave Server Status List for IP: " + newHashMap.get("ipAddress"));
                                            checkLoop = true;
                                            slaveServerStatusList.set(k, newHashMap);
                                            break loop2;
                                        }
                                    }
                                       if(checkLoop == false)
                                        slaveServerStatusList.add(newHashMap);

                                }

                                System.out.println("Printing From Array List: " + slaveServerStatusList.get(0).get("diskSpace"));
                            }
                            catch(Exception e){
                                System.out.println("Exception Occurred while making a Heart Beat Request to : " + connections.get(i));
                                System.out.println("Removing IP Address from Connection Array List: " + connections.get(i));
                                connections.remove(i);
                            }
                        }
                    }
                    else{
                        System.out.println("There are no Connections to the Master Server");
                    }
                    try{
                        System.out.println("Get Leader Info Called");
                        // InetAddress localhost;
                        // String ipAddress = "";
                        // try {
                        // localhost = InetAddress.getLocalHost();
                        // ipAddress = localhost.getHostAddress().trim();
                        // } catch (Exception e) {
    
                        // }
    
                        ManagedChannel ch = ManagedChannelBuilder.forAddress("192.168.0.9", 9000).usePlaintext(true).build();
                        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);
                        // HeartBeatGrpc.HeartBeatBlockingStub stub = HeartBeatGrpc.newBlockingStub(ch);
                        // Heartbeat.NodeInfo.Builder k = Heartbeat.NodeInfo.newBuilder();
    
                        ClusterInfo.Builder bld = ClusterInfo.newBuilder();
                        // NodeInfo.Builder bld = NodeInfo.newBuilder();
                        bld.setIp("192.168.0.51");
                        bld.setPort("2345");
    
                        int randomInt = new Random().nextInt(100);
                        bld.setClusterName("Team_7_" + randomInt);
    
                        ack acknowledgement = stub.getLeaderInfo(bld.build());
                        System.out.println("Acknowledgment Success is: " + acknowledgement.getSuccess());
                        System.out.println("Acknowledgement  message is: " + acknowledgement.getMessage());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Super Node Not yet connected");
                    }
    
                }

                
            }
        };
        timer1.schedule(task1,0,4000);
        task1.run();
        
        svr = ServerBuilder.forPort(portNumber)
                           .addService(new HeartBeatServer("localhost", portNumber))
                           .addService(new FileOperationServer())
                           .build();
        System.out.println("-- starting server");
        svr.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				MainServer.this.stop();
			}
		});        
    }

    protected void stop() {
		svr.shutdown();
    }

    private void blockUntilShutdown() throws Exception {
		svr.awaitTermination();
    }

    public ArrayList<HashMap<String, String>> getSlaveList(){
        return slaveServerStatusList;
    }
}