package com.works.jpawork.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String title;
    @Lob
    private Blob image;
    private Double price;
    private Integer stock;
    private String detail;
    private Long uid;
}
