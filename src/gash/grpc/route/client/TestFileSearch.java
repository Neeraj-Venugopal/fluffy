package gash.grpc.route.client;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import fileservice.FileData;
import fileservice.FileserviceGrpc;
import fileservice.FileService;
import fileservice.FileInfo;
import fileservice.ack;

public class TestFileSearch{

    public static void main(String[] args){
    
        ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 2345).usePlaintext(true).build();
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(ch);
        //Heartbeat.NodeInfo.Builder k = Heartbeat.NodeInfo.newBuilder();

        FileInfo.Builder bld = FileInfo.newBuilder();
        bld.setFilename("30mbvideo.mp4");
        bld.setUsername("Test_User");

        ack testAck = stub.fileSearch(bld.build());
        System.out.println("Is Present " + testAck.getSuccess()); 
        System.out.println("Message From Server: " + testAck.getMessage());
    }
}
