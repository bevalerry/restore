package com.example.restore.Services;

import com.example.restore.Model.Product;
import com.example.restore.Repositories.Product_Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class Product_Service {

    @Autowired
    private Product_Repository productRepository;
    public void add(String product_name, int price, MultipartFile imageFile) throws IOException {
        Product product = new Product();
        product.setName_product(product_name);
        product.setPrice(price);

        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageData = imageFile.getBytes();
            product.setImg(imageData);
        }

        productRepository.save(product);
    }



    public void delete(int id) {
        productRepository.deleteById(id);
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void updateProduct(int id, String product_name, int price)
    {
        Product product = productRepository.getById(id);
        product.setName_product(product_name);
        product.setPrice(price);
        productRepository.save(product);
    }

    public int getPrice(int id)
    {
        return productRepository.findPriceById(id);
    }

    public String getProductName(int id)
    {
        return productRepository.findProductById(id);
    }
    public Product getProduct(Integer id)
    {
        return productRepository.getById(id);
    }

    public Optional<Product> findById(Integer id) {
       return productRepository.findById(id);
    }
}
