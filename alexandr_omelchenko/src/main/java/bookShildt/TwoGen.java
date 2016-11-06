package bookShildt;

public class TwoGen<T, V> {
    T ob1;
    V ob2;

    TwoGen(T ol, V o2) {
        ob1 = ol;
        ob2 = o2;
    }

    void showTypes() {
        System.out.println("Tип Т - это" + ob1.getClass().getName());
        System.out.println("Tип V - это" + ob2.getClass().getName());
    }

    T getob1() {
        return ob1;
    }

    V getob2() {
        return ob2;
    }

    public static void main(String[] args) {
        TwoGen<Integer, String> tgObj = new TwoGen<>(88, "Обобщения");
        tgObj.showTypes();
        int v = tgObj.getob1();
        System.out.println("значение1 "+v);
        String str = tgObj.getob2();
        System.out.println("значение2 "+str);

    }

}
