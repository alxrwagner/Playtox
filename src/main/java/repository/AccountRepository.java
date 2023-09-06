package repository;

import com.example.playtox.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TransferService;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<String, Account>();
    }

    public Account findAccountByID(String id){
        return accounts.get(id);
    }

    public boolean checkingAccount(String id){
        return accounts.containsKey(id);
    }

    public void addAccount(Account account){
        accounts.put(account.getID(), account);
    }
}
