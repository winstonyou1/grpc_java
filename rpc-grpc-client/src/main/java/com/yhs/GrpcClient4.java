package com.yhs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author：winston
 * @Filename：GrpcClient4
 * @Date：2024/3/4 21:23
 */
public class GrpcClient4 {
    public static void main(String[] args) throws InterruptedException {
        //创建channel
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8082).usePlaintext().build();
        try{
            //创建监听stub
            HelloServiceGrpc.HelloServiceStub helloService= HelloServiceGrpc.newStub(managedChannel);
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder() ;
            builder.setName("yhs");
            HelloProto.HelloRequest helloRequest = builder.build();
            helloService.c2ss(helloRequest, new StreamObserver<HelloProto.HelloResponse>() {
                List<HelloProto.HelloResponse> responseList = new ArrayList();
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    //自己实现onNext方法，处理服务端返回的数据 服务端响应一个消息后，如果需要立即处理的话，把业务逻辑写到这里
                    System.out.println("服务端每一次响应的信息: " + helloResponse.getResult());
                    responseList.add(helloResponse);
                }

                @Override
                public void onError(Throwable throwable) {
                    //自己实现onError方法，处理异常
                }

                @Override
                public void onCompleted() {
                    //自己实现onCompleted方法，处理完成  把服务端响应的所有数据拿到后，进行业务处理
                    System.out.println("服务端响应完毕应结果:"+responseList);
                }
            });
            managedChannel.awaitTermination(12, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!managedChannel.isShutdown())
                managedChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
