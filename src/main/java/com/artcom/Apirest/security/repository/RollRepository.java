package com.artcom.Apirest.security.repository;

import com.artcom.Apirest.security.entity.Roll;
import com.artcom.Apirest.security.enums.RollName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RollRepository extends JpaRepository <Roll, Integer>{

    Optional<Roll> findByRollName(RollName rollName);
}
