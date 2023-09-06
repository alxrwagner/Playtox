package com.example.playtox.model;

import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
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
        lockAccount.writeLock().lock();
        try {
            this.money += moneyAmount;
        }finally {
            lockAccount.writeLock().unlock();
        }
    }

    public void withdrawMoney(int moneyAmount){
        lockAccount.writeLock().lock();
        try {
                this.money -= moneyAmount;
        }finally {
            lockAccount.writeLock().unlock();
        }
    }

    public ReadWriteLock getLock(){
        return lockAccount;
    }
}
