package com.grpc.accountservicegrpc.Service;

import com.grpc.accountservicegrpc.Dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(Integer id);
    List<AccountDto> getAllAccounts();
    AccountDto createAccount(AccountDto dto);
    AccountDto updateAccount(Integer id, AccountDto dto);
    void deleteAccount(Integer id);
    List<AccountDto> searchByUsername(String username);
}
