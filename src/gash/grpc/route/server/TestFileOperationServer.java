package gash.grpc.route.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import fileservice.FileData;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.FileserviceGrpc.FileserviceImplBase;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class TestFileOperationServer{

    public static void main(String []args) throws IOException, InterruptedException {

        FileserviceGrpc.FileserviceImplBase svc = new FileserviceImplBase() {

            @Override
            public StreamObserver<FileData> uploadFile(final StreamObserver<ack> responseObserver){
                final ServerCallStreamObserver<ack> serverCallStreamObserver = (ServerCallStreamObserver<ack>) responseObserver;
                serverCallStreamObserver.disableAutoInboundFlowControl();

                final AtomicBoolean wasReady = new AtomicBoolean(false);


                serverCallStreamObserver.setOnReadyHandler(new Runnable(){
                    public void run(){
                        if(serverCallStreamObserver.isReady() && wasReady.compareAndSet(false, true)){
                            System.out.println("Ready");

                            serverCallStreamObserver.request(1);
                        }
                    }
                });

                return new StreamObserver<FileData>() {

					@Override
					public void onCompleted() {
                        System.out.println("Completed");
						responseObserver.onCompleted();
					}

					@Override
					public void onError(Throwable t) {
                        t.printStackTrace();
                        responseObserver.onCompleted();
						
					}

					@Override
					public void onNext(FileData request) {
                        try {
                            // Accept and enqueue the request.
                            String name = request.getFilename();
                            System.out.println("--> " + name);
              
                            // Simulate server "work"
                            Thread.sleep(100);
              
                            // Send a response.
                            String message = "Hello " + name;
                            System.out.println("<-- " + message);
                            ack reply = ack.newBuilder().setMessage(message).setSuccess(true).build();
                            responseObserver.onNext(reply);
              
                            // Check the provided ServerCallStreamObserver to see if it is still ready to accept more messages.
                            if (serverCallStreamObserver.isReady()) {
                              // Signal the sender to send another request. As long as isReady() stays true, the server will keep
                              // cycling through the loop of onNext() -> request()...onNext() -> request()... until either the client
                              // runs out of messages and ends the loop or the server runs out of receive buffer space.
                              //
                              // If the server runs out of buffer space, isReady() will turn false. When the receive buffer has
                              // sufficiently drained, isReady() will turn true, and the serverCallStreamObserver's onReadyHandler
                              // will be called to restart the message pump.
                              serverCallStreamObserver.request(1);
                            } else {
                              // If not, note that back-pressure has begun.
                              wasReady.set(false);
                            }
                          } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            responseObserver.onError(
                                Status.UNKNOWN.withDescription("Error handling request").withCause(throwable).asException());
                          }
                        }


                };
            }
        };

        final Server server = ServerBuilder.forPort(2345).addService(svc).build().start();

        System.out.println("Listening on: 2345");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down");
              server.shutdown();
            }
          });
          server.awaitTermination();


    }

}