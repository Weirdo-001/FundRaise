package com.example.fundhorizon;


import com.example.fundhorizon.TransactionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    // Default constructor (required by JPA)
    public Transaction() {
    }

    public Transaction(String name, String phone, BigDecimal amount) {
        this.name = name;
        this.phone = phone;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

	public Transaction(Long id, String name, String phone, BigDecimal amount, LocalDateTime transactionDate) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}
    
}
