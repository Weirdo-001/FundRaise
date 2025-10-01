package com.example.fundhorizon;

import com.example.fundhorizon.TransactionDTO;
import com.example.fundhorizon.Transaction;
import com.example.fundhorizon.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void storeTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO != null && transactionDTO.getName() != null && transactionDTO.getPhone() != null && transactionDTO.getAmount() != null) {
            Transaction transaction = new Transaction();
            transaction.setName(transactionDTO.getName());
            transaction.setPhone(transactionDTO.getPhone());
            transaction.setAmount(BigDecimal.valueOf(transactionDTO.getAmount())); // Assuming amount in DTO is Double
            transaction.setTransactionDate(LocalDateTime.now());
            transactionRepository.save(transaction);
        } else {
            // Handle invalid DTO or missing data appropriately (e.g., throw an exception)
            System.err.println("Invalid transaction data received.");
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // You might have other methods for retrieving, updating, or deleting transactions
}
