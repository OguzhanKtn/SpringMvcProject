package com.works.jpawork.controllers;

import com.works.jpawork.entities.Product;
import com.works.jpawork.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {
    final ProductService productService;

    @GetMapping("/search")
    public String searchProduct(@RequestParam(defaultValue = "") String q, Model model,@RequestParam(defaultValue = "0") int page){
        model.addAttribute("q",q);
        Page<Product> prd = productService.searchProduct(q,page);
         model.addAttribute("product",prd);

        return "searchProduct";
    }
}
