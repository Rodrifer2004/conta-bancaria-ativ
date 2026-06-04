package com.example.conta_bancaria.entity;


import java.math.BigDecimal;

import org.hibernate.annotations.DialectOverride.Version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class ContaBancariaVersionada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String titular;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @Version(major = 0)
    @Column(nullable = false)
    private Integer version;

    
    public ContaBancariaVersionada() {}
    public ContaBancariaVersionada(long id, String titular, BigDecimal saldo, Integer version) {
        super();
        this.id = id;
        this.titular = titular;
        this.saldo = saldo;
        this.version = version;
    }   
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    
}