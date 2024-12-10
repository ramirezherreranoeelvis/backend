package com.ex.backend.employe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.backend.model.Ingreso;

@Repository
public interface IEntryRepository extends JpaRepository<Ingreso, Integer> {

}
