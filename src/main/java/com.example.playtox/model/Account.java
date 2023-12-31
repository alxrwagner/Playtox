package com.example.playtox.model;

import com.example.playtox.exception.NotEnoughMoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TransferService;

import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
    private final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private final ReadWriteLock lockAccount;
    private final String ID;
    private int money;

    public Account() {
        this.ID = UUID.randomUUID().toString();
        this.money = 10000;
        this.lockAccount = new ReentrantReadWriteLock();
    }

    public String getID(){
        return this.ID;
    }

    public int getMoney(){
        lockAccount.readLock().lock();
        try{
            return this.money;
        } finally {
            lockAccount.readLock().unlock();
        }
    }

    public void addMoney(int moneyAmount){
        if (moneyAmount <= 0){
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }
        lockAccount.writeLock().lock();
        try {
            this.money += moneyAmount;
        }finally {
            lockAccount.writeLock().unlock();
        }
    }

    public void withdrawMoney(int moneyAmount) {
        if (money >= moneyAmount) {
            if (moneyAmount <= 0) {
                throw new IllegalArgumentException("Transfer amount must be greater than 0");
            }
            lockAccount.writeLock().lock();
            try {
                this.money -= moneyAmount;
            } finally {
                lockAccount.writeLock().unlock();
            }
        } else {
            logger.info("Not enough money in the account {}", ID);
            throw new NotEnoughMoneyException("Not enough money in the account " + ID);
        }
    }
}
