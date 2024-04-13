package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCompany(Company company);
}
