package com.works.jpawork.controllers;

import com.works.jpawork.entities.Product;
import com.works.jpawork.entities.User;
import com.works.jpawork.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    final ProductService service ;
    final HttpServletRequest request;

    @GetMapping("/product")
    public String products(Model model, @RequestParam(defaultValue = "1") int page){
        model.addAttribute("product",service.productList(page));
        return "product";
    }

    @GetMapping("/addProduct")
    public String addProduct(Product product){
        User user = (User) request.getSession().getAttribute("user");
        Long id = user.getUid();
        product.setUid(id);
        service.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/deleteProduct/{pid}")
    public String deleteProduct(@PathVariable String pid){
        service.deleteProduct(pid);
        return "redirect:/product";
    }

    @GetMapping("/updateProduct")
    public String update(Product product){
        System.out.println(product.getPid());
        service.updateProduct(product);
        return "redirect:/product";
    }
}
