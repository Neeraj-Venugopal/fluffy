package gash.grpc.route.server;

import java.io.IOException;

//import com.google.api.Advice.Builder;
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

public class TestHeartBeatServer extends HeartBeatImplBase{

    public Server svr;

    public static void main(String[] args) throws Exception {
        try{
            final TestHeartBeatServer impl = new TestHeartBeatServer();
            impl.start();
            impl.blockUntilShutdown();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void start() throws Exception{
        svr = ServerBuilder.forPort(2345).addService(new TestHeartBeatServer()).build();
        System.out.println("-- starting server");
        svr.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				TestHeartBeatServer.this.stop();
			}
		});


    }

    protected void stop() {
		svr.shutdown();
    }

    private void blockUntilShutdown() throws Exception {
		svr.awaitTermination();
	}
    
    @Override
    public void isAlive(heart.NodeInfo request, io.grpc.stub.StreamObserver<heart.Stats> responseObserver){
        System.out.println("Requesting for Heart Beat");
        heart.Stats.Builder stat = Stats.newBuilder();
        stat.setCpuUsage("10%");
        stat.setDiskSpace("20%");
        stat.setUsedMem("34%");
        heart.Stats rtn = stat.build();
        responseObserver.onNext(rtn);
		responseObserver.onCompleted();
    }
}