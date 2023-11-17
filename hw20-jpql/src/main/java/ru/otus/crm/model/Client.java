package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        if (address != null){
            setAddress(address);
        }
        if (phones != null){
            setPhones(phones);
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name,this.address,this.phones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setAddress(Address address) {
        this.address = address;
        this.address.setClient(this);
    }

    public void setPhones(List<Phone> phones) {
        this.phones.clear();
        this.phones.addAll(phones);
        this.phones.forEach(p -> p.setClient(this));
    }
}
