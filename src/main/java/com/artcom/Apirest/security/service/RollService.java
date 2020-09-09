package com.artcom.Apirest.security.service;


import com.artcom.Apirest.security.entity.Roll;
import com.artcom.Apirest.security.enums.RollName;
import com.artcom.Apirest.security.repository.RollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RollService {

    @Autowired
    RollRepository rollRepository;

    public Optional<Roll> getByRollName (RollName rollName){
        return rollRepository.findByRollName(rollName);
    }

    public void save(Roll roll){
        rollRepository.save(roll);
    }
}
