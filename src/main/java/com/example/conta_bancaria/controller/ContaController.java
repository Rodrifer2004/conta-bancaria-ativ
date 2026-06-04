package com.example.conta_bancaria.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.conta_bancaria.dto.ValorDTO;
import com.example.conta_bancaria.service.ContaBancariaService;
import com.example.entity.ContaBancaria;

@RequestMapping("/contas")
@RestController
public class ContaController 
{
    private final ContaBancariaService contaBancariaService;

    public ContaController(ContaBancariaService contaBancariaService)
    {
        this.contaBancariaService = contaBancariaService;
    }

    @PostMapping("/{id}/deposito")
    public ContaBancaria depositar(
        @PathVariable Long id,
        @RequestBody ValorDTO dto) 
        {
            return contaBancariaService.depositar(id, dto.getValor());
        }

    @PostMapping("/{id}/saque")
    public ContaBancaria sacar(
         @PathVariable Long id,
        @RequestBody ValorDTO dto) 
        {
            return contaBancariaService.sacar(id, dto.getValor());
        }
}
