package com.heisyenberg.springcrmbot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

@Entity(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @Column(nullable = false)
    private String email;
    @Pattern(regexp = "^\\+7\\d{10}$")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add(firstName)
                .add(lastName)
                .add(email)
                .add(phone)
                .toString();
    }
}
