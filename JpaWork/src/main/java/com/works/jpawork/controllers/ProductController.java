package com.works.jpawork.controllers;

import com.works.jpawork.entities.Product;
import com.works.jpawork.entities.User;
import com.works.jpawork.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    final ProductService service ;
    final HttpServletRequest request;

    List<Product> ls = new ArrayList<>();
    @GetMapping("/product")
    public String products(Model model, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("product",service.productList(page));
        return "product";
    }

    @GetMapping("/addProduct")
    public String addProduct(Product product) {
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
    @PostMapping("/imageAdd")
    public String imageAdd(@RequestParam("image") MultipartFile file) throws IOException, SQLException {
        byte[] fileBytes = file.getBytes();
        Blob blob = new SerialBlob(fileBytes);
        Product product = new Product();
        product.setImage(blob);
        return "redirect:/product";
    }

   /* @ResponseBody
    @GetMapping (value = "/getImage/{index}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage( @PathVariable int index ) throws IOException, SQLException {
        Blob blob = ls.get(index).getImage();
        int blobLength = (int) blob.length();
        byte[] image = blob.getBytes(1, blobLength);
        return image;
    }*/


}
