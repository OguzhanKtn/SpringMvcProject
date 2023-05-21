package com.works.jpawork.repositories;

import com.works.jpawork.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.title = ?1, p.price = ?2, p.stock = ?3, p.detail = ?4 where p.pid = ?5")
    void updateProduct(String title, Double price, Integer stock, String detail,Long pid);

    List<Product> findByTitleLikeIgnoreCaseOrDetailLikeIgnoreCase(String title, String detail);

    Page<Product> findByUidEquals(Long uid, Pageable pageable);




}