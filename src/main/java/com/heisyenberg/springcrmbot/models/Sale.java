package com.heisyenberg.springcrmbot.models;

import lombok.*;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add(client.getEmail())
                .add(product.getVendorCode())
                .add(quantity.toString())
                .add(price.toString())
                .toString();
    }
}
