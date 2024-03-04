package com.yhs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class GrpcClient3 {

    public static void main(String[] args) {
        //1、创建通信管道
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 8082).usePlaintext().build();
        try{
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
            Iterator<HelloProto.HelloResponse> helloResponseIterator = helloServiceBlockingStub.c2ss(helloRequest);

            //3.4、打印结果
            while(helloResponseIterator.hasNext()){
                HelloProto.HelloResponse helloResponse = helloResponseIterator.next();
                System.out.println("服务端返回的数据：" + helloResponse.getResult());
            }

        }catch (Exception e){
            log.error("异常信息：", e);
            throw new RuntimeException(e);
        }finally {
            //4、关闭通信管道
            channel.shutdownNow();
        }

    }
}
