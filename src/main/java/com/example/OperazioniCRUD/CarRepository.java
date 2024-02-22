package com.example.OperazioniCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAll();
}
