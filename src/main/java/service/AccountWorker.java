package service;

import com.example.playtox.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class AccountWorker extends Thread{
    private final Logger logger = LoggerFactory.getLogger(AccountWorker.class);
    private final Account account1;
    private final Account account2;
    private final TransferService transferService;

    private final int limitOperation;

    public AccountWorker(Account account1, Account account2, TransferService transferService) {
        this.account1 = account1;
        this.account2 = account2;
        this.transferService = transferService;
        this.limitOperation = 30;
    }

    @Override
    public void run(){
        Random random = new Random();
        String idFrom;
        String idTo;
        int counter = 0;

        while (counter < limitOperation){
            if (random.nextBoolean()){
                idFrom = account1.getID();
                idTo = account2.getID();
            } else {
                idFrom = account2.getID();
                idTo = account1.getID();
            }
            transferService.transfer(idFrom, idTo, random.nextInt(5000));
            counter++;
            logger.info("Account ID: {} | account balance is {}", account1.getID(), account1.getMoney());
            logger.info("Account ID: {} | account balance is {}", account2.getID(), account2.getMoney());

            try {
                Thread.sleep(random.nextInt(1000, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
