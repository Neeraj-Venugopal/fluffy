package gash.grpc.route.client;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
import fileservice.FileData;
import fileservice.FileData.Builder;
import fileservice.FileService;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.FileInfo;

import java.io.FileOutputStream;
import java.io.BufferedOutputStream;


import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.lang.Byte;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;

public class FileOperationDownloadClient {
    public static void main(String[] args) throws InterruptedException {
      //  final CountDownLatch done = new CountDownLatch(1);
  //Send Download Request to Super Node 
  ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.0.9", 9000).usePlaintext(true).build();
  
   //Send Download Request to Cluster Master Server ( Running on same machine as client during testing) 
  // ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 2345).usePlaintext(true).build();
  
  
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(channel);

        // String temp = "Download File";
        // byte[] fn = temp.getBytes();
      //  String filename = "Video Sample - 1GB.mp4";
      String filename = "";
        FileInfo bld = FileInfo.newBuilder().setUsername("bigtest").setFilename("bigtest.m4v").build();

        Iterator<FileData> features = stub.downloadFile(bld);
        while (features.hasNext()) {
          // process responses
          FileData filedat = features.next();
          String byteDataStr="";
          try{
         //   byteDataStr = new String(filedat.getData().toByteArray(), "UTF-8");
          try
	          {
		
	            	System.out.println("Saving to file");
			         BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("text.txt",true));
   		      	 out.write(filedat.getData().toByteArray());
                out.close();
		}
	catch(Exception E)
		{
			E.printStackTrace();
		}
     throw new UnsupportedEncodingException("Exception");
          }
          catch(UnsupportedEncodingException e){
            System.out.println("Unable to Read Payload");
          }
          System.out.println("reply: User Name: " + filedat.getUsername() + " | File Name: " + filedat.getFilename() + " | seq: " + filedat.getSeq() + " | Payload: " + byteDataStr);
        }
        channel.shutdown();
        // After Download the Client will exit.. It shutdowns the channel
    }
}    
