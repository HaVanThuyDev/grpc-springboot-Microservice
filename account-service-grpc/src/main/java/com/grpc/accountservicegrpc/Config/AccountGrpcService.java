package com.grpc.accountservicegrpc.Config;

import com.grpc.accountservicegrpc.Dto.AccountDto;
import com.grpc.accountservicegrpc.Service.AccountService;
import com.service.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class AccountGrpcService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private AccountService accountService;

    @Override
    public void getAccount(AccountRequest request,
                           StreamObserver<Account> responseObserver) {
        AccountDto dto = accountService.getAccountById(request.getId());

        if (dto == null) {
            responseObserver.onError(
                    new RuntimeException("Account not found with id: " + request.getId())
            );
            return;
        }

        responseObserver.onNext(mapToProto(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AccountList> responseObserver) {
        List<AccountDto> accounts = accountService.getAllAccounts();
        AccountList list = AccountList.newBuilder()
                .addAllAccounts(accounts.stream().map(this::mapToProto).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void createAccount(CreateAccountRequest request,
                              StreamObserver<Account> responseObserver) {
        AccountDto dto = AccountDto.builder()
                .id(null)
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .productId(request.getProductId())
                .build();

        AccountDto created = accountService.createAccount(dto);
        responseObserver.onNext(mapToProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateAccount(UpdateAccountRequest request,
                              StreamObserver<Account> responseObserver) {
        AccountDto dto = AccountDto.builder()
                .id(request.getId())
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .productId(request.getProductId())
                .build();

        AccountDto updated = accountService.updateAccount(request.getId(), dto);
        if (updated == null) {
            responseObserver.onError(new RuntimeException("Account not found with id: " + request.getId()));
            return;
        }
        responseObserver.onNext(mapToProto(updated));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAccount(DeleteAccountRequest request,
                              StreamObserver<Empty> responseObserver) {
        accountService.deleteAccount(request.getId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void searchAccounts(SearchRequest request,
                               StreamObserver<AccountList> responseObserver) {
        List<AccountDto> accounts = accountService.searchByUsername(request.getKeyword());
        AccountList list = AccountList.newBuilder()
                .addAllAccounts(accounts.stream().map(this::mapToProto).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    private Account mapToProto(AccountDto dto) {
        return Account.newBuilder()
                .setId(dto.getId() != null ? dto.getId() : 0)
                .setName(dto.getName() != null ? dto.getName() : "")
                .setUsername(dto.getUsername() != null ? dto.getUsername() : "")
                .setPassword(dto.getPassword() != null ? dto.getPassword() : "")
                .setProductId(dto.getProductId() != null ? dto.getProductId() : 0)
                .build();
    }
}
