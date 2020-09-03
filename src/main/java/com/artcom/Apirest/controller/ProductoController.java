package com.artcom.Apirest.controller;


import com.artcom.Apirest.dto.Mensaje;
import com.artcom.Apirest.dto.ProductoDto;
import com.artcom.Apirest.entity.Producto;
import com.artcom.Apirest.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.listPorducts();
        return new ResponseEntity<List<Producto>>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/detalle/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

//Este metodo es solo de ejemplo, no se usa nunca en el front
    @GetMapping("/detalle/{name}")
    public ResponseEntity<Producto> getByName(@PathVariable("name") String name){
        if(!productoService.existsByName(name))
            return new ResponseEntity(new Mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByName(name).get();
        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }

    //Validaciones
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getName()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrice() < 0)
            return new ResponseEntity(new Mensaje("El precio es obligatorio o debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByName(productoDto.getName()))
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        Producto producto = new Producto(productoDto.getName(), productoDto.getPrice());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id,@RequestBody ProductoDto productoDto){
        //Primero comprobamos que exista
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
        if(productoService.existsByName(productoDto.getName()) && productoService.getByName(productoDto.getName()).get().getId() != id)
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(productoDto.getName()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrice() < 0)
            return new ResponseEntity(new Mensaje("El precio es obligatorio o debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getOne(id).get();
        producto.setName(productoDto.getName());
        producto.setPrice(productoDto.getPrice());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto actualizado"), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe el producto"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }


}


