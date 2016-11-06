package nadrabank.controller;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(80);
        BigDecimal b = new BigDecimal(31.5);
         b = b.add(a);
        System.out.println(b);
    }
}
