package bookShildt;

public class Truck extends Vehicle {
    private int cargocap;
    // Конструктор класса Truck
     Truck(int р, int f, int m, int с) {
         super(р, f, m);
         cargocap = с;
     }
    int getCargo() {
        return cargocap;
    }
    void putcargo(int с) {
        cargocap = с;
    }
}
