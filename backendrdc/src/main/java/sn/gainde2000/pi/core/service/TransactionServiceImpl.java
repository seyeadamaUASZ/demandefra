package sn.gainde2000.pi.core.service;

import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Transaction;
import sn.gainde2000.pi.core.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return this.repository.save(transaction);
    }

    @Override
    public List<Transaction> listTransactions() {
        return this.repository.findAll();
    }

    @Override
    public Transaction getTransaction(Long id) {
        return this.repository.findById(id).get();
    }
}
