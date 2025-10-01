package com.example.fundhorizon;

public class TransactionDTO {
    private String name;
    private String phone;
    private Double amount; // Or BigDecimal depending on your precision needs

    // Default constructor (required for some frameworks)
    public TransactionDTO() {
    }

    // Constructor with fields
    public TransactionDTO(String name, String phone, Double amount) {
        this.name = name;
        this.phone = phone;
        this.amount = amount;
    }

    // Getters and setters for the fields
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // You might also have other relevant fields and methods
}

