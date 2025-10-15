package DesafiosDeProjeto.JavaBank.repository;

import static DesafiosDeProjeto.JavaBank.repository.CommonsRepository.checkFundsForTransaction;

import java.util.ArrayList;
import java.util.List;

import DesafiosDeProjeto.JavaBank.exception.AccountWithInvestmentsException;
import DesafiosDeProjeto.JavaBank.exception.InvestmentNotFoundException;
import DesafiosDeProjeto.JavaBank.exception.WalletNotFoundException;
import DesafiosDeProjeto.JavaBank.model.AccountWallet;
import DesafiosDeProjeto.JavaBank.model.Investment;
import DesafiosDeProjeto.JavaBank.model.InvestmentWallet;

public class InvestmentRepository {

    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long initialFunds){
        this.nextId++;
        Investment investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id){
        if(!wallets.isEmpty()){
            List<AccountWallet> accountInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountInUse.contains(account)) {
                throw new AccountWithInvestmentsException("A conta '" + account + "' já possui um investimento");
            }
        }
        
        Investment investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        InvestmentWallet wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds){
        InvestmentWallet wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investimento");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds){
        InvestmentWallet wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "saque de investimentos");
        if(wallet.getFunds() == 0){
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount(){
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public Investment findById(final long id){
        return investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("O investimento '" + id + "' não foi encontrado"));
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira não foi encontrada"));
    }

    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }

    public List<Investment> list() {
        return this.investments;
    }
}
