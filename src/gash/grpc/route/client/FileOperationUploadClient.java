package gash.grpc.route.client;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;
//import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
import fileservice.FileData;
import fileservice.FileData.Builder;
import fileservice.FileService;
import fileservice.FileserviceGrpc;
import fileservice.ack;
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
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Random;

import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;
import fileservice.ack;

public class FileOperationUploadClient {
    public static int globalFile = 1;
    private static BufferedInputStream in;
    public static int count = 1;
    public static int chunks = 1;
    public static int counter = 1;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch done = new CountDownLatch(1);
         //Send Upload Request to Super Node 
       ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.0.9", 9000).usePlaintext(true).build();
 
     //Send Upload Request to Cluster Master Server ( Running on same machine as client during testing)
   // ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 2345).usePlaintext(true).build();
    
        FileserviceGrpc.FileserviceBlockingStub stub = FileserviceGrpc.newBlockingStub(channel);

        FileserviceGrpc.FileserviceStub stub1 = FileserviceGrpc.newStub(channel);

        ClientResponseObserver<FileData, ack> testObserver= new ClientResponseObserver<FileData, ack>() {
            ClientCallStreamObserver<FileData> requestStream;

            @Override
            public void beforeStart(ClientCallStreamObserver<FileData> requestStream) {
                this.requestStream = requestStream;


                requestStream.setOnReadyHandler(new Runnable() {

                    @Override
                    public void run() { 
                        while (requestStream.isReady()) {
                            System.out.println("Inside requeststream while loop");
                            FileInputStream fis = null;
                            File file =new File("src/gash/grpc/route/client/fileSamples/test.txt");
                            try{
                                fis = new FileInputStream(file);
                                // Reading 2 mb at a time
                                final int blen = 1024 * 1024 * 2;
                                int seq = 0;
                                byte[] raw = new byte[blen];
                                boolean done = false;

                                while(!done){
                                    int n = fis.read(raw, 0, blen);
                                    if(n <= 0) {
                                        break;
                                    }
                                    seq++;
                                    count++;
                                    System.out.println("Sequence Number is:" + seq);
                                    FileData request = FileData.newBuilder()
                                                                .setFilename(file.getName())
                                                                .setUsername("Team_7")
                                                                .setSeq(seq)
                                                                .setData(ByteString.copyFrom(raw, 0, n)).build();
                                                                                                                                
                                                            // .setFilename("test.txt")
                                    requestStream.onNext(request);
                                    try
                                    {
                                        Thread.sleep(1000);
                                    }
                                    catch(InterruptedException E)
                                    {

                                    }
                                    /*if(fis.available() > 0){
                                        done = false;
                                    }else{
                                        done = true;
                                    }*/
                                }
                                requestStream.onCompleted();
                            }
                            catch(Exception e){

                            }
                            finally{
                                try {
                                    fis.close();
                                } catch (IOException e) {
                                    ; // ignore
                                }

                            }

                        }
                    }
                });
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
                System.out.println("Count Value is: " + count);
                done.countDown();
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                done.countDown();
            }

            @Override
            public void onNext(ack value) {
                System.out.println("Reply is: " + value.getMessage());
                requestStream.request(1);

            }
        };
       
        fileservice.FileData.Builder bld = FileData.newBuilder();
      //  for(int i = 1; i<=100; i++)
       // {
             stub1.uploadFile(testObserver);
           //  counter++;
       // }
       // stub1.uploadFile(testObserver);
       // stub1.uploadFile(testObserver);
        done.await();
        channel.shutdown();
        channel.awaitTermination(1, TimeUnit.SECONDS);
    }
}