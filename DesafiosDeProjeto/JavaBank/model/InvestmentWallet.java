package DesafiosDeProjeto.JavaBank.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class InvestmentWallet extends Wallet{

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(BankService.INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investimento");
    }

    public Investment getInvestment(){
        return investment;
    }

    public AccountWallet getAccount(){
        return account;
    }

    public void updateAmount(final long percent){
        long amount = getFunds() * percent / 100;
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", OffsetDateTime.now());
        List<Money> money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }

    @Override
    public String toString(){
        return super.toString() + "InvestmentWallet{" +
                "investment=" + investment +
                ", account=" + account +
                "}";
    }
}
