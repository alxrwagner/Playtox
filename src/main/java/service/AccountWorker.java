package service;

import com.example.playtox.exception.NotEnoughMoneyException;
import com.example.playtox.exception.NotFoundAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountWorker extends Thread {

    private  final Logger logger = LoggerFactory.getLogger(AccountWorker.class);
    private final String account1;
    private final String account2;
    private final TransferService transferService;
    private boolean isActive = true;

    private final int limitOperation = 30;
    private static AtomicInteger counterOperation = new AtomicInteger(0);

    public AccountWorker(String accountIdFrom, String accountIdTo, TransferService transferService) {
        this.account1 = accountIdFrom;
        this.account2 = accountIdTo;
        this.transferService = transferService;
    }

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String idFrom;
        String idTo;

        while (counterOperation.get() < limitOperation && isActive) {
            if (random.nextBoolean()) {
                idFrom = account1;
                idTo = account2;
            } else {
                idFrom = account2;
                idTo = account1;
            }
            try {
                transferService.transfer(idFrom, idTo, random.nextInt(5000));
                counterOperation.set(counterOperation.incrementAndGet());
                Thread.sleep(random.nextInt(1000, 2000));
            }catch (NotFoundAccount e){
                isActive = false;
                logger.info("Account not found");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
