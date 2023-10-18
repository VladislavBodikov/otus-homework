package ru.bodikov.otus.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Getter
public class Cassette {

    CurrencyAndBanknoteNominalKey key;
    ArrayDeque<Banknote> banknotes = new ArrayDeque<>();

    public Cassette(CurrencyAndBanknoteNominalKey key) {
        this.key = key;
    }

    public Banknote extractBanknote(){
        return banknotes.pop();
    }

    public List<Banknote> extractBanknotes(int count){
        if (count > banknotes.size()){
            throw new RuntimeException("Cassette dont have enough banknotes: " + count);
        }

        List<Banknote> out = new ArrayList<>();
        for (int i = 0; i < count; i++){
            out.add(extractBanknote());
        }
        return out;
    }

    public boolean addBanknote(Banknote banknote){
        return banknotes.add(banknote);
    }

    public int getCountOfBanknotes(){
        return banknotes.size();
    }

    public static Cassette createCasseteWithInitState(CurrencyAndBanknoteNominalKey key , int countOfBanknotes){
        Cassette cassette = new Cassette(key);
        for (int i = 0; i < countOfBanknotes; i++) {
            cassette.addBanknote(new Banknote(key));
        }
        return cassette;
    }


}
