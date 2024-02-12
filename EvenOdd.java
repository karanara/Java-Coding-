import javax.management.ObjectName;

public class EvenOdd implements Runnable{
    static int count = 1;
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

    public static void main(String[] args) {
        Object obj = new Object();
        Runnable r1 = new EvenOdd(obj);
        Runnable r2 = new EvenOdd(obj);
        new Thread(r1,"even").start();
        new Thread(r2,"odd").start();
    }
    
}