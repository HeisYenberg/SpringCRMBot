package com.heisyenberg.springcrmbot.repositories;

import com.heisyenberg.springcrmbot.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
}
