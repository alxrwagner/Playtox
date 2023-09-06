package service;

import com.example.playtox.exception.NotEnoughMoneyException;
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

    public void transfer(String accountIDFrom, String accountIDTo, int moneyAmount) {
        logger.info("A transfer operation has been started. From ID: {} | To ID: {}", accountIDFrom, accountIDTo);

        if (accountRepository.checkingAccount(accountIDFrom)) {
            Account accountFrom = accountRepository.findAccountByID(accountIDFrom);

            if (accountRepository.checkingAccount(accountIDTo)) {
                if (checkMoneyAmount(accountFrom, moneyAmount)) {
                    Account accountTo = accountRepository.findAccountByID((accountIDTo));
                    accountFrom.getLock().writeLock().lock();
                    accountTo.getLock().writeLock().lock();

                    try {
                        accountFrom.withdrawMoney(moneyAmount);
                        logger.info("The amount of money {} was withdraw from ID: {}", moneyAmount, accountIDFrom);
                        accountTo.addMoney(moneyAmount);
                        logger.info("The amount of money {} eas add to ID: {}", moneyAmount, accountIDTo);
                        logger.info("Account ID: {} | account balance is {}", accountIDFrom, accountFrom.getMoney());
                        logger.info("Account ID: {} | account balance is {}", accountIDTo, accountTo.getMoney());
                    } finally {
                        accountFrom.getLock().writeLock().unlock();
                        accountTo.getLock().writeLock().unlock();
                    }
                } else {
                    throw new NotEnoughMoneyException("Not enough money in the account " + accountIDFrom);
                }
            } else {
                throw new NotFoundAccount("Not found account " + accountIDTo);
            }
        }else {
            throw new NotFoundAccount("Not found account " + accountIDFrom);
        }
    }

    private boolean checkMoneyAmount(Account account, int moneyAmount) {
        return account.getMoney() >= moneyAmount;
    }
}
