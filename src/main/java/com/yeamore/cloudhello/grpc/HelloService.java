package com.yeamore.cloudhello.grpc;

import com.github.conanchen.yeamore.hello.grpc.HelloGrpc;
import com.github.conanchen.yeamore.hello.grpc.HelloReply;
import com.github.conanchen.yeamore.hello.grpc.HelloRequest;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@GRpcService(interceptors = {LogInterceptor.class})
public class HelloService extends HelloGrpc.HelloImplBase {
    private static final Logger log = LoggerFactory.getLogger(HelloService.class);
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Calendar calendar = Calendar.getInstance();

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        final HelloReply.Builder replyBuilder = HelloReply.newBuilder().setId(calendar.getTimeInMillis()).setMessage("Hello " + request.getName() + dateFormat.format(calendar.getTime()));
        HelloReply helloReply = replyBuilder.build();
        responseObserver.onNext(helloReply);
        log.info(String.format("HelloService.sayHello() %d:%s gson=%s", helloReply.getId(), helloReply.getMessage(), gson.toJson(helloReply)));
        responseObserver.onCompleted();
    }
}