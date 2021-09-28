package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.common.FailedTransactionException;
import com.paymybuddy.paymybuddy.common.Operation;
import com.paymybuddy.paymybuddy.dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.BankOperation;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.BankOperationRepository;
import com.paymybuddy.paymybuddy.service.IBankOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
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

    /**
     * Determines whether the user has sufficient funds
     * @param user the sender
     * @param amount the amount to be transferred
     * @return true if the balance is not negative, false otherwise
     */
    private boolean transferPossible(User user, BigDecimal amount){
        return user.getBalance()
                .subtract(amount)
                .compareTo(BigDecimal.ZERO) >= 0;
    }
}
