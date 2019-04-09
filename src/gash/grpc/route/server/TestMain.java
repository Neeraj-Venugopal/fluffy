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


public class TestMain{
    private Server svr;
    public static int serverId;
    public static int portNumber;
    public static boolean isLeader = false;

    public static ArrayList<HashMap<String, String>> slaveServerStatusList = new ArrayList<HashMap<String, String>> ();

    public static void main(String[] args){
        try{
            if(args.length != 3){
                System.out.println("Setting Variables to Default Values");
                serverId = 1234;
                portNumber = 2345;
            }

            else{
                System.out.println("Assigning Server Configuration");
                serverId = Integer.parseInt(args[0]);
                portNumber = Integer.parseInt(args[1]);
                System.out.println(args[2]);
                if(args[2].equals("t") || args[2].equals("true")){
                    isLeader = true;
                }
                System.out.println(isLeader);
            }

           // FileOperationServer fs = new FileOperationServer();

            System.out.println("Server ID: " + serverId + "\t | Port Number: " + portNumber + "\t | Is Leader: " + isLeader) ;

            final TestMain impl = new TestMain();
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

        System.out.println("Inside the Start Method of the Server Main.java");
        
        // Logic to frequently check for a file present in root directory conf/serverIPAddress.txt
        // When There is a any change it checks if the data attained is a valid IP Address.
               svr = ServerBuilder.forPort(portNumber)
                           .addService(new TestFileStreamFromClient())
                           .addService(new TestDownloadFile())
                           .build();
        System.out.println("-- starting server");
        svr.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				TestMain.this.stop();
			}
		});        
    }

    protected void stop() {
		svr.shutdown();
    }

    private void blockUntilShutdown() throws Exception {
		svr.awaitTermination();
    }
}