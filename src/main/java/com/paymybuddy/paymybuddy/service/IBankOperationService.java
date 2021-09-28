package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.common.FailedTransactionException;
import com.paymybuddy.paymybuddy.dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.BankOperation;

public interface IBankOperationService {

    /**
     * This method concerns transfers to and from a bank account
     * @param transfer
     * @return A BankOperation object in case of a successful transaction
     * @throws FailedTransactionException
     */
    BankOperation bankOperation(TransferDTO transfer) throws FailedTransactionException;

}
