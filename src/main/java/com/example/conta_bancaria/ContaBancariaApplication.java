package com.example.conta_bancaria;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.conta_bancaria.entity.ContaBancaria;
import com.example.conta_bancaria.entity.ContaBancariaVersionada;
import com.example.conta_bancaria.repository.ContaBancariaRepository;
import com.example.conta_bancaria.repository.ContaVersionadaRepository;

@SpringBootApplication
public class ContaBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContaBancariaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ContaBancariaRepository repository, ContaVersionadaRepository repositoryVer)
	{
		return args -> 
		{
			if(repository.count() == 0)
				{
				ContaBancaria conta = new ContaBancaria();
				conta.setTitular("Rodrigo");
				conta.setSaldo(new BigDecimal("1000.00"));
				repository.save(conta);
				}
			if(repositoryVer.count() == 0)
				{
				ContaBancariaVersionada contaVersionada = new ContaBancariaVersionada();
				contaVersionada.setTitular("Andrew");
				contaVersionada.setSaldo(new BigDecimal("1000.00"));
				repositoryVer.save(contaVersionada);
				}
		};
	}
}
