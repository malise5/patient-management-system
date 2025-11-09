package com.pm.patientservice.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BillingServiceGrpcClient {

    // localhost:9001/BillingService/CreatePatientAccount
    //aws.grpc:123123/BillingService/CreatePatientAccount
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
                                    @Value("${billing.service.grpc.port:9001}")
                                    int serverPort)
    {
        log.info("Connecting to billing service GRPC service at: {}:{}", serverAddress, serverPort);
        ManagedChannel  channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

    }

    public BillingResponse createBillingAccount(String patientId, String name, String email){

        BillingRequest request = BillingRequest.newBuilder().setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Create billing account request response: {} : {}", response.getAccountId(), response.getStatus());
        return response;
    }
}
