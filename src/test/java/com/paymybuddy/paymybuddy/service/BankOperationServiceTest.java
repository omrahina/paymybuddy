package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.Repository.BankOperationRepository;
import com.paymybuddy.paymybuddy.model.BankOperation;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.serviceImpl.BankOperationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankOperationServiceTest {

    @Mock
    BankOperationRepository bankOperationRepository;

    @InjectMocks
    BankOperationService bankOperationService;

    @Test
    public void transfer_toMyBank_ok(){
        User user = new User("user1@gmail.com", "password");
        user.setBalance(BigDecimal.valueOf(200));
        TransferDTO transferDTO = new TransferDTO(user, BigDecimal.valueOf(50), true, false);
        when(bankOperationRepository.save(any(BankOperation.class))).thenReturn(new BankOperation());

        BankOperation operation = bankOperationService.bankOperation(transferDTO);

        assertThat(operation).isNotNull();
        assertThat(user.getBalance()).isEqualTo(BigDecimal.valueOf(150));

    }

    @Test
    public void transfer_toMyBank_fail(){
        User user = new User("user1@gmail.com", "password");
        user.setBalance(BigDecimal.valueOf(50));
        TransferDTO transferDTO = new TransferDTO(user, BigDecimal.valueOf(100), true, false);

        assertThatExceptionOfType(FailedTransactionException.class).isThrownBy(()
                -> bankOperationService.bankOperation(transferDTO))
                .withMessage("Not enough money in your account.");
    }

    @Test
    public void transfer_fromMyBank_ok(){
        User user = new User("user1@gmail.com", "password");
        TransferDTO transferDTO = new TransferDTO(user, BigDecimal.valueOf(50), false, true);
        when(bankOperationRepository.save(any(BankOperation.class))).thenReturn(new BankOperation());

        BankOperation operation = bankOperationService.bankOperation(transferDTO);

        assertThat(operation).isNotNull();
        assertThat(user.getBalance()).isEqualTo(BigDecimal.valueOf(50));
    }
}
