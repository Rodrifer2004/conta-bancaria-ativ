package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "saldo", precision = 15, scale = 2)
    private BigDecimal saldo;

    
    public ContaBancaria() {}
    public ContaBancaria(long id, BigDecimal saldo) {
        super();
        this.id = id;
        this.saldo = saldo;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    
}
