package gash.grpc.route.client;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import fileservice.*;
import fileservice.ClusterInfo;
import fileservice.ClusterStats;
import fileservice.Empty;

public class GetMainServerStatus{


    public static void main(String[] args){
        ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 2345).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);

        fileservice.Empty.Builder bld = Empty.newBuilder();
        ClusterStats testStats = stub.getClusterStats(bld.build());
        System.out.println("CPU Usage: " + testStats.getCpuUsage()); 
        System.out.println("Disk Space: " + testStats.getDiskSpace());
        System.out.println("RAM Memory: " + testStats.getUsedMem());
    }
}
