package com.estherrc.my_money_saver.service;

import com.estherrc.my_money_saver.enumerator.TransactionType;
import com.estherrc.my_money_saver.model.Account;
import com.estherrc.my_money_saver.model.Transaction;
import com.estherrc.my_money_saver.repository.AccountRepository;
import com.estherrc.my_money_saver.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        updateAccountBalance(savedTransaction);
        return savedTransaction;
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        List<Long> accountIds = accounts.stream().map(Account::getId).collect(Collectors.toList());
        return transactionRepository.findByAccountIdIn(accountIds);
    }

    private void updateAccountBalance(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal newBalance = account.getBalance();
        if (transaction.getTransactionType() == TransactionType.INCOME) {
            newBalance = newBalance.add(transaction.getAmount());
        } else if (transaction.getTransactionType() == TransactionType.EXPENSE) {
            newBalance = newBalance.subtract(transaction.getAmount());
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
