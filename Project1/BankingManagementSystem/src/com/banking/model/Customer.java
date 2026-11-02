package com.banking.model;

/** Represents a bank customer (table: customers). */
public class Customer {

    private int customerId;
    private String customerName;
    private String email;
    private String phone;

    public Customer(String customerName, String email, String phone) {
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int customerId, String customerName, String email, String phone) {
        this(customerName, email, phone);
        this.customerId = customerId;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Customer ID : " + customerId + "\n"
             + "Name        : " + customerName + "\n"
             + "Email       : " + email + "\n"
             + "Phone       : " + phone;
    }
}
