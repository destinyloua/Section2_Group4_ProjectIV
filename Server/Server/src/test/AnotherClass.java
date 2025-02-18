package test;

//AnotherClass.java
public class AnotherClass implements Runnable {
 @Override
 public void run() {
     for (int i = 0; i <= 100; i++) {
         System.out.println("AnotherClass: " + i);
         try {
             Thread.sleep(2000); // Wait for 2 seconds
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 }
}
