package bookShildt;

public class NumericFns<T extends Number> {
    T num;

    NumericFns(T n) {
        num = n;
    }

    double reciprocal() {
        return 1 / num.doubleValue();
    }

    double fraction() {
        return num.doubleValue() - num.intValue();
    }

    boolean absEqual(NumericFns<?> ob) {
        return Math.abs(num.doubleValue()) == Math.abs(ob.num.doubleValue());
    }

    public static void main(String args[]) {
        NumericFns<Integer> iOb = new NumericFns<Integer>(6);
        NumericFns<Double> dOb = new NumericFns<Double>(-6.0);
        NumericFns<Long> lOb = new NumericFns<Long>(5L);
        System.out.println("Cpaвнeниe iOb и dOb");
        if (iOb.absEqual(iOb))
            System.out.println("Aбcoлютныe значения совпадают.");
        else
            System.out.println("Aбcoлютныe значения отличаются.");
        System.out.println();

        System.out.println("Cpaвнeниe iOb и lOb");
        if (iOb.absEqual(lOb))
            System.out.println("Aбcoлютныe значения совпадают.");
        else
            System.out.println("Aбcoлютныe значения отличаются.");
    }

}
