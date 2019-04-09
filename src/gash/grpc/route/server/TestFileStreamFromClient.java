package gash.grpc.route.server;

import java.util.Iterator;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import fileservice.FileserviceGrpc.FileserviceImplBase;
import fileservice.FileserviceGrpc;
import fileservice.FileData;
import fileservice.ack;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;
import java.io.IOException;

import java.io.UnsupportedEncodingException;

public class TestFileStreamFromClient extends FileserviceImplBase{
    private static ManagedChannel ch;
    
        @Override
        public StreamObserver<FileData> uploadFile(final StreamObserver<ack> responseObserver){
            return new StreamObserver<FileData>(){
                
                @Override
                public void onNext(FileData filedata1) {
                  System.out.println(filedata1.getFilename());
                  System.out.println(filedata1.getUsername());
                  String byteDataStr="";
                  try{
                   byteDataStr = new String(filedata1.getData().toByteArray(), "UTF-8");

                  }catch(UnsupportedEncodingException e){
                    System.out.println(e);
                  }
                 // System.out.println(byteDataStr);
                 System.out.println( filedata1.getFilename() + " : " + filedata1.getSeq());
                }
        
                @Override
                public void onError(Throwable t) {
                  
                }
        
                @Override
                public void onCompleted() {
                  responseObserver.onNext(ack.newBuilder().setMessage("This is a test").setSuccess(true).build());
                  responseObserver.onCompleted();
                  //responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<FileData> updateFile(final StreamObserver<ack> responseObserver){
            return new StreamObserver<FileData>(){
                
                @Override
                public void onNext(FileData filedata1) {
                  System.out.println(filedata1.getFilename());
                  System.out.println(filedata1.getUsername());
                  String byteDataStr="";
                  try{
                   byteDataStr = new String(filedata1.getData().toByteArray(), "UTF-8");

                  }catch(UnsupportedEncodingException e){
                    System.out.println(e);
                  }
                 // System.out.println(byteDataStr);
                 System.out.println( filedata1.getFilename() + " : " + filedata1.getSeq());
                }
        
                @Override
                public void onError(Throwable t) {
                  
                }
        
                @Override
                public void onCompleted() {
                  responseObserver.onNext(ack.newBuilder().setMessage("This is a test").setSuccess(true).build());
                  responseObserver.onCompleted();
                  //responseObserver.onCompleted();
                }
            };
        }
}

