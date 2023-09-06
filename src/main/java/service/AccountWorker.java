package service;

import com.example.playtox.model.Account;

import java.util.concurrent.ThreadLocalRandom;

public class AccountWorker extends Thread{
    private final Account account1;
    private final Account account2;
    private final TransferService transferService;

    private static final int limitOperation = 30;

    public AccountWorker(Account account1, Account account2, TransferService transferService) {
        this.account1 = account1;
        this.account2 = account2;
        this.transferService = transferService;
    }

    @Override
    public void run(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
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


            try {
                Thread.sleep(random.nextInt(1000, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
