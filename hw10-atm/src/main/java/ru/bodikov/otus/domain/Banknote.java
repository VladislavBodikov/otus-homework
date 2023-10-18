package ru.bodikov.otus.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class Banknote{

    CurrencyAndBanknoteNominalKey key;
    String uniqueSerialNumber;

    public Banknote(CurrencyAndBanknoteNominalKey key) {

        this.key = key;
        this.uniqueSerialNumber = key.getCurrency().hashCode() + "_" + key.getBanknoteNominal().hashCode() + "_" + UUID.randomUUID();
    }

}
