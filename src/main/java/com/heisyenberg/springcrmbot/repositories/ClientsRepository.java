package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
    List<Client> findByCompany(Company company);
}
