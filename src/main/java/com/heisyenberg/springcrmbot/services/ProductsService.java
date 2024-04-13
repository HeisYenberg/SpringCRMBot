package com.heisyenberg.springcrmbot.services;

import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public boolean addProduct(Product product) {
        try {
            productsRepository.save(product);
        } catch (Constants.ConstantException e) {
            return false;
        }
        return true;
    }

    public List<Product> getProducts(Company company) {
        return productsRepository.findAllByCompany(company);
    }
}
