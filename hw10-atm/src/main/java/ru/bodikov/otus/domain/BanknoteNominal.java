package ru.bodikov.otus.domain;


import lombok.Getter;

public enum BanknoteNominal implements Comparable<BanknoteNominal>{

    N_1(1),
    N_5(5),
    N_50(50),
    N_100(100),
    N_200(200),
    N_500(500),
    N_1000(1000),
    N_5000(5000);


    @Getter
    private final int nominal;

    BanknoteNominal(int nominal) {
        this.nominal = nominal;
    }
}
