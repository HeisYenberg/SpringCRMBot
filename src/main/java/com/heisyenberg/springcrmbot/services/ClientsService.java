package com.heisyenberg.springcrmbot.services;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public void addClient(Client client) {
        clientsRepository.save(client);
    }

    public List<Client> getAllByCompany(Company company) {
        return clientsRepository.findByCompany(company);
    }
}
