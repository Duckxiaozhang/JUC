package com.zhang.pc;

/*
* 线程之间的通信问题：生产-消费问题
* 线程交替执行 A B 操作同一个变量 num = 0
* A线程 num+1
* B线程 num-1
* */
public class A {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"A").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {
            try {
                data.decrement();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
                }
            }
        },"B").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {
            try {
                data.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        },"C").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {
            try {
                data.decrement();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        },"D").start();
    }
}

//判断通知 业务 等待
class Data {

    private int number = 0;
    //+1

    public synchronized void increment() throws InterruptedException {
        while (number != 0){
            //等待
            this.wait();
        }
            number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        //通知其他线程，可以开始执行
        this.notifyAll();
    }
    //-1

    public synchronized void decrement() throws InterruptedException {
        while (number == 0){
            //等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        //通知其他线程，可以开始执行
        this.notifyAll();
    }

}

