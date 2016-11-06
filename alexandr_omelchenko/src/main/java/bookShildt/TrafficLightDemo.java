package bookShildt;

    enum TrafficLightColor {
        RED(1600), GREEN(2000), YELLOW(500) ;
        private int sleepTime;

        public int getSleepTime() {
            return sleepTime;
        }

        public void setSleepTime(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        TrafficLightColor(int sleepTime) {
            this.sleepTime = sleepTime;
        }
    }

    class TrafficLightSimulator implements Runnable {
        private Thread thrd;
        private TrafficLightColor tlc;
        boolean stop = false;
        boolean changed = false;

        TrafficLightSimulator(TrafficLightColor init) {
            tlc = init;
            thrd = new Thread(this);
            thrd.start();
        }
        TrafficLightSimulator() {
            tlc = TrafficLightColor.RED;
            thrd = new Thread(this);
            thrd.start();
        }

        public void run() {
            while (!stop) {
                try {
                    switch (tlc) {
                        case GREEN:
                            Thread.sleep(tlc.getSleepTime());
                            break;
                        case YELLOW:
                            Thread.sleep(tlc.getSleepTime());
                            break;
                        case RED:
                            Thread.sleep(tlc.getSleepTime());
                            break;
                    }
                } catch (InterruptedException exc) {
                    System.out.println(exc);
                }
                changeColor();
            }
        }

        synchronized void changeColor() {

            switch (tlc.ordinal()) {
                case 0:
                    tlc = TrafficLightColor.GREEN;
                    break;
                case 2:
                    tlc = TrafficLightColor.RED;
                    break;
                case 1:
                    tlc = TrafficLightColor.YELLOW;
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
            catch (InterruptedException exc) {
                System.out.println(exc);
            }
        }

        TrafficLightColor getColor() {
            return tlc;
        }

        void cancel() {
            stop = true;
        }

    }

public class TrafficLightDemo {
    public static void main(String[] args) {
        TrafficLightSimulator t1 = new TrafficLightSimulator(TrafficLightColor.GREEN);
        for(int i=0; i<9; i++){
            System.out.println(t1.getColor());
            t1.waitForChange();
        }
        t1.cancel();
    }
}
