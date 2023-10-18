package ru.bodikov.otus.domain;

import ru.bodikov.otus.domain.Banknote;

import java.util.Currency;
import java.util.List;

public interface ATM {

    boolean putMoney(Banknote banknote);

    /**
     * return not insertable banknotes
     */
    List<Banknote> putMoney(List<Banknote> banknote);

    List<Banknote> withdrawMoney(int value, Currency currency);

    double getAccountBalance();
}
