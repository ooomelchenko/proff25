package week12_lesson24;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

public class LambdaEx {
    public static void main(String[] args) {
        Runnable run=()-> System.out.println("run");
        new Thread(run).start();
       // Comparator<Integer> comp = (Integer e1, Integer e2) -> e1-e2;
        Comparator<Integer> comp = (e1, e2) -> e1-e2;
        Consumer consumer = (el)-> System.out.println();
       // Arrays.asList(1, 2, 3, 4).forEach(consumer);
        Arrays.asList(1, 2, 3, 4).forEach(System.out::println);
    }
}
