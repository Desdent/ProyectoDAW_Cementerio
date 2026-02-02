package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Ciudad repository.
 */
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    /**
     * Find all by provincia id list.
     *
     * @param provinciaId the provincia id
     * @return the list
     */
    List<Ciudad> findAllByProvinciaId(Long provinciaId);

}
