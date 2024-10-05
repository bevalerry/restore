package com.example.restore.Services;

import com.example.restore.Model.Basket;
import com.example.restore.Repositories.Basket_Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class Basket_Service {
    @Autowired
    private Basket_Repository basketRepository;
    public void add(Basket basket) {
        basketRepository.saveAndFlush(basket);

    }
    public Basket getByID(int id)
    {
        return  basketRepository.findByID(id);
    }

    public void deleteByid(int id) {
        basketRepository.deleteById(id);
    }
    public void deleteBasket()
    {
        basketRepository.deleteAll();
    }

    public List<Basket> getAll() {

        return basketRepository.findall();
    }
    public  int getSwap(int id)
    {
        return basketRepository.findSwapById(id);
    }
    public int findIdByName(String name_product)
    {
        return basketRepository.findIdByName(name_product);
    }
    public int FullPrice()
    {
        List<Basket> baskets = basketRepository.findAll();
        int chet = 0;
        for(Basket basket : baskets)
        {

            if (basket.getSwap() > 1)
            {
                chet+=basket.getPrice() * basket.getSwap();
            }
            else
            {
                chet += basket.getPrice();
            }
        }
        return chet;
    }
}
