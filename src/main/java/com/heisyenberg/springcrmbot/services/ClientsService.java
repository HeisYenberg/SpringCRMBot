package com.heisyenberg.springcrmbot.services;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public boolean addClient(Client client) {
        try {
            if (clientsRepository.existsByCompanyAndEmail(
                    client.getCompany(), client.getEmail())) {
                return false;
            }
            clientsRepository.save(client);
        } catch (Constants.ConstantException e) {
            return false;
        }
        return true;
    }

    public List<Client> getAllByCompany(Company company) {
        return clientsRepository.findAllByCompany(company);
    }

    public Optional<Client> getClientByEmail(Company company, String email) {
        return clientsRepository.findByCompanyAndEmail(company, email);
    }
}
