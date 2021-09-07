package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
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
