package gash.grpc.route.server;

import java.io.IOException;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import jdk.internal.dynalink.linker.LinkerServices.Implementation;
import heart.HeartBeatGrpc;
import heart.Heartbeat;
import heart.NodeInfo;
import heart.Stats;
import heart.HeartBeatGrpc.HeartBeatImplBase;
import io.grpc.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeartBeatServer extends HeartBeatImplBase{

    public String sourceIPAddress;
    public String destIpAddress;
    public int portNumber;
    public static ArrayList<HashMap<String, String>> slaveList;

    HeartBeatServer(String destIpAddress, int portNumber){
        System.out.println("Created Object for Heart Beat Server");
        this.destIpAddress = destIpAddress;
        this.portNumber = portNumber;
    }

    @Override
    public void isAlive(heart.NodeInfo request, io.grpc.stub.StreamObserver<heart.Stats> responseObserver){
        System.out.println("Checking Heart Beat");
       // System.out.println(request.get)
        SystemInfo sysInfo = new SystemInfo();
        Map <String, Double> systemInfo = sysInfo.getSystemInfo(this.sourceIPAddress);
        heart.Stats.Builder stat = Stats.newBuilder();

        stat.setCpuUsage(Double.toString(systemInfo.get("cpuUtilization")));
        stat.setDiskSpace(Double.toString(systemInfo.get("diskSpace")));
        stat.setUsedMem(Double.toString(systemInfo.get("memoryUsed")));
        heart.Stats rtn = stat.build();
        responseObserver.onNext(rtn);
        responseObserver.onCompleted();
    }
}
