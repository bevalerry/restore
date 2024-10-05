package com.example.restore.Controller;

import com.example.restore.Model.Basket;
import com.example.restore.Model.Product;
import com.example.restore.Services.Basket_Service;
import com.example.restore.Services.Product_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Base_Controller
{
    private final Product_Service productService;
    private final Basket_Service basketService;





    //MainPage
    @GetMapping("/") //первоначальная страница с добавлением атрибутов для вывода товаров
    public String index(Model model)
    {
        List<Product> products2 = productService.getAll();
        model.addAttribute("products", products2);
        model.addAttribute("id", 0);
        return "MainPage";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/send")//добавление товара из базы данных продуктов в базу данных корзины
    public String addBasket(@RequestParam(name = "id") int id)
    {
        if (basketService.getByID(id) != null)
        {
            basketService.add(new Basket(id,
                    basketService.getByID(id).getName_product(),
                    basketService.getByID(id).getPrice(), basketService.getSwap(id) + 1));
        }
        else
        {
            basketService.add(new Basket(id, productService.getProductName(id), productService.getPrice(id), 1));
        }

        return "redirect:/";
    }

    @GetMapping("/products/{id}/image")
    @ResponseBody
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        byte[] image = product.getImg();

        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);  // Или IMAGE_PNG в зависимости от формата изображения
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }










    //BasketPage
    @GetMapping("/basket") // страница корзины с добавлением атрибутов для вывода корзины
    public String getBusket(Model model) {
        List<Basket> baskets = basketService.getAll();
        baskets.sort(Comparator.comparing(Basket::getId)); //сортировка корзины по id товара
        model.addAttribute("baskets", baskets);
        model.addAttribute("id", 0);
        model.addAttribute("full_price", basketService.FullPrice());
        return "BasketPage";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add_basket")//добавление в коризну еще одного товара
    public String addOnBasketPage(@RequestParam(name = "id") int id)
    {

        basketService.add(new Basket(id,
                basketService.getByID(id).getName_product(),
                basketService.getByID(id).getPrice(), basketService.getSwap(id) + 1));
        return "redirect:/basket";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/del_basket")//удаление товара из корзины
    public String delOnBasketPage(Model model, @RequestParam(name = "id") int id)
    {
        if (basketService.getSwap(id) == 1) // смотрим сколько товаров в корзине добавлено
        {
            basketService.deleteByid(id);
        }
        else //удаляем позицию если остался лишь один товар
        {
            basketService.add(new Basket(id,
                    basketService.getByID(id).getName_product(),
                    basketService.getByID(id).getPrice(), basketService.getSwap(id) - 1));
        }
        return "redirect:/basket";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/clear") //очистить корзину
    public String clearBasket() {
        basketService.deleteBasket();
        return "redirect:/basket";
    }







    //AdminPage
    @GetMapping("/AdminPage")
    public String getProducts(Model model) {
        List<Product> products2 = productService.getAll();
        model.addAttribute("products", products2);
        model.addAttribute("id", 0);

        return "AdminPage";
    }
    @PostMapping("/add_Product")//Добавление товаров в базу данных Продуктов (Админ панель)
    public String addProduct( @RequestParam(name = "name_product_add") String name_product,
                              @RequestParam(name = "price_add") int price,
                              @RequestParam("imageFile") MultipartFile imageFile)
    {
        try {
            productService.add(name_product, price, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // возвращаем страницу ошибки в случае ошибки
        }
        return "redirect:/AdminPage";
    }
    @PostMapping("/delete_Product")//Удаление товаров в базу данных Продуктов (Админ панель)
    public String deleteItem(@RequestParam(name = "id") int id)
    {
        productService.delete(id);
        return "redirect:/AdminPage";
    }
    @PostMapping("/update_Product")
    public String updateProduct(@RequestParam(name = "id_add") int id,
                                @RequestParam(name = "price_update") int price)
    {
        productService.updateProduct(id, productService.getProductName(id), price);
        return "redirect:/AdminPage";
    }



}
