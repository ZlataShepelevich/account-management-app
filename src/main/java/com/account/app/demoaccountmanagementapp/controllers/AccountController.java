package com.account.app.demoaccountmanagementapp.controllers;

import com.account.app.demoaccountmanagementapp.models.Account;
import com.account.app.demoaccountmanagementapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountOpt = accountService.findByUsername(username);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            return ResponseEntity.ok(Map.of("balance", account.getBalance()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam int accountId, @RequestParam double amount) {
        accountService.deposit(accountId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam int accountId, @RequestParam double amount) {
        accountService.withdraw(accountId, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @PostMapping("/block")
    public ResponseEntity<?> block(@RequestParam int accountId) {
        accountService.blockAccount(accountId);
        return ResponseEntity.ok("Account blocked");
    }

    @PostMapping("/unblock")
    public ResponseEntity<?> unblock(@RequestParam int accountId) {
        accountService.unblockAccount(accountId);
        return ResponseEntity.ok("Account unblocked");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable int accountId) {
        Optional<Account> accountOpt = accountService.findByIdAcc(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("balance", account.getBalance());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
