package com.target.myRestfulApi.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.target.myRestfulApi.domain.ProductDetails;
import com.target.myRestfulApi.dto.PriceDetailsDto;
import com.target.myRestfulApi.dto.ProductDetailsDto;
import com.target.myRestfulApi.dto.ResponseModel;
import com.target.myRestfulApi.repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service public class ProductDetailsService {

    @Autowired ProductDetailsRepository productDetailsRepository;

    @Transactional
    public ProductDetailsDto getProductbyId(Long productId) {

        ProductDetails productDetails = productDetailsRepository.getOne(productId);
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();

        PriceDetailsDto priceDetailsDto = new PriceDetailsDto();
        priceDetailsDto.setPrice(productDetails.getPrice());
        priceDetailsDto.setCurrencyCode(productDetails.getCurrencyCode());

        productDetailsDto.setId(productDetails.getId());
        productDetailsDto.setName(productDetails.getName());
        productDetailsDto.setCurrentValue(priceDetailsDto);

        return productDetailsDto;

    }

    @Transactional
    public void saveProductDetails(ProductDetailsDto productDetailsDto) {
        ProductDetails productDetails =  new ProductDetails();
     //   productDetails.setId(productDetailsDto.getId());
        productDetails.setName(productDetailsDto.getName());
        productDetails.setPrice(productDetailsDto.getCurrentValue().getPrice());
        productDetails.setCurrencyCode(productDetailsDto.getCurrentValue().getCurrencyCode());
        productDetailsRepository.saveAndFlush(productDetails);
    }

    @Transactional
    public void updateProductDetails(ProductDetailsDto productDetailsDto) {
        ProductDetails productDetails =  productDetailsRepository.getOne(productDetailsDto.getId());
        productDetails.setName(productDetailsDto.getName());
        productDetails.setPrice(productDetailsDto.getCurrentValue().getPrice());
        productDetails.setCurrencyCode(productDetailsDto.getCurrentValue().getCurrencyCode());
        productDetailsRepository.saveAndFlush(productDetails);
    }

    public String externalProductDetails() {

        final String uri = "https://redsky.target.com/v2/pdp/tcin/13860428?excludes="
                + "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,"
                + "question_answer_statistics";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return result.getBody();

    }

    public ProductDetailsDto getPriceAndProductDetailsAsMock(Long productId) {
        final String uri = "http://localhost:8080/products/price/" + productId;
        // call price api
        RestTemplate restTemplate = new RestTemplate();
        Map priceDetailsMap = (Map) restTemplate.getForObject(uri, ResponseModel.class).getBody();
        // get productdetails
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();
        Object[][] productDetails = productDetailsRepository.getIdAndName(productId);
        productDetailsDto.setId((new Long(productDetails[0][0].toString())));
        productDetailsDto.setName(productDetails[0][1].toString());
        BigDecimal price = BigDecimal.valueOf((Double) priceDetailsMap.get("price"));
        PriceDetailsDto priceDetailsDto = new PriceDetailsDto();
        priceDetailsDto.setPrice(price);
        priceDetailsDto.setCurrencyCode(priceDetailsMap.get("currencyCode").toString());
        productDetailsDto.setCurrentValue(priceDetailsDto);
        // combine
        return productDetailsDto;
    }

    public  PriceDetailsDto getPriceDetailsById(Long productId) {
        PriceDetailsDto priceDetailsDto = new PriceDetailsDto();

        Object[][] priceDetails = productDetailsRepository.getPriceDetails(productId);
        priceDetailsDto.setPrice((BigDecimal)priceDetails[0][0]);
        priceDetailsDto.setCurrencyCode((String)priceDetails[0][1]);

        return priceDetailsDto;
    }

}
