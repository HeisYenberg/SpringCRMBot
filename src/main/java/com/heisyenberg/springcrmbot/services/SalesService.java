package com.heisyenberg.springcrmbot.services;

import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.models.Sale;
import com.heisyenberg.springcrmbot.repositories.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesService {
    private final ClientsService clientsService;
    private final ProductsService productsService;
    private final SalesRepository salesRepository;

    @Autowired
    public SalesService(ClientsService clientsService,
                        ProductsService productsService,
                        SalesRepository salesRepository) {
        this.clientsService = clientsService;
        this.productsService = productsService;
        this.salesRepository = salesRepository;
    }

    public boolean addSale(String clientEmail, String vendorCode,
                           String quantity, Company company) {
        try {
            Optional<Client> client = clientsService
                    .getClientByEmail(company, clientEmail);
            Optional<Product> product = productsService
                    .getProductByVendorCode(company, vendorCode);
            if (!client.isPresent() || !product.isPresent()) {
                return false;
            }
            int quantityInt = Integer.parseInt(quantity);
            Sale sale = new Sale();
            sale.setClient(client.get());
            sale.setProduct(product.get());
            sale.setQuantity(quantityInt);
            sale.setPrice(product.get().getPrice() * quantityInt);
            salesRepository.save(sale);
        } catch (Constants.ConstantException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    public List<Sale> getSales(Company company) {
        return salesRepository.findAllByCompany(company);
    }

    public List<Sale> getClientSales(Company company, String clientEmail) {
        Optional<Client> client =
                clientsService.getClientByEmail(company, clientEmail);
        if (!client.isPresent()) {
            return new ArrayList<>();
        }
        return salesRepository.findAllByCompanyAndClient(company, client.get());
    }

    public List<Sale> getProductSales(Company company, String vendorCode) {
        Optional<Product> product =
                productsService.getProductByVendorCode(company, vendorCode);
        if (!product.isPresent()) {
            return new ArrayList<>();
        }
        return salesRepository.findAllByCompanyAndProduct(company,
                product.get());
    }
}
