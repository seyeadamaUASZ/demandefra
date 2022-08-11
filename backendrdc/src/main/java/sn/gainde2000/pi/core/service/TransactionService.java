package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Transaction;

import java.util.List;

public interface TransactionService {
    public Transaction addTransaction(Transaction transaction);
    public List<Transaction> listTransactions();
    public Transaction getTransaction(Long id);
}
