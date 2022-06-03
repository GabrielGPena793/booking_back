package com.dh.PI.controllers;


import com.dh.PI.dto.ImageDTO;
import com.dh.PI.dto.productsDTO.ProductRequestDTO;
import com.dh.PI.dto.productsDTO.ProductResponseDTO;
import com.dh.PI.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.status(201).body(service.create(productRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable ){
        return ResponseEntity.status(200).body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.status(201).body(service.update(productRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> addImages(@PathVariable Long id, @RequestBody Set<ImageDTO> imageDTOS){
        return ResponseEntity.status(201).body(service.addImages(id, imageDTOS));
    }
    @GetMapping("/category")
    public Page<ProductResponseDTO> findAllProductsByCategory(@RequestParam String qualification,
                                                              @PageableDefault(page = 0, size = 10) Pageable pageable){
        return service.findAllProductsByCategory(qualification, pageable);
    }

    @GetMapping("/city")
    public Page<ProductResponseDTO> findAllProductsByCity(@RequestParam String name,
                                                              @PageableDefault(page = 0, size = 10) Pageable pageable){
        return service.findAllProductsByCity(name, pageable);
    }

    @GetMapping("/city/date")
    public ResponseEntity<List<ProductResponseDTO>> findByNameBetweenDate(@RequestParam String cityName,
                                                                          @RequestParam String init,
                                                                          @RequestParam String end){

        return ResponseEntity.status(200).body(service.findByNameBetweenDate(cityName,init,end));
    }
}
