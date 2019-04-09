package gash.grpc.route.client;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import heart.HeartBeatGrpc;
import heart.Heartbeat;
import heart.NodeInfo;
import heart.Stats;

import route.Route;
import route.RouteServiceGrpc;

public class HeartBeatClient{
    private static long clientID = 501;
    private static int port = 2345;

    public static void main(String[] args){
    
        ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", HeartBeatClient.port).usePlaintext(true).build();
        HeartBeatGrpc.HeartBeatBlockingStub stub = HeartBeatGrpc.newBlockingStub(ch);
        //Heartbeat.NodeInfo.Builder k = Heartbeat.NodeInfo.newBuilder();

        NodeInfo.Builder bld = NodeInfo.newBuilder();
        bld.setIp("localhost");
        bld.setPort(Integer.toString(HeartBeatClient.port));

        Stats testStats = stub.isAlive(bld.build());
        System.out.println("CPU Usage: " + testStats.getCpuUsage()); 
        System.out.println("Disk Space: " + testStats.getDiskSpace());
        System.out.println("RAM Memory: " + testStats.getUsedMem());
    }
}
