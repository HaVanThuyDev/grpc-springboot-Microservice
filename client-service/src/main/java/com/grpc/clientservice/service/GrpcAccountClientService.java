package com.grpc.clientservice.service;

import com.google.protobuf.Empty;
import com.grpc.clientservice.dto.AccountDto; // Import DTO của client
import com.service.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrpcAccountClientService {

    private final AccountServiceGrpc.AccountServiceBlockingStub accountStub;

    private AccountDto mapToDto(Account grpcAccount) {
        return AccountDto.builder()
                .id(grpcAccount.getId())
                .name(grpcAccount.getName())
                .username(grpcAccount.getUsername())
                .productId(grpcAccount.getProductId())
                .build();
    }
    public List<AccountDto> getAllAccounts() {
        AccountList grpcAccountList = accountStub.getAllAccounts(Empty.getDefaultInstance());
        return grpcAccountList.getAccountsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public AccountDto getAccountById(int id) {
        AccountRequest request = AccountRequest.newBuilder()
                .setId(id)
                .build();
        Account grpcAccount = accountStub.getAccount(request);
        return mapToDto(grpcAccount);
    }
    public AccountDto createAccount(String name, String username, String password, int productId) {
        CreateAccountRequest request = CreateAccountRequest.newBuilder()
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .setProductId(productId)
                .build();
        Account grpcAccount = accountStub.createAccount(request);
        return mapToDto(grpcAccount);
    }
    public AccountDto updateAccount(int id, String name, String username, String password, int productId) {
        UpdateAccountRequest request = UpdateAccountRequest.newBuilder()
                .setId(id)
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .setProductId(productId)
                .build();
        Account grpcAccount = accountStub.updateAccount(request);
        return mapToDto(grpcAccount);
    }
    public void deleteAccount(int id) {
        DeleteAccountRequest request = DeleteAccountRequest.newBuilder()
                .setId(id)
                .build();
        accountStub.deleteAccount(request);
    }
    public List<AccountDto> searchAccounts(String keyword) {
        SearchRequest request = SearchRequest.newBuilder()
                .setKeyword(keyword)
                .build();
        AccountList grpcAccountList = accountStub.searchAccounts(request);
        return grpcAccountList.getAccountsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}