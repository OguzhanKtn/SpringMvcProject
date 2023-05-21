package com.works.jpawork.services;

import com.works.jpawork.entities.Product;
import com.works.jpawork.entities.User;
import com.works.jpawork.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;
    final HttpServletRequest request;

    public Page<Product> productList(int page) {
        User user = (User) request.getSession().getAttribute("user");
        Long uid = user.getUid();
        Pageable pageable = PageRequest.of(page,2);
        Page<Product> productPage = productRepository.findByUidEquals(uid,pageable);
        return productPage;
    }

    public Product saveProduct(Product product){
       return productRepository.save(product);
    }

    public List<Product> searchProduct(String q){
       return productRepository.findByTitleLikeIgnoreCaseOrDetailLikeIgnoreCase(q,q);
    }

    public void deleteProduct(String id){
        try{
            long Id = Long.parseLong(id);
            productRepository.deleteById(Id);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public void updateProduct(Product product){
        productRepository.updateProduct(product.getTitle(),product.getPrice(),product.getStock(),product.getDetail(),product.getPid());
    }
}