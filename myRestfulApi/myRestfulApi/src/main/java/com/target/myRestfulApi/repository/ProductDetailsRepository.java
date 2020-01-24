package com.target.myRestfulApi.repository;
import com.target.myRestfulApi.domain.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails , Long> {

    @Override ProductDetails getOne(Long aLong);

    @Query("Select P from ProductDetails P")
    List<ProductDetails> getAll();

    @Query("Select P.id, P.name from ProductDetails P where P.id=:productId")
    Object[][] getIdAndName(@Param("productId") Long productId);

    @Query("Select P.price, P.currencyCode from ProductDetails P where P.id=:productId")
    Object[][] getPriceDetails(@Param("productId") Long productId);
}
