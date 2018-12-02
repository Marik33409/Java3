public class Main {

    public static void main(String[] args) {
        Printer p = new Printer();

        task1(p);
        task2(p);
        System.out.println();
        task3();

    }

    private static void task1(Printer p){
        new Thread(() -> p.printA()).start();
        new Thread(() -> p.printB()).start();
        new Thread(() -> p.printC()).start();
    }

    private static void task2(Printer p){
        new Thread(() -> p.printToFile("Tread 1")).start();
        new Thread(() -> p.printToFile("Tread 2")).start();
        new Thread(() -> p.printToFile("Tread 3")).start();
    }

    private static void task3(){
        MFU mfu = new MFU();
        mfu.copyDoc(15);
    }

}