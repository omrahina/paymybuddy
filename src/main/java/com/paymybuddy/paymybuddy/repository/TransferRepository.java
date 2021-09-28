package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT t FROM Transfer t " +
    "WHERE t.user.email = :userEmail")
    List<Transfer> findAllByUserEmail(@Param("userEmail") String userEmail);
}
