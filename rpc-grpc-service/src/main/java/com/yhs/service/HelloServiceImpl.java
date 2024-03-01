package com.yhs.service;

import com.yhs.HelloProto.HelloRequest;
import com.yhs.HelloProto.HelloResponse;
import com.yhs.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

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
}
