package DesafiosDeProjeto.JavaBank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DesafiosDeProjeto.JavaBank.exception.AccountNotFoundException;
import DesafiosDeProjeto.JavaBank.exception.NoFundsEnoughException;
import DesafiosDeProjeto.JavaBank.exception.WalletNotFoundException;
import DesafiosDeProjeto.JavaBank.model.AccountWallet;
import DesafiosDeProjeto.JavaBank.model.Investment;
import DesafiosDeProjeto.JavaBank.model.InvestmentWallet;
import DesafiosDeProjeto.JavaBank.model.MoneyAudit;
import DesafiosDeProjeto.JavaBank.repository.AccountRepository;
import DesafiosDeProjeto.JavaBank.repository.InvestmentRepository;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import java.time.OffsetDateTime;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args){
        
        while(true){
            System.out.println("Informe a opção desejada:");
            System.out.println("1: Criar uma conta");
            System.out.println("2: Criar um investimento");
            System.out.println("3: Fazer uma carteira de investimento");
            System.out.println("4: Depositar na conta");
            System.out.println("5: Sacar da conta");
            System.out.println("6: Transferência entre contas");
            System.out.println("7: Investir");
            System.out.println("8: Sacar investimento");
            System.out.println("9: Listar contas");
            System.out.println("10: Listar investimento");
            System.out.println("11: Listar carteiras de investimento");
            System.out.println("12: Atualizar investimentos");
            System.out.println("13: Histórico de conta");
            System.out.println("14: Sair");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transferToAccount();
                case 7 -> investment();
                case 8 -> rescueInvestment();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 13 -> checkHistory();
                case 14 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void createAccount(){
        System.out.print("Informe as chaves pix (separadas por ';'): ");
        List<String> pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.print("Informe o valor inicial do depósito: ");
        long amount = scanner.nextLong();
        AccountWallet wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment(){
        System.out.print("Informe a taxa do investimento: ");
        int tax = scanner.nextInt();
        System.out.print("Informe o valor inicial do depósito: ");
        long initialFunds = scanner.nextLong();
        Investment investiment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investiment);
    }

    private static void withdraw(){
        System.out.print("Informe a chave pix da conta para saque: ");
        String pix = scanner.next();
        System.out.print("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deposit(){
        System.out.print("Informe a chave pix da conta para depósito: ");
        String pix = scanner.next();
        System.out.print("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }   
    }

    private static void transferToAccount(){
        System.out.print("Informe a chave pix da conta de origem: ");
        String source = scanner.next();
        System.out.print("Informe a chave pix da conta de destino: ");
        String target = scanner.next();
        System.out.print("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }   
    }

    private static void createWalletInvestment(){
        System.out.print("Informe a chave pix da conta: ");
        String pix = scanner.next();
        AccountWallet account = accountRepository.findByPix(pix);
        System.out.print("Informe o identificador do investimento: ");
        int investment = scanner.nextInt();
        InvestmentWallet investmentWallet = investmentRepository.initInvestment(account, investment);
        System.out.println("Conta de investimento criada: " + investmentWallet);
    }

    private static void investment(){
        System.out.print("Informe a chave pix da conta para investimento: ");
        String pix = scanner.next();
        System.out.print("Informe o valor que será investido: ");
        long amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }   
    }

    private static void rescueInvestment(){
        System.out.print("Informe a chave pix da conta para resgate do investimento: ");
        String pix = scanner.next();
        System.out.print("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkHistory(){
        System.out.print("Informe a chave pix da conta para verificar o extrato: ");
        String pix = scanner.next();
        try {
            Map<OffsetDateTime, List<MoneyAudit>> history = accountRepository.getHistory(pix);
            history.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionID());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size() / 100) + "," + (v.size() % 100));
            });
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
