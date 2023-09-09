package service;

import com.example.playtox.exception.NotFoundAccount;
import com.example.playtox.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.AccountRepository;

public class TransferService {

    private final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(String accountIDFrom, String accountIDTo, int moneyAmount) throws NotFoundAccount {
        logger.info("A transfer operation has been started. From ID: {} | To ID: {}", accountIDFrom, accountIDTo);

        Account accountFrom = accountRepository.findAccountByID(accountIDFrom).orElseThrow(NotFoundAccount::new);
        Account accountTo = accountRepository.findAccountByID(accountIDTo).orElseThrow(NotFoundAccount::new);

        accountFrom.withdrawMoney(moneyAmount);
        logger.info("The amount of money {} was withdraw from ID: {}", moneyAmount, accountIDFrom);
        accountTo.addMoney(moneyAmount);
        logger.info("The amount of money {} was add to ID: {}", moneyAmount, accountIDTo);
        logger.info("Account ID: {} | account balance is {}", accountIDFrom, accountFrom.getMoney());
        logger.info("Account ID: {} | account balance is {}", accountIDTo, accountTo.getMoney());
    }
}
