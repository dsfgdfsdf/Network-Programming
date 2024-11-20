package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;
    private String photoUrl;


    @Transient
    private BigDecimal monthlyOrderSum = BigDecimal.ZERO;
    @Transient
    private boolean maxOrderCustomer = false;

    @Transient
    private boolean noOrdersThisMonth = false;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> orders;


    public boolean isMaxOrderCustomer() {
        return maxOrderCustomer;
    }

    public void setMaxOrderCustomer(boolean maxOrderCustomer) {
        this.maxOrderCustomer = maxOrderCustomer;
    }

    public boolean isNoOrdersThisMonth() {
        return noOrdersThisMonth;
    }

    public void setNoOrdersThisMonth(boolean noOrdersThisMonth) {
        this.noOrdersThisMonth = noOrdersThisMonth;
    }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public BigDecimal getMonthlyOrderSum() {
        return monthlyOrderSum;
    }

    public void setMonthlyOrderSum(BigDecimal monthlyOrderSum) {
        this.monthlyOrderSum = monthlyOrderSum;
    }
}
