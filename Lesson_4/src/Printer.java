import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private final Object monitor = new Object();
    private volatile char currentLetter = 'A';

    //1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок
    //– ABСABСABС). Используйте wait/notify/notifyAll.
    public void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A')
                        monitor.wait();
                    System.out.print("A");
                    currentLetter = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B')
                        monitor.wait();
                    System.out.print("B");
                    currentLetter = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C')
                        monitor.wait();
                    System.out.print("C ");
                    currentLetter = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //2. Написать небольшой метод, в котором 3 потока построчно пишут данные в файл (по 10
    //записей с периодом в 20 мс).

    public void printToFile(String text) {
        for (int i = 0; i < 10; i++) {
            synchronized (monitor) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt", true));
                    bw.write(text);
                    bw.newLine();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}