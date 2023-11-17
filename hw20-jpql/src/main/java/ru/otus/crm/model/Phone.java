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
public class Phone {
    @Id
    @SequenceGenerator(name = "phone_gen",sequenceName = "phone_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "phone_gen")
    @Column(name = "id")
    private Long id;

    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}
