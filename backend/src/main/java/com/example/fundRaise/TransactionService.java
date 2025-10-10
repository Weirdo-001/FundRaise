package com.example.fundRaise;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void storeTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setName(transactionDTO.getName());
        transaction.setPhone(transactionDTO.getPhone());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
