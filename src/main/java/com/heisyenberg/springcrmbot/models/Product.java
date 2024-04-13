package com.heisyenberg.springcrmbot.models;

import lombok.*;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(id.toString() + ')')
                .add(name)
                .add(description)
                .toString();
    }
}
