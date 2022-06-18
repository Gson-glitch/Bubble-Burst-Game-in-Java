import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
    Bubbles Bbl = new Bubbles();
    static int interval;
    static Timer timer;
    public Stopwatch(int x) {
        int secs = setBound(x);
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = secs;
//        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
//                System.out.println(setInterval());
                Bbl.updateTimeLabel(setInterval());
            }
        }, delay, period);
    }


    public int setBound(int s) {
        return s;
    }

    public static void main(String[] args) {
//        new Stopwatch();
    }

    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }
}