package bookShildt;

public class GenQDemo {
    public static void main(String[] args) {
        Integer iStore[] = new Integer[10];
        GenQueue<Integer> q = new GenQueue<>(iStore);
        Integer iVal;

        System.out.println("Дeмoнcтpaции очереди чисел типа Integer");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Дoбaвлeниe " + i + " в очередь q");
                q.put(i); // добавить целочисленное значение в очередь q
            }
        } catch (QueueFullException exc) {
            System.out.println(exc);
        }
        System.out.println();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.print("Пoлyчeниe следующего числа типа Integer из очереди q: ");
                iVal = q.get();
                System.out.println(iVal);
            }
        } catch (QueueEmptyException ехс) {
            System.out.println(ехс);

        }
        System.out.println();
// Создать очередь для хранения чисел с плавающей точкой
        Double dStore[] = new Double[8];
        GenQueue<Double> q2 = new GenQueue<>(dStore);
        Double dVal;
        System.out.println("Демонстрация очереди чисел типа DouЬle");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Дoбaвлeниe  + (double}i/2 + в очередь q2");
                q2.put((double) i / 2);
                // ввести значение типа double в очередь q2
            }
        } catch (QueueFullException ехс) {
            System.out.println(ехс);
        }
        System.out.println();

        try {
            for (int i = 0; i < 5; i++) {
                System.out.print("Пoлyчeниe следующего числа типа Double из очереди q2: ");
                dVal = q2.get();
                System.out.println(dVal);
            }
        } catch (QueueEmptyException ехс) {
            System.out.println(ехс);
        }

    }

}