package com.estherrc.my_money_saver.controller;

import com.estherrc.my_money_saver.model.Account;
import com.estherrc.my_money_saver.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/user/{userId}")
    public List<Account> getAccountsByUserId(@PathVariable Long userId) {
        return accountService.getAccountsByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        try {
            accountService.addAccount(account);
            return ResponseEntity.ok("Account added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding account: " + e.getMessage());
        }
    }

    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<Account> updateAccountDetails(@PathVariable Long id, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccountDetails(id, accountDetails);
        return ResponseEntity.ok(updatedAccount);
    }
}