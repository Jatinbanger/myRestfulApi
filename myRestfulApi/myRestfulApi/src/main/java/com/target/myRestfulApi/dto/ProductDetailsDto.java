package com.target.myRestfulApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailsDto {

    private Long id;
    private String name;
    private PriceDetailsDto currentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceDetailsDto getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(PriceDetailsDto currentValue) {
        this.currentValue = currentValue;
    }
}
