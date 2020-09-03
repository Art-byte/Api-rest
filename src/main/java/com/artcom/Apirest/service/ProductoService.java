package com.artcom.Apirest.service;

import com.artcom.Apirest.entity.Producto;
import com.artcom.Apirest.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productRepository;

    public List<Producto> listPorducts(){
        return productRepository.findAll();
    }

    public Optional<Producto> getOne(int id){
        return productRepository.findById(id);
    }

    public Optional<Producto> getByName(String name){
        return productRepository.findByName(name);
    }

    public void save(Producto producto){
        productRepository.save(producto);
    }

    public void delete(int id){
        productRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return productRepository.existsByName(name);
    }

}
