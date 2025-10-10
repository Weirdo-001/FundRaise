package com.example.fundRaise;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> storeTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        transactionService.storeTransaction(transactionDTO);
        return new ResponseEntity<>("Transaction stored successfully!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        // In a real app, this should probably be an admin-only endpoint
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
