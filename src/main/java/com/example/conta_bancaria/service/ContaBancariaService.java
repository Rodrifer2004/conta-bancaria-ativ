package com.example.conta_bancaria.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.conta_bancaria.entity.ContaBancaria;
import com.example.conta_bancaria.repository.ContaBancariaRepository;

import jakarta.transaction.Transactional;

@Service
public class ContaBancariaService {
    private final ContaBancariaRepository repository;

    public ContaBancariaService(ContaBancariaRepository repository){
        this.repository = repository;
    }

    
    public ContaBancaria buscarConta(Long id){
        return repository.findById(id)
        .orElseThrow(() ->
                new RuntimeException("Conta não encontrada"));
    };

    public ContaBancaria depositar(Long id, BigDecimal valor)
    {
        ContaBancaria conta = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        BigDecimal saldoAtual = conta.getSaldo();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        BigDecimal novoSaldo = saldoAtual.add(valor);
        conta.setSaldo(novoSaldo);
        return repository.save(conta);
    }

    @Transactional
    public ContaBancaria sacar(Long id, BigDecimal valor)
    {
        ContaBancaria conta = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        BigDecimal saldoAtual = conta.getSaldo();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(saldoAtual.compareTo(valor) < 0)
            {
                throw new RuntimeException("saldo insuficiente");
            }
        BigDecimal novoSaldo = saldoAtual.subtract(valor);
        conta.setSaldo(novoSaldo);
        return repository.save(conta);
    }
}
