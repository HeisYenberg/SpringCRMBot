package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
    boolean existsByCompanyAndEmail(Company company, String email);

    List<Client> findAllByCompany(Company company);

    Optional<Client> findByCompanyAndEmail(Company company, String email);
}
