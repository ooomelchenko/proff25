package bookShildt;

enum TrafficLightColor2 {
    RED, GREEN, YELLOW
}

class TrafficLightSimulator2 implements Runnable {
    private Thread thrd;
    private TrafficLightColor2 tlc;
    boolean stop = false;
    boolean changed = false;

    TrafficLightSimulator2(TrafficLightColor2 init) {
        tlc = init;
        thrd = new Thread(this);
        thrd.start();
    }
    TrafficLightSimulator2() {
        tlc = TrafficLightColor2.RED;
        thrd = new Thread(this);
        thrd.start();
    }

    public void run() {

        while (!stop) {
            try {
                switch (tlc) {
                    case GREEN:
                        Thread.sleep(10000);
                        break;
                    case YELLOW:
                        Thread.sleep(5000);
                        break;
                    case RED:
                        Thread.sleep(10000);
                        break;
                }
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            changeColor();

        }
    }

    synchronized void changeColor() {
        switch (tlc) {
            case RED:
                tlc = TrafficLightColor2.GREEN;
                break;
            case YELLOW:
                tlc = TrafficLightColor2.RED;
                break;
            case GREEN:
                tlc = TrafficLightColor2.YELLOW;
                break;
        }
        changed = true;
        notify();
    }

    synchronized void waitForChange() {
        try {
            while (!changed)
                wait();
            changed = false;
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public TrafficLightColor2 getColor() {
        return tlc;
    }

    void cancel() {
        stop = true;
    }

}

public class TrafficLightDemo2 {
    public static void main(String[] args) {
        TrafficLightSimulator2 t1 = new TrafficLightSimulator2(TrafficLightColor2.GREEN);

        for(int i=0; i<9; i++){
            System.out.println(t1.getColor());
            t1.waitForChange();
        }
        t1.cancel();
    }
}