package service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountWorker extends Thread {
    private final String account1;
    private final String account2;
    private final TransferService transferService;

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

        while (counterOperation.get() < limitOperation) {
            if (random.nextBoolean()) {
                idFrom = account1;
                idTo = account2;
            } else {
                idFrom = account2;
                idTo = account1;
            }
            transferService.transfer(idFrom, idTo, random.nextInt(5000));
            counterOperation.set(counterOperation.incrementAndGet());


            try {
                Thread.sleep(random.nextInt(1000, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
