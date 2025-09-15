package com.grpc.clientservice.controller;

import com.grpc.clientservice.dto.AccountDto;
import com.grpc.clientservice.service.GrpcAccountClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final GrpcAccountClientService accountClientService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountClientService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable int id) {
        AccountDto account = accountClientService.getAccountById(id);
        return ResponseEntity.ok(account);
    }
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountDto createdAccount = accountClientService.createAccount(
                accountDto.getName(),
                accountDto.getUsername(),
                accountDto.getPassword(),
                accountDto.getProductId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable int id, @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountClientService.updateAccount(
                id,
                accountDto.getName(),
                accountDto.getUsername(),
                accountDto.getPassword(),
                accountDto.getProductId()
        );
        return ResponseEntity.ok(updatedAccount);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        accountClientService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(@RequestParam String keyword) {
        List<AccountDto> accounts = accountClientService.searchAccounts(keyword);
        return ResponseEntity.ok(accounts);
    }
}