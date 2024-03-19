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
public class GrpcClient5 {
    public static void main(String[] args) throws InterruptedException {
        //创建channel
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8082).usePlaintext().build();
        try{
            //创建监听stub
            HelloServiceGrpc.HelloServiceStub helloService= HelloServiceGrpc.newStub(managedChannel);
            StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloService.cs2s(new StreamObserver<HelloProto.HelloResponse>() {
                //服务端返回的响应-单次
                @Override
                public void onNext(HelloProto.HelloResponse helloResponse) {
                    //打印服务端响应
                    System.out.println("server response:"+helloResponse.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    //打印服务端响应-全部
                    System.out.println("server response all" );
                }
            });
            //模拟发送多个请求
            for (int i = 0 ; i < 6 ; ++i ){
                HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder().setName("winston-" + i).build();
                helloRequestStreamObserver.onNext(helloRequest);
                Thread.sleep(1000);
            }
            helloRequestStreamObserver.onCompleted();
            managedChannel.awaitTermination(12,TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!managedChannel.isShutdown())
                managedChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
