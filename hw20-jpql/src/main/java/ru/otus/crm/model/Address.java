package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @SequenceGenerator(name = "address_gen",sequenceName = "address_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "address_seq")
    @Column(name = "id")
    private Long id;
    private String street;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;
//    private Long client_id;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }
}
