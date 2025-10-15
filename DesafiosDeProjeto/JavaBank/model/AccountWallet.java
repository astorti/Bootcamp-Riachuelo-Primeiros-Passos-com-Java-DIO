package DesafiosDeProjeto.JavaBank.model;

import java.util.List;

public class AccountWallet extends Wallet{

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(BankService.ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(BankService.ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criação da conta");
    }

    public List<String> getPix(){
        return pix;
    }

    public void addMoney(final long amount, final String description){
        List<Money> money = generateMoney(amount, description);
        this.money.addAll(money);
    }

    @Override
    public String toString(){
        return super.toString() + "AccountWallet{" +
                "pix=" + pix +
                "}";
    }

}
