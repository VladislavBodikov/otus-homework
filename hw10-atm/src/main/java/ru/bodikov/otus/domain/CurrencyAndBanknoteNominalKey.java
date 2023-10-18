package ru.bodikov.otus.domain;

import lombok.Value;

import java.util.Currency;


@Value
public class CurrencyAndBanknoteNominalKey implements Comparable<CurrencyAndBanknoteNominalKey> {

    Currency currency;
    BanknoteNominal banknoteNominal;

    @Override
    public int compareTo(CurrencyAndBanknoteNominalKey o) {
        return Integer.compare(banknoteNominal.getNominal(), o.getBanknoteNominal().getNominal());
    }
}
