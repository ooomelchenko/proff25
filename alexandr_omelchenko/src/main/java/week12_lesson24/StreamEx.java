package week12_lesson24;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by HP on 18.08.2015.
 */
public class StreamEx {
    public static void main(String[] args) {
        int res =Stream
                .of(1, 2, 3, 4, 5, 6, 7)
                .parallel()
                .filter((e) -> e > 3)
                 //       .collect(Collectors.....)
              //  .map((e)-> e+2)
              //  .flatMap()
                .reduce((sum, el) -> sum+el).get();
                //.forEach(System.out::print);
                System.out.print(res);

        //преобразование коллекции в stream
        //Arrays.asList().stream().map().reduce()
    }
}