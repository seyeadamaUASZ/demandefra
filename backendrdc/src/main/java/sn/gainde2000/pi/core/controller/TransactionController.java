package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Transaction;
import sn.gainde2000.pi.core.service.TransactionService;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("transaction/list")
    public ServeurResponse listTransactions(){
        ServeurResponse response = new ServeurResponse();
        List<Transaction> transactions = transactionService.listTransactions();
        if(!transactions.isEmpty()){
            response.setStatut(true);
            response.setDescription("liste des transactions");
            response.setData(transactions);
        }else{
            response.setStatut(false);
            response.setDescription("not empty list !");
            response.setData(null);
        }
        return response;
    }
}
