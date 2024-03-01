package com.yhs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcClient1 {

    public static void main(String[] args) {
        //1、创建通信管道
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 8082).usePlaintext().build();
        //2、获得代理对象
        HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
        //3、完成GRPC调用
        //3.1、创建请求对象
        HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
        //3.2、设置请求参数
        builder.setName("yhs");
        HelloProto.HelloRequest helloRequest = builder.build();
        System.out.println("客户端请求参数：" + helloRequest.toString());
        //3.3、执行GRPC调用
        HelloProto.HelloResponse hello = helloServiceBlockingStub.hello(helloRequest);
        //3.4、打印结果
        System.out.println("服务端返回的数据：" + hello.getResult());
    }
}
