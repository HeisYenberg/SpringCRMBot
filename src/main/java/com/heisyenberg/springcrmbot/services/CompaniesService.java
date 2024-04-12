package com.heisyenberg.springcrmbot.services;

import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.repositories.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompaniesService {
    private final CompaniesRepository companiesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CompaniesService(CompaniesRepository companiesRepository,
                            PasswordEncoder passwordEncoder) {
        this.companiesRepository = companiesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Company> login(String email, String password) {
        Optional<Company> company = companiesRepository.findByEmail(email);
        if (company.isPresent()) {
            if (passwordEncoder.matches(password,
                    company.get().getPassword())) {
                return company;
            }
        }
        return Optional.empty();
    }

    public Optional<Company> register(String name, String email,
                                      String password) {
        Optional<Company> company = companiesRepository.findByEmail(email);
        if (!company.isPresent()) {
            Company newCompany = new Company(null, name, email,
                    passwordEncoder.encode(password));
            return Optional.of(companiesRepository.save(newCompany));
        }
        return Optional.empty();
    }
}
