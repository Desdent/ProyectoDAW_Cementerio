package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findAllByProvinciaId(Long provinciaId);

}
