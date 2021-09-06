package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.BankOperation;

public interface IBankOperationService {

    BankOperation bankOperation(TransferDTO transfer) throws FailedTransactionException;

}
