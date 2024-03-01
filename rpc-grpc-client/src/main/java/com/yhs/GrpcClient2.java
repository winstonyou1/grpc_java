package com.yhs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcClient2 {

    public static void main(String[] args) {
        //1、创建通信管道
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 8082).usePlaintext().build();
        try{
            //2、获得代理对象
            HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
            //3、完成GRPC调用
            //3.1、创建请求对象
            HelloProto.MutiHelloRequest.Builder builder = HelloProto.MutiHelloRequest.newBuilder();
            //3.2、设置请求参数
            builder.addName("yhs");
            builder.addName("ljr");
            builder.addName("ncc");
            HelloProto.MutiHelloRequest helloRequest = builder.build();
            System.out.println("客户端请求参数：" + helloRequest.toString());
            //3.3、执行GRPC调用
            HelloProto.MutiHelloResponse mutiHello = helloServiceBlockingStub.mutiHello(helloRequest);
            //3.4、打印结果
            System.out.println("服务端返回的数据：" + mutiHello.getResult());
        }catch (Exception e){
            log.error("异常信息：", e);
            throw new RuntimeException(e);
        }finally {
            //4、关闭通信管道
            channel.shutdownNow();
        }

    }
}
