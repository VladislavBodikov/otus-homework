package ru.bodikov.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bodikov.otus.domain.*;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.bodikov.otus.domain.BanknoteNominal.*;

class ATMImplTest {

    ATMImpl atm;
    Currency rubCurrency = Currency.getInstance("RUB");
    int initAccountBalance = 100_000;

    @BeforeEach
    void init() {
        this.atm = new ATMImpl(initAccountBalance);
    }

    @Test
    void withdrawMoney_request700_atmHas7banknotesWith100Nominal() {
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_100), 7));

        List<Banknote> banknotes = atm.withdrawMoney(700, rubCurrency);

        Assertions.assertEquals(7,banknotes.size());
    }

    @Test
    void withdrawMoney_request600_atmHas1banknotesWith500NominalAnd6BanknotesWith100Nominal() {
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_500), 1));
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_100), 6));

        List<Banknote> banknotes = atm.withdrawMoney(600, rubCurrency);

        Assertions.assertEquals(2,banknotes.size());
        Assertions.assertEquals(1, (int) banknotes.stream().filter(banknote -> banknote.getKey().getBanknoteNominal().getNominal() == 500).count());
        Assertions.assertEquals(1, (int) banknotes.stream().filter(banknote -> banknote.getKey().getBanknoteNominal().getNominal() == 100).count());
    }

    @Test
    void withdrawMoney_request600_atmHas1banknotesWith500NominalAnd5BanknotesWith200Nominal() {
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_500), 1));
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_200), 5));

        Assertions.assertThrows(RuntimeException.class, () -> atm.withdrawMoney(600, rubCurrency));
    }

    @Test
    void putMoney_putUSDWhenDontHaveSuitableCassetteForThisValue_throwException() {
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_100), 0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> atm.putMoney(new Banknote(new CurrencyAndBanknoteNominalKey(Currency.getInstance("USD"), N_100))));
    }

    @Test
    void putMoney_put100Rub_successAcceptBanknote() {
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, N_100), 0));

        atm.putMoney(new Banknote(new CurrencyAndBanknoteNominalKey(Currency.getInstance("RUB"), N_100)));

        Assertions.assertEquals(initAccountBalance + 100,atm.getAccountBalance());
    }

    @Test
    void putMoneys() {
        int countOfBanknotes = 10;
        BanknoteNominal banknoteNominal = N_100;
        atm.addCassette(Cassette.createCasseteWithInitState(new CurrencyAndBanknoteNominalKey(rubCurrency, banknoteNominal), 0));
        List<Banknote> banknotes = Stream.generate(() -> new Banknote(new CurrencyAndBanknoteNominalKey(Currency.getInstance("RUB"), banknoteNominal))).limit(countOfBanknotes).collect(Collectors.toList());


        atm.putMoney(banknotes);


        Assertions.assertEquals(initAccountBalance + countOfBanknotes *  banknoteNominal.getNominal(),atm.getAccountBalance());
    }


    @Test
    void getAccountBalance() {
        Assertions.assertEquals(initAccountBalance,atm.getAccountBalance());
    }

}