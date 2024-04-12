package com.heisyenberg.springcrmbot.models;

import lombok.*;

import javax.persistence.*;

@Entity(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
