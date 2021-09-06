package com.paymybuddy.paymybuddy.serviceImpl;

import com.paymybuddy.paymybuddy.Common.Constants;
import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.Repository.TransferRepository;
import com.paymybuddy.paymybuddy.model.Transfer;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.ITransferService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Data
@Transactional
@Slf4j
public class TransferService implements ITransferService {

    private TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository){
        this.transferRepository = transferRepository;
    }

    @Override
    public List<Transfer> myTransfers(String userEmail) {
        List<Transfer> transfers = transferRepository.findAllByUserEmail(userEmail);
        if (!transfers.isEmpty()){
            log.info("Transfer(s) found for the current user");
            return transfers;
        }
        log.error("No transfer found");
        return List.of();

    }

    @Override
    public Transfer newTransfer(TransferDTO transfer, User buddy) throws FailedTransactionException{
        User user = transfer.getUser();
        BigDecimal amount = transfer.getAmount();
        BigDecimal realAmount = realAmountValue(amount);
        if (transferPossible(user, realAmount)){
            user.setBalance(user.getBalance().subtract(realAmount));
            buddy.setBalance(buddy.getBalance().add(amount));
            Transfer newTransfer = new Transfer(user, buddy, amount, transfer.getDescription());
            log.info("Transaction success");
            return transferRepository.save(newTransfer);
        }
        log.error("Not enough money");
        throw new FailedTransactionException("Not enough money in your account.");
    }

    private BigDecimal realAmountValue(BigDecimal amount){
        BigDecimal percentageAmount = amount.multiply(BigDecimal.valueOf(Constants.PERCENTAGE / 100.0));
        return amount.add(percentageAmount);
    }

    private boolean transferPossible(User user, BigDecimal realAmount){
        return user.getBalance()
                .subtract(realAmount)
                .compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();
        log.info("passed");
        return transfers;
    }

}
