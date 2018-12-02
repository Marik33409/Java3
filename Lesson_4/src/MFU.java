public class MFU {
    //3. Написать класс МФУ, на котором возможно одновременно выполнять печать и сканирование
    //документов, но нельзя одновременно печатать или сканировать два документа. При печати в
    //консоль выводится сообщения «Отпечатано 1, 2, 3,... страницы», при сканировании –
    //аналогично «Отсканировано...». Вывод в консоль с периодом в 50 мс.

    private final Object mfu = new Object();
    private volatile char currentStatys = 's';

    public void printDoc(int pageCount) {
        synchronized (mfu) {
            try {
                for (int i = 0; i < pageCount; i++) {
                    while (currentStatys != 'p')
                        mfu.wait();
                    System.out.println("print " + (i+1) + " page(s)");
                    currentStatys = 's';
                    Thread.sleep(50);
                    mfu.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void scanDoc(int pageCount) {
        synchronized (mfu) {
            try {
                for (int i = 0; i < pageCount; i++) {
                    while (currentStatys != 's')
                        mfu.wait();
                    System.out.println("scan " + (i+1) + " page(s)");
                    currentStatys = 'p';
                    Thread.sleep(50);
                    mfu.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyDoc(int pageCount){
        new Thread(() -> this.scanDoc(pageCount)).start();
        new Thread(() -> this.printDoc(pageCount)).start();
    }
}