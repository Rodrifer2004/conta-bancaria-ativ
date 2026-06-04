package com.example.conta_bancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.conta_bancaria.entity.ContaBancariaVersionada;

public interface ContaVersionadaRepository extends JpaRepository<ContaBancariaVersionada, Long>{

}
