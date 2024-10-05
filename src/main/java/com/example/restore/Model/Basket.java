package com.example.restore.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")
public class Basket
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_id_seq")
    @SequenceGenerator(name = "basket_id_seq", sequenceName = "basket_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "name_product")
    String name_product;
    @Column(name = "price")
    int price;
    @Column(name = "swap")
    int swap;
}
