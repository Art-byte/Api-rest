package com.artcom.Apirest.security.controller;


import com.artcom.Apirest.dto.Mensaje;
import com.artcom.Apirest.security.dto.JwtDto;
import com.artcom.Apirest.security.dto.LoginUser;
import com.artcom.Apirest.security.dto.NewUser;
import com.artcom.Apirest.security.entity.Roll;
import com.artcom.Apirest.security.entity.User;
import com.artcom.Apirest.security.enums.RollName;
import com.artcom.Apirest.security.jwt.JwtProvider;
import com.artcom.Apirest.security.service.RollService;
import com.artcom.Apirest.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RollService rollService;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/new")
    //@CrossOrigin("*")
    public ResponseEntity<?> New (@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);

        if(userService.existsByUserName(newUser.getUserName()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        if(userService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);

        User user = new User(
                newUser.getName(),
                newUser.getUserName(),
                newUser.getEmail(),
                passwordEncoder.encode(newUser.getPassword())
                );

        Set<Roll> rolls = new HashSet<>();
        rolls.add(rollService.getByRollName(RollName.ROLE_USER).get());

        //El usuario lo tenemos que indicar desde el json dentro del arreglo
        if(newUser.getRolls().contains("admin"))
            rolls.add(rollService.getByRollName(RollName.ROLE_ADMIN).get());
        user.setRolls(rolls);
        userService.save(user);
        return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos "), HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(),
                        loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(
                jwt,
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}




















