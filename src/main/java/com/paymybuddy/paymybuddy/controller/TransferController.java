package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.common.FailedTransactionException;
import com.paymybuddy.paymybuddy.dto.TransferDTO;
import com.paymybuddy.paymybuddy.model.Transfer;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.ITransferService;
import com.paymybuddy.paymybuddy.serviceImpl.BankOperationService;
import com.paymybuddy.paymybuddy.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class TransferController {

    private final ITransferService transferService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankOperationService bankOperationService;

    public static final String TRANSFER = "transfer";

    public TransferController(ITransferService transferService){
        this.transferService = transferService;
    }

    @GetMapping("/transfer")
    public String myTransfers(Model model, HttpServletRequest request){
        String currentUserEmail = currentUserEmail(request);
        List<Transfer> transfers = transferService.myTransfers(currentUserEmail);
        User user = userService.findUser(currentUserEmail);

        if (!transfers.isEmpty()){
            model.addAttribute("transfers", transfers);
        }

        model.addAttribute("connections", userService.getConnections(user));
        model.addAttribute("newTransfer", new TransferDTO(user));
        model.addAttribute("user", user);

        return TRANSFER;
    }

    @PostMapping("/transfer")
    public String newTransfer(@ModelAttribute("newTransfer") TransferDTO transfer, Model model){
        User user = transfer.getUser();

        if ("default".equals(transfer.getConnection())){
            model.addAttribute("error", "Transaction not executed. Please select a valid buddy.");
        } else {
            User buddy = userService.findUser(transfer.getConnection());
            try{
                transferService.newTransfer(transfer, buddy);
                model.addAttribute("success", "Transaction successfully executed.");
            } catch (FailedTransactionException e){
                model.addAttribute("error", e.getMessage());
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("transfers", transferService.myTransfers(user.getEmail()));
        model.addAttribute("connections", userService.getConnections(user));
        model.addAttribute("newTransfer", new TransferDTO(user));
        return TRANSFER;
    }

    @PostMapping("/transfer/bank")
    @ResponseBody
    public ResponseEntity<String> bankTransfer(@ModelAttribute("newTransfer") TransferDTO transfer, Model model){
        try{
            bankOperationService.bankOperation(transfer);
            return new ResponseEntity<>("Transaction successfully executed.", HttpStatus.OK);
        } catch (FailedTransactionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public String currentUserEmail(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal != null ? principal.getName() : null;
    }
}
