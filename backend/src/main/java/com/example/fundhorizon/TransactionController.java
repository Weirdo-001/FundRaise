package com.example.fundhorizon;

import com.example.fundhorizon.Transaction;
import com.example.fundhorizon.TransactionRepository;
import com.example.fundhorizon.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000") // Adjust if your React app runs on a different port
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<String> storeTransaction(@RequestBody TransactionRequest request) {
        if (request.getName() == null || request.getPhone() == null || request.getAmount() == null) {
            return new ResponseEntity<>("Name, phone, and amount are required.", HttpStatus.BAD_REQUEST);
        }
        try {
            BigDecimal amount = new BigDecimal(request.getAmount());
            Transaction transaction = new Transaction(request.getName(), request.getPhone(), amount);
            transaction.setTransactionDate(LocalDateTime.now()); // Ensure timestamp is set on the server
            transactionRepository.save(transaction);
            return new ResponseEntity<>("Transaction stored successfully!", HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid amount format.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to store transaction.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}

// Request DTO (Data Transfer Object) for receiving transaction data
class TransactionRequest {
    private String name;
    private String phone;
    private String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
