package com.yhs.service;


import com.google.protobuf.ProtocolStringList;
import com.yhs.HelloProto;
import com.yhs.HelloProto.HelloRequest;
import com.yhs.HelloProto.HelloResponse;
import com.yhs.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HelloServiceImpl
 * @Author Winston
 * @Description TODO
 * @Date 2024/2/29 21:33
 * @Version 1.0
 **/
public class HelloServiceImpl  extends HelloServiceGrpc.HelloServiceImplBase {
    /*
     * StreamObserver<HelloResponse> responseObserver 对应的是hello方法的返回值
     * 1、接受client提交的参数
     * 2、业务处理 sercie+ dao 调用对应的业务功能
     * 3、返回结果给client
     *
     */
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        //1、接收客户端发送来的请求数据
        String name = request.getName();
        //2、业务处理
        System.out.println("name parameter is " + name);
        //3、封装响应
        //3.1、创建相应对象的构造者
        HelloResponse.Builder builder = HelloResponse.newBuilder();
        //3.2、填充数据
        builder.setResult("hello method invoke ok, name is " + name);
        //3.3、封装响应
        HelloResponse helloResponse = builder.build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void mutiHello(HelloProto.MutiHelloRequest request, StreamObserver<HelloProto.MutiHelloResponse> responseObserver) {
        //1、接收客户端发送来的请求数据
        ProtocolStringList nameList = request.getNameList();
        //2、业务处理
        System.out.println("name parameter is " + nameList);
        //3、封装响应
        //3.1、创建相应对象的构造者
        HelloProto.MutiHelloResponse.Builder builder = HelloProto.MutiHelloResponse.newBuilder();
        //3.2、填充数据
        builder.setResult("hello method invoke ok, name is " + nameList);
        //3.3、封装响应
        HelloProto.MutiHelloResponse mutiHelloResponse = builder.build();
        responseObserver.onNext(mutiHelloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void c2ss(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        //1、接收客户端发送来的请求数据
        String name = request.getName();
        try{
            //模拟耗时操作
            for (int i = 0 ; i < 9 ; i++){
                //2、业务处理
                System.out.println("name parameter is i - "+i+" " + name);
                //3、封装响应
                //3.1、创建相应对象的构造者
                HelloResponse.Builder builder = HelloResponse.newBuilder();
                //3.2、填充数据
                builder.setResult("hello method invoke ok, name is i - "+i+" " + name );
                //3.3、封装响应
                HelloResponse helloResponse = builder.build();
                responseObserver.onNext(helloResponse);
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }

    /**
    *@Date：2024/3/4
    *@Author：winston
    *@return：客户端流式RPC 返回值是StreamObserver  监听多个请求 返回一个结果集
     **/
    @Override
    public StreamObserver<HelloRequest> cs2s(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            List<HelloRequest> requestList = new ArrayList<>();
            @Override
            public void onNext(HelloRequest helloRequest) {
                System.out.println("接收到了客户端发送的一条消息：" + helloRequest.getName());
                //1、接收客户端发送来的请求数据 单次处理返回值
                requestList.add(helloRequest);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("服务器已经收到了所有客户端发送请求，准备返回结果");
                //1、接收客户端发送来的请求数据 所有请求都接收后处理返回值
                HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
                builder.setResult("hello method invoke ok"+requestList.toString());
                HelloResponse helloResponse = builder.build();
                responseObserver.onNext(helloResponse);
                responseObserver.onCompleted();
            }
        };
    }
}
