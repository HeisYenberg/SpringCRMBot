package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    boolean existsByCompanyAndVendorCode(Company company, String vendorCode);

    List<Product> findAllByCompany(Company company);

    Optional<Product> findByCompanyAndVendorCode(
            Company company, String vendorCode);
}
