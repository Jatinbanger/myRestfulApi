package com.target.myRestfulApi;

import com.target.myRestfulApi.dto.ProductDetailsDto;
import com.target.myRestfulApi.service.ProductDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;

@SpringBootTest
@Transactional
class MyRestfulApiApplicationTests {

	@Autowired ProductDetailsService productDetailsService;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkForProductDetailsById() {
		ProductDetailsDto productDetailsDto = productDetailsService.getProductbyId(1L);
		if (productDetailsDto != null) {
			Assert.isTrue(productDetailsDto.getId().equals(1L));
		}
	}

}
