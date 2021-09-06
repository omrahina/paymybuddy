package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.Transfer;
import com.paymybuddy.paymybuddy.model.User;

import java.util.List;

public interface ITransferService {

    List<Transfer> myTransfers(String userEmail);

    Transfer newTransfer(TransferDTO transfer, User buddy) throws FailedTransactionException;

    List<Transfer> getAllTransfers();

}
