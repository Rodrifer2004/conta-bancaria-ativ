package com.example.conta_bancaria.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.conta_bancaria.dto.ValorDTO;
import com.example.conta_bancaria.entity.ContaBancariaVersionada;
import com.example.conta_bancaria.service.ContaVersionadaService;

@RequestMapping("/contas-versionadas")
@RestController
public class ContaVerController 
{
    private final ContaVersionadaService contaVersionadaService;

    public ContaVerController(ContaVersionadaService contaVersionadaService)
    {
        this.contaVersionadaService = contaVersionadaService;
    }

    @PostMapping("/{id}/deposito")
    public ContaBancariaVersionada depositar(
        @PathVariable Long id,
        @RequestBody ValorDTO dto) 
        {
            return contaVersionadaService.depositar(id, dto.getValor());
        }

    @PostMapping("/{id}/saque")
    public ContaBancariaVersionada sacar(
         @PathVariable Long id,
         @RequestBody ValorDTO dto) 
        {
            return contaVersionadaService.sacar(id, dto.getValor());
        }
}
