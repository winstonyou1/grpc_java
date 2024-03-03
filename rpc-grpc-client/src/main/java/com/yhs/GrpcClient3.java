package com.yhs;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class GrpcClient3 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();
        try{
            HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            builder.setName("yhs");
            HelloProto.HelloRequest helloRequest = builder.build();
            Iterator<HelloProto.HelloResponse> helloResponseIterator = helloServiceBlockingStub.c2ss(helloRequest);
            while(helloResponseIterator.hasNext()){
                HelloProto.HelloResponse helloResponse = helloResponseIterator.next();
                System.out.println("helloResponse.getResult(): " + helloResponse.getResult());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            managedChannel.shutdown();
        }

    }
}
