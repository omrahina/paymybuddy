package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.Common.FailedTransactionException;
import com.paymybuddy.paymybuddy.Dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.Transfer;
import com.paymybuddy.paymybuddy.model.User;

import java.util.List;

public interface ITransferService {

    /**
     * Returns a list of transfers related to a specific user
     * @param userEmail A unique string identifying the user
     * @return A list of transfers or an empty list
     */
    List<Transfer> myTransfers(String userEmail);

    /**
     * This method executes or not a transfer depending on certain parameters
     * @param transfer an object carrying specificities regarding the transfer
     * @param buddy The receiver
     * @return Transfer object if successful
     * @throws FailedTransactionException
     */
    Transfer newTransfer(TransferDTO transfer, User buddy) throws FailedTransactionException;

    List<Transfer> getAllTransfers();

}
