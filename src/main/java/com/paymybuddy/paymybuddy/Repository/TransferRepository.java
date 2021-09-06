package com.paymybuddy.paymybuddy.Repository;

import com.paymybuddy.paymybuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT t FROM Transfer t " +
    "WHERE t.user.email = :userEmail")
    List<Transfer> findAllByUserEmail(@Param("userEmail") String userEmail);
}
