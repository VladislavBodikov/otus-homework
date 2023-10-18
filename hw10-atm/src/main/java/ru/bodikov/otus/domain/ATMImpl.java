package ru.bodikov.otus.domain;

import java.util.*;
import java.util.stream.Collectors;

public class ATMImpl implements ATM{


    private final Map<CurrencyAndBanknoteNominalKey, Cassette> cassettesMap = new HashMap<>();


    /**
     * тут опустим логику что мы вставляем карту, считыаем у нее баланс нужной валюты и так далее
     * сделаем поле с балансом карты\аккаунта
     */
    private int accountBalance;

    public ATMImpl(int accountBalance) {
        this.accountBalance = accountBalance;
    }


    public Cassette addCassette(Cassette cassette){
        return cassettesMap.putIfAbsent(cassette.getKey(), cassette);
    }

    @Override
    public boolean putMoney(Banknote banknote) {
        Cassette cassette = cassettesMap.get(banknote.getKey());
        if (cassette != null){
            accountBalance += banknote.getKey().getBanknoteNominal().getNominal();
            return cassette.addBanknote(banknote);
        }
        throw new IllegalArgumentException("ATM dont have suitable cassette for Banknote: " + banknote.getKey());
    }

    @Override
    public List<Banknote> putMoney(List<Banknote> banknotes) {
        List<Banknote> notInsertableBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes){
            try{
                putMoney(banknote);
            }
            catch (IllegalArgumentException e){
                notInsertableBanknotes.add(banknote);
            }
        }
        return notInsertableBanknotes;
    }

    @Override
    public List<Banknote> withdrawMoney(int value, Currency currency) {
        if (!isATMHasEnoughMoney(value,currency)){
            throw new RuntimeException("ATM dont have enough money");
        }

        if (getAccountBalance() < value){
            throw new RuntimeException("Dont have enough money at your balance");
        }

        // отфильтровываем кассеты только нужной валюты, в порядке возрастания номинала
        List<CurrencyAndBanknoteNominalKey> sortedByNominalKeys = getCassettesWithCurrencySortedByNominal(currency);
        ListIterator<CurrencyAndBanknoteNominalKey> iterator = sortedByNominalKeys.listIterator(sortedByNominalKeys.size());
        // запомним кассеты и количество купюр, которое надо будет из них извлечь
        Map<Cassette, Integer> banknoteCountToExtract = new HashMap<>();
        int leftToWithdraw = value;

        // 1. проходим по всем номиналам в порядке убывания
        while (iterator.hasPrevious()){
            CurrencyAndBanknoteNominalKey key = iterator.previous();
            Cassette cassette = cassettesMap.get(key);
            int nominal = key.getBanknoteNominal().getNominal();
            int banknotesLeftInCassette = cassette.getCountOfBanknotes();
            // 2. и пытаемся из них собрать сумму для съема
            while (leftToWithdraw >= nominal && banknotesLeftInCassette > 0){
                banknoteCountToExtract.computeIfPresent(cassette, (x, y) -> y + 1);
                banknoteCountToExtract.putIfAbsent(cassette, 1);
                leftToWithdraw -= nominal;
                banknotesLeftInCassette--;
            }
        }
        // 3.если не сможем, бросим исключение
        if (leftToWithdraw != 0){
            throw new RuntimeException("Please enter an amount that is a multiple of " + sortedByNominalKeys.get(0).getBanknoteNominal().getNominal());
        }
        return extractBanknotesFromCassettes(banknoteCountToExtract);
    }

    private List<CurrencyAndBanknoteNominalKey> getCassettesWithCurrencySortedByNominal(Currency currency) {
        return cassettesMap.keySet().stream()
                .filter(cassette -> cassette.getCurrency().equals(currency))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Banknote> extractBanknotesFromCassettes(Map<Cassette, Integer> banknoteCountToExtract){
        return banknoteCountToExtract.entrySet().stream()
                .flatMap(entry -> entry.getKey().extractBanknotes(entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public double getAccountBalance() {
        return accountBalance;
    }

    private boolean isATMHasEnoughMoney(int amount, Currency currency){
        Integer amountCurrencyAtATM = cassettesMap.entrySet().stream()
                .filter(entry -> entry.getKey().getCurrency().equals(currency))
                .map(entry -> entry.getKey().getBanknoteNominal().getNominal() * entry.getValue().getCountOfBanknotes())
                .reduce(Integer::sum)
                .orElseThrow(()->new IllegalArgumentException("ATM dont have money of this Currency"));
        return amountCurrencyAtATM >= amount;
    }


}
