import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import javax.management.ObjectName;

public class EvenOdd{
    /*static int count = 1;
    Object object;

    public EvenOdd(Object object) {
        this.object = object;
    }

    @Override
    public void run() {

        while (count <= 100) {
            if (count % 2 == 0 && Thread.currentThread().getName().equals("even")) {
                synchronized (object) {
                    System.out.println("Thread Name : " + Thread.currentThread().getName() + " value :" + count);
                    count++;
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (count % 2 != 0 && Thread.currentThread().getName().equals("odd")) {
                synchronized (object) {
                    System.out.println("Thread Name : " + Thread.currentThread().getName() + " value :" + count);
                    count++;
                    object.notify();
                }
            }

        }

    }

    /*public static void main(String[] args) {
        Object obj = new Object();
        Runnable r1 = new EvenOdd(obj);
        Runnable r2 = new EvenOdd(obj);
        new Thread(r1,"even").start();
        new Thread(r2,"odd").start();
    }*/
    /*public static void main (String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.rangeClosed(1, 10)
        .forEach(num->{
            CompletableFuture<Integer> oddCompletableFuture = CompletableFuture.completedFuture(num)
            .thenApplyAsync(x->{
                if (x%2!=0) {
                    System.out.println("Thread Name " + Thread.currentThread().getName() + " : " + x);
                }
                return num; },executorService);
                oddCompletableFuture.join();
                CompletableFuture<Integer> EvenCompletableFuture = CompletableFuture.completedFuture(num)
                .thenApplyAsync(x->{
                    if (x%2==0) {
                        System.out.println("Thread Name " + Thread.currentThread().getName() + " : " + x);
                    }
                    return num; },executorService);
                    EvenCompletableFuture.join();
            
            });
    }*/
    private static IntPredicate evenCondition = e -> e % 2 == 0;
    private static IntPredicate oddCondition = e -> e % 2 != 0;
    private static Object  object= new Object();
    public static void printResults(IntPredicate condition){
        IntStream.rangeClosed(1,10)
        .filter(condition)
        .forEach(EvenOdd::execute);

    }
    public static void execute(int i){
        synchronized(object){
            try{
                System.out.println("Thread Name " + Thread.currentThread().getName() + " : " + i);
                object.notify();
                object.wait(); 
            }catch(InterruptedException ex){

            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(() -> EvenOdd.printResults(oddCondition));
        CompletableFuture.runAsync(() -> EvenOdd.printResults(evenCondition));
        Thread.sleep(1000);

    }
    
}