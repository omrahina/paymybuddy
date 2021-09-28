package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.BankOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankOperationRepository extends JpaRepository<BankOperation, Long> {

    @Query(value = "SELECT operation FROM BankOperation operation " +
    "WHERE operation.user.email = :userEmail")
    List<BankOperation> findAllByUserEmail(@Param("userEmail") String userEmail);
}
