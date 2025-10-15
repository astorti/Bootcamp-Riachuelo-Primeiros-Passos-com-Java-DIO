package DesafiosDeProjeto.JavaBank.model;

import java.util.ArrayList;
import java.util.List;

public class Money {

    private final List<MoneyAudit> history = new ArrayList<>();

    public Money(final MoneyAudit history){
        this.history.add(history);
    }

    public List<MoneyAudit> getHistory(){
        return history;
    }

    public void addHistory(final MoneyAudit history){
        this.history.add(history);
    }
}
