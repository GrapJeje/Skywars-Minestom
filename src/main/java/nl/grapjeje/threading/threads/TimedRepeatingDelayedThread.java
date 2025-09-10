package nl.grapjeje.threading.threads;

import lombok.Getter;
import lombok.Setter;
import nl.grapjeje.threading.ThreadingManager;
import nl.grapjeje.threading.TickTimer;

/**
 * Represents a repeating thread that starts after a delay and executes a Runnable at fixed intervals.
 * Can be cancelled at any time.
 */
public class TimedRepeatingDelayedThread {
    private long startTime;
    private long endTime;

    @Setter
    private boolean cancelled = false;

    @Getter
    private long runTime = System.currentTimeMillis();

    /**
     * Creates and runs a delayed repeating thread.
     *
     * @param runnable       the Runnable to execute
     * @param repeatInterval the interval between executions in milliseconds
     * @param repeatAmount   the number of times to repeat, -1 for infinite
     * @param delay          the initial delay in milliseconds before starting the first execution
     */
    public TimedRepeatingDelayedThread(Runnable runnable, long repeatInterval, long repeatAmount, long delay) {
        ThreadingManager.getExecutor().execute(() -> {
            TickTimer.sleep(delay);
            long repeater = repeatAmount;
            while ((repeater > 0 || repeater == -1) && !cancelled) {
                TickTimer.sleep(repeatInterval);
                startTime = System.nanoTime();
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                endTime = System.nanoTime();
                if (repeater > 0) repeater--;
            }
        });
    }
}
