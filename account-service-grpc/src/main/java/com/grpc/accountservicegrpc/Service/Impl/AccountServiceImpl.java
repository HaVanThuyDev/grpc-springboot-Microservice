package com.grpc.accountservicegrpc.Service.Impl;

import com.grpc.accountservicegrpc.Dto.AccountDto;
import com.grpc.accountservicegrpc.Entity.AccountEntity;
import com.grpc.accountservicegrpc.Repository.AccountRepository;
import com.grpc.accountservicegrpc.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private AccountDto mapToDto(AccountEntity entity) {
        if (entity == null) return null;
        return new AccountDto(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getProductId()
        );
    }
    private AccountEntity mapToEntity(AccountDto dto) {
        if (dto == null) return null;
        return new AccountEntity(
                dto.getId(),
                dto.getName(),
                dto.getPassword(),
                dto.getUsername(),
                dto.getProductId()
        );
    }

    @Override
    public AccountDto getAccountById(Integer id) {
        Optional<AccountEntity> entity = accountRepository.findById(id);
        return entity.map(this::mapToDto).orElse(null);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto createAccount(AccountDto dto) {
        AccountEntity entity = mapToEntity(dto);
        entity.setId(null); // để JPA tự sinh ID
        AccountEntity saved = accountRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public AccountDto updateAccount(Integer id, AccountDto dto) {
        Optional<AccountEntity> optionalEntity = accountRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return null;
        }
        AccountEntity entity = optionalEntity.get();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setProductId(dto.getProductId());
        AccountEntity updated = accountRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> searchByUsername(String username) {
        return accountRepository.findAll()
                .stream()
                .filter(acc -> acc.getUsername().toLowerCase().contains(username.toLowerCase()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
