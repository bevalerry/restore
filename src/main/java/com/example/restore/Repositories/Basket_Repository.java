package com.example.restore.Repositories;

import com.example.restore.Model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Basket_Repository extends JpaRepository<Basket, Integer> {
    @Query("select u from Basket u where u.id =:id")
    Basket findByID(@Param("id") int id);

    @Query("select u from Basket u")
    List<Basket> findall();
    @Query("select Bev.swap from Basket Bev where Bev.id = 1")
    int getFullValue();
    @Query("select Bev.swap from Basket Bev where Bev.id = :id")
    int findSwapById(@Param("id") int id);

    @Query("select Bev.id from Basket Bev where Bev.name_product = :name_product")
    int findIdByName(@Param("name_product") String name_product);
}
