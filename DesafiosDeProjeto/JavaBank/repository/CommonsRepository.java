package DesafiosDeProjeto.JavaBank.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import DesafiosDeProjeto.JavaBank.exception.NoFundsEnoughException;
import DesafiosDeProjeto.JavaBank.model.BankService;
import DesafiosDeProjeto.JavaBank.model.Money;
import DesafiosDeProjeto.JavaBank.model.MoneyAudit;
import DesafiosDeProjeto.JavaBank.model.Wallet;

public final class CommonsRepository {

    private CommonsRepository(){

    }

    public static void checkFundsForTransaction(final Wallet source, final long amount){
        if(source.getFunds() < amount){
            throw new NoFundsEnoughException("Sua conta não tem saldo suficiente para realizar esta transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactioID, final long funds, final String description){
        MoneyAudit history = new MoneyAudit(transactioID, BankService.ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }
}
