package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Common.Operation;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.Repository.BankOperationRepository;
import com.paymybuddy.paymybuddy.model.BankOperation;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.IBankOperationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@Data
@Slf4j
public class BankOperationService implements IBankOperationService {

    private BankOperationRepository bankOperationRepository;

    public BankOperationService(BankOperationRepository bankOperationRepository){
        this.bankOperationRepository = bankOperationRepository;
    }

    @Override
    public BankOperation bankOperation(TransferDTO transfer) throws FailedTransactionException {
        User user = transfer.getUser();
        BigDecimal amount = transfer.getAmount();
        if (transfer.isToMyBankAccount()){
            log.info("Transaction to a bank account in progress");
            if (transferPossible(user, amount)){
                user.setBalance(user.getBalance().subtract(amount));
                BankOperation bankOperation = new BankOperation(user, transfer.getBankAccount(), amount, Operation.OUT, transfer.getDescription());
                log.info("Transaction success");
                return bankOperationRepository.save(bankOperation);
            }
            log.error("Not enough money");
            throw new FailedTransactionException("Not enough money in your account.");
        }
        if (transfer.isFromMyBankAccount()){
            log.info("Transaction from a bank account in progress");
            user.setBalance(user.getBalance().add(amount));
            BankOperation bankOperation = new BankOperation(user, transfer.getBankAccount(), amount, Operation.IN, transfer.getDescription());
            log.info("Transaction success");
            return bankOperationRepository.save(bankOperation);
        }
        log.error("Failed transaction");
        throw new FailedTransactionException("Failed transaction.");
    }

    private boolean transferPossible(User user, BigDecimal realAmount){
        return user.getBalance()
                .subtract(realAmount)
                .compareTo(BigDecimal.ZERO) >= 0;
    }
}