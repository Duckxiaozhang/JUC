package com.zhang;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SeleTicketDemo02 {
    public static void main(String[] args) {
        Ticket02 ticket = new Ticket02();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "A").start();
    }
}
class Ticket02 {
    private int number = 30;
    Lock lock = new ReentrantLock();

    public  void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ": 卖出第" + (number--) + "张票，剩余：" + number);
            }
        }
        finally {
            lock.unlock();
        }
    }
}