package com.example.restore.Repositories;

import com.example.restore.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_Repository extends JpaRepository<Product, Integer>
{
    @Query("select Bev.name_product from Product Bev where Bev.id = :id")
    String findProductById(@Param("id") int id);
    @Query("select Bev.price from Product Bev where Bev.id = :id")
    int findPriceById(@Param("id") int id);



}
