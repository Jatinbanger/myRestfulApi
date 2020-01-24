package com.target.myRestfulApi.controller;

import com.target.myRestfulApi.dto.ProductDetailsDto;
import com.target.myRestfulApi.service.ProductDetailsService;
import com.target.myRestfulApi.dto.ResponseModel;
import com.target.myRestfulApi.repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired ProductDetailsService productDetailsService;

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @GetMapping("products/{id}")
    public ResponseModel getProductDetailsById(@PathVariable("id") Long productId) {
        ResponseModel result = new ResponseModel();
        result.setBody(productDetailsService.getProductbyId(productId));
        return result;
    }

    @PostMapping("products/{id}")
    public ResponseModel saveProductDetails(@PathVariable("id") Long productId, @RequestBody ProductDetailsDto productDetailsDto) {
        ResponseModel result = new ResponseModel();
        productDetailsService.saveProductDetails(productDetailsDto);
        return result;
    }

    @PutMapping("products/{id}")
    public ResponseModel updateProductDetails(@PathVariable("id") Long productId, @RequestBody ProductDetailsDto productDetailsDto) {
        ResponseModel result = new ResponseModel();
        productDetailsService.updateProductDetails(productDetailsDto);
        return result;
    }

    @GetMapping("externalproduct")
    public ResponseModel getExternalProductDetails() {
        ResponseModel result = new ResponseModel();
        result.setBody(productDetailsService.externalProductDetails());
        return result;
    }

    @GetMapping("products/{id}/price")
    public ResponseModel getProductAndPriceDetailsById(@PathVariable("id") Long productId) {
        ResponseModel result = new ResponseModel();
        result.setBody(productDetailsService.getPriceAndProductDetailsAsMock(productId));
        return result;
    }

    @GetMapping("products/price/{id}")
    public ResponseModel getPriceDetailsById(@PathVariable("id") Long productId) {
        ResponseModel result = new ResponseModel();
        result.setBody(productDetailsService.getPriceDetailsById(productId));
        return result;
    }



    @GetMapping("")
    public ResponseModel getAllProductDetails() {
        ResponseModel result = new ResponseModel();
        result.setBody(productDetailsRepository.getAll());
        return result;
    }






}
