package gash.grpc.route.server;

//import com.google.api.Advice.Builder;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
import fileservice.FileData;
import fileservice.FileData.Builder;
import fileservice.FileService;
import fileservice.FileserviceGrpc;
import fileservice.ack;
import fileservice.FileInfo;
import fileservice.FileserviceGrpc.FileserviceImplBase;


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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class TestDownloadFile extends FileserviceImplBase{

    @Override
    public void downloadFile(fileservice.FileInfo request, io.grpc.stub.StreamObserver<fileservice.FileData> responseObserver){
        fileservice.FileData.Builder fileDataBuilder = FileData.newBuilder();

        File file = new File("src/gash/grpc/route/server/fileSamples/file-sample_100kB.doc");

        System.out.println("User Name: " + request.getUsername());
        System.out.println("File Name: " + request.getFilename());

        FileInputStream fis = null;
        try {
            System.out.println("Trying to read File");
			fis = new FileInputStream(file);
			int seq = 0;
			final int blen = 1024 * 1024 * 3;
			byte[] raw = new byte[blen];
			boolean done = false;
			while (!done) {
				int n = fis.read(raw, 0, blen);
				if (n <= 0)
					break;

				// identifying sequence number
				seq++;

				// routing/header information
				fileDataBuilder.setFilename(request.getFilename());
				fileDataBuilder.setSeq(seq);
				fileDataBuilder.setData(ByteString.copyFrom(raw, 0, n));
                System.out.println("Trying to send File");
				fileservice.FileData rtn = fileDataBuilder.build();
				responseObserver.onNext(rtn);
			}
		} catch (IOException e) {
			; // ignore? really?
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				; // ignore
			}

			responseObserver.onCompleted();
		}

	}
    
}
