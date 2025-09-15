package com.grpc.accountservicegrpc.Repository;

import com.grpc.accountservicegrpc.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findByUsername(String username);
}
