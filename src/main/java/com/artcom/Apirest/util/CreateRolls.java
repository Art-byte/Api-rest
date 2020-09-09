package com.artcom.Apirest.util;

//Esta clase solo sirve para insertar dos registros a la base de datos

//SOLO DEBE EJECUTARSE UNA VEZ

/***
import com.artcom.Apirest.security.entity.Roll;
import com.artcom.Apirest.security.enums.RollName;
import com.artcom.Apirest.security.service.RollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRolls implements CommandLineRunner {

    @Autowired
    RollService rollService;

    @Override
    public void run(String... args) throws Exception {
        Roll rollAdmin = new Roll(RollName.ROLE_ADMIN);
        Roll rollUser = new Roll(RollName.ROLE_USER);

        rollService.save(rollAdmin);
        rollService.save(rollUser);


    }
}


 */