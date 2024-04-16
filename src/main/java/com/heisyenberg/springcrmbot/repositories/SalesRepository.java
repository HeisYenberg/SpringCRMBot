package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByCompany(Company company);

    List<Sale> findAllByCompanyAndClient(Company company, Client client);

    List<Sale> findAllByCompanyAndProduct(Company company, Product product);
}
