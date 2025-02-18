package test;

//MainClass.java
public class MainClass {
 public static void main(String[] args) {
     // Create a thread for AnotherClass
     Thread anotherThread = new Thread(new AnotherClass());
     anotherThread.start(); // Start the thread
     
     // Main thread prints numbers from 100 to 0
     for (int i = 100; i >= 0; i--) {
         System.out.println("MainClass: " + i);
         try {
             Thread.sleep(2000); // Wait for 2 seconds
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 }
}