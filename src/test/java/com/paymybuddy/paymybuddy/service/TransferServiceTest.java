package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.common.FailedTransactionException;
import com.paymybuddy.paymybuddy.dto.TransferDTO;
import com.paymybuddy.paymybuddy.repository.TransferRepository;
import com.paymybuddy.paymybuddy.model.Transfer;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.serviceImpl.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransferServiceTest {

    @Mock
    TransferRepository transferRepository;

    @InjectMocks
    TransferService transferService;

    @Test
    public void should_return_transfers(){
        when(transferRepository.findAllByUserEmail(anyString())).thenReturn(List.of(new Transfer(), new Transfer()));

        List<Transfer> transfers = transferService.myTransfers("user@gmail.com");

        assertThat(transfers).hasSize(2);

    }

    @Test
    public void should_return_an_empty_list(){
        when(transferRepository.findAllByUserEmail(anyString())).thenReturn(List.of());

        List<Transfer> transfers = transferService.myTransfers("user@gmail.com");

        assertThat(transfers).isEmpty();
    }

    @Test
    public void newTransfer_ok(){
        User user = new User("user1@gmail.com", "password");
        user.setBalance(BigDecimal.valueOf(200));
        User buddy = new User("buddy@gmail.com", "password");
        TransferDTO transferDTO = new TransferDTO(user, "buddy@gmail.com", BigDecimal.valueOf(50));
        Transfer transfer = new Transfer();
        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        Transfer newTransfer = transferService.newTransfer(transferDTO, buddy);

        assertThat(newTransfer).isNotNull();
        assertThat(buddy.getBalance()).isEqualTo(BigDecimal.valueOf(50));

    }

    @Test
    public void newTransfer_not_enough_money(){
        User user = new User("user1@gmail.com", "password");
        user.setBalance(BigDecimal.valueOf(10));
        User buddy = new User("buddy@gmail.com", "password");
        TransferDTO transferDTO = new TransferDTO(user, "buddy@gmail.com", BigDecimal.valueOf(50));

        assertThatExceptionOfType(FailedTransactionException.class).isThrownBy(()
                -> transferService.newTransfer(transferDTO, buddy))
                .withMessage("Not enough money in your account.");
    }
}
