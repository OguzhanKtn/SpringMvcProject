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
        Pageable pageable = PageRequest.of(page,100);
        Page<Product> productPage = productRepository.findByUidEquals(uid,pageable);
        return productPage;
    }

    public Product saveProduct(Product product){
       return productRepository.save(product);
    }

    public Page<Product> searchProduct(String q, int page){
        User user = (User) request.getSession().getAttribute("user");
        Long uid = user.getUid();
        q = "%"+q+"%";
        try{
            Pageable pageable = PageRequest.of(page,50);
           Page<Product> prd = productRepository.findByTitleLikeIgnoreCaseAndUidEqualsOrDetailLikeIgnoreCaseAndUidEquals(q,uid,q,uid,pageable);
           return prd;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
       return null;
    }

    public void deleteProduct(String id){
        try{
            User user = (User) request.getSession().getAttribute("user");
            Long pid = Long.parseLong(id);
            Long uid = user.getUid();
            boolean status = productRepository.existsByPidEqualsAndUidEquals(pid,uid);
            if(status){
                productRepository.deleteById(pid);
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public void updateProduct(Product product){
        try{
            User user = (User) request.getSession().getAttribute("user");
            Long pid = product.getPid();
            Long uid = user.getUid();
            boolean status = productRepository.existsByPidEqualsAndUidEquals(pid,uid);
            if(status){
                productRepository.updateProduct(product.getTitle(),product.getPrice(),product.getStock(),product.getDetail(),product.getPid());
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }
}