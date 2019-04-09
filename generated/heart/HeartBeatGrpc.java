package heart;

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
 * <pre>
 * The heartbeat service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0-SNAPSHOT)",
    comments = "Source: heartbeat.proto")
public final class HeartBeatGrpc {

  private HeartBeatGrpc() {}

  public static final String SERVICE_NAME = "heart.HeartBeat";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<heart.NodeInfo,
      heart.Stats> getIsAliveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "isAlive",
      requestType = heart.NodeInfo.class,
      responseType = heart.Stats.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<heart.NodeInfo,
      heart.Stats> getIsAliveMethod() {
    io.grpc.MethodDescriptor<heart.NodeInfo, heart.Stats> getIsAliveMethod;
    if ((getIsAliveMethod = HeartBeatGrpc.getIsAliveMethod) == null) {
      synchronized (HeartBeatGrpc.class) {
        if ((getIsAliveMethod = HeartBeatGrpc.getIsAliveMethod) == null) {
          HeartBeatGrpc.getIsAliveMethod = getIsAliveMethod = 
              io.grpc.MethodDescriptor.<heart.NodeInfo, heart.Stats>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "heart.HeartBeat", "isAlive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  heart.NodeInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  heart.Stats.getDefaultInstance()))
                  .setSchemaDescriptor(new HeartBeatMethodDescriptorSupplier("isAlive"))
                  .build();
          }
        }
     }
     return getIsAliveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HeartBeatStub newStub(io.grpc.Channel channel) {
    return new HeartBeatStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HeartBeatBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HeartBeatBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HeartBeatFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HeartBeatFutureStub(channel);
  }

  /**
   * <pre>
   * The heartbeat service definition.
   * </pre>
   */
  public static abstract class HeartBeatImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends status
     * </pre>
     */
    public void isAlive(heart.NodeInfo request,
        io.grpc.stub.StreamObserver<heart.Stats> responseObserver) {
      asyncUnimplementedUnaryCall(getIsAliveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getIsAliveMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                heart.NodeInfo,
                heart.Stats>(
                  this, METHODID_IS_ALIVE)))
          .build();
    }
  }

  /**
   * <pre>
   * The heartbeat service definition.
   * </pre>
   */
  public static final class HeartBeatStub extends io.grpc.stub.AbstractStub<HeartBeatStub> {
    private HeartBeatStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartBeatStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartBeatStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartBeatStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends status
     * </pre>
     */
    public void isAlive(heart.NodeInfo request,
        io.grpc.stub.StreamObserver<heart.Stats> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIsAliveMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The heartbeat service definition.
   * </pre>
   */
  public static final class HeartBeatBlockingStub extends io.grpc.stub.AbstractStub<HeartBeatBlockingStub> {
    private HeartBeatBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartBeatBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartBeatBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartBeatBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends status
     * </pre>
     */
    public heart.Stats isAlive(heart.NodeInfo request) {
      return blockingUnaryCall(
          getChannel(), getIsAliveMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The heartbeat service definition.
   * </pre>
   */
  public static final class HeartBeatFutureStub extends io.grpc.stub.AbstractStub<HeartBeatFutureStub> {
    private HeartBeatFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartBeatFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartBeatFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartBeatFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends status
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<heart.Stats> isAlive(
        heart.NodeInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getIsAliveMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_IS_ALIVE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HeartBeatImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HeartBeatImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_IS_ALIVE:
          serviceImpl.isAlive((heart.NodeInfo) request,
              (io.grpc.stub.StreamObserver<heart.Stats>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HeartBeatBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HeartBeatBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return heart.Heartbeat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HeartBeat");
    }
  }

  private static final class HeartBeatFileDescriptorSupplier
      extends HeartBeatBaseDescriptorSupplier {
    HeartBeatFileDescriptorSupplier() {}
  }

  private static final class HeartBeatMethodDescriptorSupplier
      extends HeartBeatBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HeartBeatMethodDescriptorSupplier(String methodName) {
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
      synchronized (HeartBeatGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HeartBeatFileDescriptorSupplier())
              .addMethod(getIsAliveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
