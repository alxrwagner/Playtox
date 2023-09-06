package repository;

import com.example.playtox.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
public class AccountRepository {
    private final Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<String, Account>();
    }

    public Optional<Account> findAccountByID(String id){
        return Optional.of(accounts.get(id));
    }
    public void addAccount(Account account){
        accounts.put(account.getID(), account);
    }
}
