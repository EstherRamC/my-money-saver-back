package com.estherrc.my_money_saver.service;

import com.estherrc.my_money_saver.enumerator.TransactionType;
import com.estherrc.my_money_saver.model.Account;
import com.estherrc.my_money_saver.model.Transaction;
import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.repository.AccountRepository;
import com.estherrc.my_money_saver.repository.TransactionRepository;
import com.estherrc.my_money_saver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }
    public void addAccount(Account account) {
        User user = userRepository.findById(account.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        account.setUserId(account.getUserId());
        accountRepository.save(account);
    }

    public Account updateAccountDetails(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountName(accountDetails.getAccountName());
        account.setAccountType(accountDetails.getAccountType());
        return accountRepository.save(account);
    }
}