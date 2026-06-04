package com.example.conta_bancaria.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.conta_bancaria.entity.ContaBancariaVersionada;
import com.example.conta_bancaria.repository.ContaVersionadaRepository;

import jakarta.transaction.Transactional;

@Service
public class ContaVersionadaService {
    private final ContaVersionadaRepository repository;

    public ContaVersionadaService(ContaVersionadaRepository repository){
        this.repository = repository;
    }

    
    public ContaBancariaVersionada buscarConta(Long id){
        return repository.findById(id)
        .orElseThrow(() ->
                new RuntimeException("Conta não encontrada"));
    };

    public ContaBancariaVersionada depositar(Long id, BigDecimal valor)
    {
        ContaBancariaVersionada contaVersionada = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        BigDecimal saldoAtual = contaVersionada.getSaldo();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        BigDecimal novoSaldo = saldoAtual.add(valor);
        contaVersionada.setSaldo(novoSaldo);
        return repository.save(contaVersionada);
    }

    @Transactional
    public ContaBancariaVersionada sacar(Long id, BigDecimal valor)
    {
        ContaBancariaVersionada contaVersionada = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        BigDecimal saldoAtual = contaVersionada.getSaldo();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(saldoAtual.compareTo(valor) < 0)
            {
                throw new RuntimeException("saldo insuficiente");
            }
        BigDecimal novoSaldo = saldoAtual.subtract(valor);
        contaVersionada.setSaldo(novoSaldo);
        return repository.save(contaVersionada);
    }
}
