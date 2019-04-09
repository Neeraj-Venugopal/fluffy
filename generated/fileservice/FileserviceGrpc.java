package fileservice;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0-SNAPSHOT)",
    comments = "Source: fileService.proto")
public final class FileserviceGrpc {

  private FileserviceGrpc() {}

  public static final String SERVICE_NAME = "fileservice.Fileservice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getUploadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadFile",
      requestType = fileservice.FileData.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getUploadFileMethod() {
    io.grpc.MethodDescriptor<fileservice.FileData, fileservice.ack> getUploadFileMethod;
    if ((getUploadFileMethod = FileserviceGrpc.getUploadFileMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getUploadFileMethod = FileserviceGrpc.getUploadFileMethod) == null) {
          FileserviceGrpc.getUploadFileMethod = getUploadFileMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileData, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "UploadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("UploadFile"))
                  .build();
          }
        }
     }
     return getUploadFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.FileData> getDownloadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadFile",
      requestType = fileservice.FileInfo.class,
      responseType = fileservice.FileData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.FileData> getDownloadFileMethod() {
    io.grpc.MethodDescriptor<fileservice.FileInfo, fileservice.FileData> getDownloadFileMethod;
    if ((getDownloadFileMethod = FileserviceGrpc.getDownloadFileMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getDownloadFileMethod = FileserviceGrpc.getDownloadFileMethod) == null) {
          FileserviceGrpc.getDownloadFileMethod = getDownloadFileMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileInfo, fileservice.FileData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "DownloadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileData.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("DownloadFile"))
                  .build();
          }
        }
     }
     return getDownloadFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.ack> getFileSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FileSearch",
      requestType = fileservice.FileInfo.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.ack> getFileSearchMethod() {
    io.grpc.MethodDescriptor<fileservice.FileInfo, fileservice.ack> getFileSearchMethod;
    if ((getFileSearchMethod = FileserviceGrpc.getFileSearchMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getFileSearchMethod = FileserviceGrpc.getFileSearchMethod) == null) {
          FileserviceGrpc.getFileSearchMethod = getFileSearchMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileInfo, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "FileSearch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("FileSearch"))
                  .build();
          }
        }
     }
     return getFileSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getReplicateFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReplicateFile",
      requestType = fileservice.FileData.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getReplicateFileMethod() {
    io.grpc.MethodDescriptor<fileservice.FileData, fileservice.ack> getReplicateFileMethod;
    if ((getReplicateFileMethod = FileserviceGrpc.getReplicateFileMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getReplicateFileMethod = FileserviceGrpc.getReplicateFileMethod) == null) {
          FileserviceGrpc.getReplicateFileMethod = getReplicateFileMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileData, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "ReplicateFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("ReplicateFile"))
                  .build();
          }
        }
     }
     return getReplicateFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.UserInfo,
      fileservice.FileListResponse> getFileListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FileList",
      requestType = fileservice.UserInfo.class,
      responseType = fileservice.FileListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fileservice.UserInfo,
      fileservice.FileListResponse> getFileListMethod() {
    io.grpc.MethodDescriptor<fileservice.UserInfo, fileservice.FileListResponse> getFileListMethod;
    if ((getFileListMethod = FileserviceGrpc.getFileListMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getFileListMethod = FileserviceGrpc.getFileListMethod) == null) {
          FileserviceGrpc.getFileListMethod = getFileListMethod = 
              io.grpc.MethodDescriptor.<fileservice.UserInfo, fileservice.FileListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "FileList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.UserInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("FileList"))
                  .build();
          }
        }
     }
     return getFileListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.ack> getFileDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FileDelete",
      requestType = fileservice.FileInfo.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fileservice.FileInfo,
      fileservice.ack> getFileDeleteMethod() {
    io.grpc.MethodDescriptor<fileservice.FileInfo, fileservice.ack> getFileDeleteMethod;
    if ((getFileDeleteMethod = FileserviceGrpc.getFileDeleteMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getFileDeleteMethod = FileserviceGrpc.getFileDeleteMethod) == null) {
          FileserviceGrpc.getFileDeleteMethod = getFileDeleteMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileInfo, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "FileDelete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("FileDelete"))
                  .build();
          }
        }
     }
     return getFileDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getUpdateFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateFile",
      requestType = fileservice.FileData.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<fileservice.FileData,
      fileservice.ack> getUpdateFileMethod() {
    io.grpc.MethodDescriptor<fileservice.FileData, fileservice.ack> getUpdateFileMethod;
    if ((getUpdateFileMethod = FileserviceGrpc.getUpdateFileMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getUpdateFileMethod = FileserviceGrpc.getUpdateFileMethod) == null) {
          FileserviceGrpc.getUpdateFileMethod = getUpdateFileMethod = 
              io.grpc.MethodDescriptor.<fileservice.FileData, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "UpdateFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.FileData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("UpdateFile"))
                  .build();
          }
        }
     }
     return getUpdateFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.Empty,
      fileservice.ClusterStats> getGetClusterStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getClusterStats",
      requestType = fileservice.Empty.class,
      responseType = fileservice.ClusterStats.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fileservice.Empty,
      fileservice.ClusterStats> getGetClusterStatsMethod() {
    io.grpc.MethodDescriptor<fileservice.Empty, fileservice.ClusterStats> getGetClusterStatsMethod;
    if ((getGetClusterStatsMethod = FileserviceGrpc.getGetClusterStatsMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getGetClusterStatsMethod = FileserviceGrpc.getGetClusterStatsMethod) == null) {
          FileserviceGrpc.getGetClusterStatsMethod = getGetClusterStatsMethod = 
              io.grpc.MethodDescriptor.<fileservice.Empty, fileservice.ClusterStats>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "getClusterStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ClusterStats.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("getClusterStats"))
                  .build();
          }
        }
     }
     return getGetClusterStatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fileservice.ClusterInfo,
      fileservice.ack> getGetLeaderInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLeaderInfo",
      requestType = fileservice.ClusterInfo.class,
      responseType = fileservice.ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fileservice.ClusterInfo,
      fileservice.ack> getGetLeaderInfoMethod() {
    io.grpc.MethodDescriptor<fileservice.ClusterInfo, fileservice.ack> getGetLeaderInfoMethod;
    if ((getGetLeaderInfoMethod = FileserviceGrpc.getGetLeaderInfoMethod) == null) {
      synchronized (FileserviceGrpc.class) {
        if ((getGetLeaderInfoMethod = FileserviceGrpc.getGetLeaderInfoMethod) == null) {
          FileserviceGrpc.getGetLeaderInfoMethod = getGetLeaderInfoMethod = 
              io.grpc.MethodDescriptor.<fileservice.ClusterInfo, fileservice.ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "fileservice.Fileservice", "getLeaderInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ClusterInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fileservice.ack.getDefaultInstance()))
                  .setSchemaDescriptor(new FileserviceMethodDescriptorSupplier("getLeaderInfo"))
                  .build();
          }
        }
     }
     return getGetLeaderInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileserviceStub newStub(io.grpc.Channel channel) {
    return new FileserviceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileserviceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FileserviceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileserviceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FileserviceFutureStub(channel);
  }

  /**
   */
  public static abstract class FileserviceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> uploadFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadFileMethod(), responseObserver);
    }

    /**
     */
    public void downloadFile(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.FileData> responseObserver) {
      asyncUnimplementedUnaryCall(getDownloadFileMethod(), responseObserver);
    }

    /**
     */
    public void fileSearch(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnimplementedUnaryCall(getFileSearchMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> replicateFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncUnimplementedStreamingCall(getReplicateFileMethod(), responseObserver);
    }

    /**
     */
    public void fileList(fileservice.UserInfo request,
        io.grpc.stub.StreamObserver<fileservice.FileListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFileListMethod(), responseObserver);
    }

    /**
     */
    public void fileDelete(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnimplementedUnaryCall(getFileDeleteMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> updateFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncUnimplementedStreamingCall(getUpdateFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * Cluster Stats Change
     * </pre>
     */
    public void getClusterStats(fileservice.Empty request,
        io.grpc.stub.StreamObserver<fileservice.ClusterStats> responseObserver) {
      asyncUnimplementedUnaryCall(getGetClusterStatsMethod(), responseObserver);
    }

    /**
     */
    public void getLeaderInfo(fileservice.ClusterInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLeaderInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadFileMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                fileservice.FileData,
                fileservice.ack>(
                  this, METHODID_UPLOAD_FILE)))
          .addMethod(
            getDownloadFileMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                fileservice.FileInfo,
                fileservice.FileData>(
                  this, METHODID_DOWNLOAD_FILE)))
          .addMethod(
            getFileSearchMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                fileservice.FileInfo,
                fileservice.ack>(
                  this, METHODID_FILE_SEARCH)))
          .addMethod(
            getReplicateFileMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                fileservice.FileData,
                fileservice.ack>(
                  this, METHODID_REPLICATE_FILE)))
          .addMethod(
            getFileListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                fileservice.UserInfo,
                fileservice.FileListResponse>(
                  this, METHODID_FILE_LIST)))
          .addMethod(
            getFileDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                fileservice.FileInfo,
                fileservice.ack>(
                  this, METHODID_FILE_DELETE)))
          .addMethod(
            getUpdateFileMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                fileservice.FileData,
                fileservice.ack>(
                  this, METHODID_UPDATE_FILE)))
          .addMethod(
            getGetClusterStatsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                fileservice.Empty,
                fileservice.ClusterStats>(
                  this, METHODID_GET_CLUSTER_STATS)))
          .addMethod(
            getGetLeaderInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                fileservice.ClusterInfo,
                fileservice.ack>(
                  this, METHODID_GET_LEADER_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class FileserviceStub extends io.grpc.stub.AbstractStub<FileserviceStub> {
    private FileserviceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileserviceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileserviceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileserviceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> uploadFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void downloadFile(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.FileData> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getDownloadFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fileSearch(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFileSearchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> replicateFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getReplicateFileMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void fileList(fileservice.UserInfo request,
        io.grpc.stub.StreamObserver<fileservice.FileListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFileListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fileDelete(fileservice.FileInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFileDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fileservice.FileData> updateFile(
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUpdateFileMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Cluster Stats Change
     * </pre>
     */
    public void getClusterStats(fileservice.Empty request,
        io.grpc.stub.StreamObserver<fileservice.ClusterStats> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetClusterStatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLeaderInfo(fileservice.ClusterInfo request,
        io.grpc.stub.StreamObserver<fileservice.ack> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLeaderInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FileserviceBlockingStub extends io.grpc.stub.AbstractStub<FileserviceBlockingStub> {
    private FileserviceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileserviceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileserviceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileserviceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<fileservice.FileData> downloadFile(
        fileservice.FileInfo request) {
      return blockingServerStreamingCall(
          getChannel(), getDownloadFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public fileservice.ack fileSearch(fileservice.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), getFileSearchMethod(), getCallOptions(), request);
    }

    /**
     */
    public fileservice.FileListResponse fileList(fileservice.UserInfo request) {
      return blockingUnaryCall(
          getChannel(), getFileListMethod(), getCallOptions(), request);
    }

    /**
     */
    public fileservice.ack fileDelete(fileservice.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), getFileDeleteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Cluster Stats Change
     * </pre>
     */
    public fileservice.ClusterStats getClusterStats(fileservice.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetClusterStatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public fileservice.ack getLeaderInfo(fileservice.ClusterInfo request) {
      return blockingUnaryCall(
          getChannel(), getGetLeaderInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FileserviceFutureStub extends io.grpc.stub.AbstractStub<FileserviceFutureStub> {
    private FileserviceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileserviceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileserviceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileserviceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fileservice.ack> fileSearch(
        fileservice.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getFileSearchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fileservice.FileListResponse> fileList(
        fileservice.UserInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getFileListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fileservice.ack> fileDelete(
        fileservice.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getFileDeleteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Cluster Stats Change
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<fileservice.ClusterStats> getClusterStats(
        fileservice.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetClusterStatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fileservice.ack> getLeaderInfo(
        fileservice.ClusterInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLeaderInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DOWNLOAD_FILE = 0;
  private static final int METHODID_FILE_SEARCH = 1;
  private static final int METHODID_FILE_LIST = 2;
  private static final int METHODID_FILE_DELETE = 3;
  private static final int METHODID_GET_CLUSTER_STATS = 4;
  private static final int METHODID_GET_LEADER_INFO = 5;
  private static final int METHODID_UPLOAD_FILE = 6;
  private static final int METHODID_REPLICATE_FILE = 7;
  private static final int METHODID_UPDATE_FILE = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileserviceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileserviceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DOWNLOAD_FILE:
          serviceImpl.downloadFile((fileservice.FileInfo) request,
              (io.grpc.stub.StreamObserver<fileservice.FileData>) responseObserver);
          break;
        case METHODID_FILE_SEARCH:
          serviceImpl.fileSearch((fileservice.FileInfo) request,
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
          break;
        case METHODID_FILE_LIST:
          serviceImpl.fileList((fileservice.UserInfo) request,
              (io.grpc.stub.StreamObserver<fileservice.FileListResponse>) responseObserver);
          break;
        case METHODID_FILE_DELETE:
          serviceImpl.fileDelete((fileservice.FileInfo) request,
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
          break;
        case METHODID_GET_CLUSTER_STATS:
          serviceImpl.getClusterStats((fileservice.Empty) request,
              (io.grpc.stub.StreamObserver<fileservice.ClusterStats>) responseObserver);
          break;
        case METHODID_GET_LEADER_INFO:
          serviceImpl.getLeaderInfo((fileservice.ClusterInfo) request,
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_FILE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadFile(
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
        case METHODID_REPLICATE_FILE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.replicateFile(
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
        case METHODID_UPDATE_FILE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateFile(
              (io.grpc.stub.StreamObserver<fileservice.ack>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FileserviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileserviceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return fileservice.FileService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Fileservice");
    }
  }

  private static final class FileserviceFileDescriptorSupplier
      extends FileserviceBaseDescriptorSupplier {
    FileserviceFileDescriptorSupplier() {}
  }

  private static final class FileserviceMethodDescriptorSupplier
      extends FileserviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileserviceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileserviceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileserviceFileDescriptorSupplier())
              .addMethod(getUploadFileMethod())
              .addMethod(getDownloadFileMethod())
              .addMethod(getFileSearchMethod())
              .addMethod(getReplicateFileMethod())
              .addMethod(getFileListMethod())
              .addMethod(getFileDeleteMethod())
              .addMethod(getUpdateFileMethod())
              .addMethod(getGetClusterStatsMethod())
              .addMethod(getGetLeaderInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
