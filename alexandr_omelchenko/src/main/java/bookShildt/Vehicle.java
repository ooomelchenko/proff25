package bookShildt;

class Vehicle {
    private int passengers; // количество пассажиров
    private int fuelcap; // емкость топливного бака
    private int mpg; // потребление топлива в милях на галлон
    // Это конструктор класса Vehicle
    Vehicle (int р, int f, int m) {
     passengers = р;
     fuelcap = f;
     mpg = m;
 }

    int range () {
        return mpg * fuelcap;
    }
    double fuelneeded(int miles) {
        return (double) miles / mpg;
    }

    public int getPassengers() {
        return passengers;
    }
    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
    public int getFuelcap() {
        return fuelcap;
    }
    public void setFuelcap(int fuelcap) {
        this.fuelcap = fuelcap;
    }
    public int getMpg() {
        return mpg;
    }
    public void setMpg(int mpg) {
        this.mpg = mpg;
    }
}
