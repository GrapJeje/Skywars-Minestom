package nl.grapjeje.threading.threads;

import lombok.Setter;
import nl.grapjeje.threading.ThreadingManager;
import nl.grapjeje.threading.TickTimer;

/**
 * Represents a repeating thread that executes a Runnable at fixed intervals for a specified number of times.
 * Can be cancelled at any time.
 */
public class TimedRepeatingThread {
    private long startTime;
    private long endTime;

    @Setter
    private boolean cancelled = false;

    /**
     * Creates and runs a repeating thread.
     *
     * @param runnable       the Runnable to execute
     * @param repeatInterval the interval between executions in milliseconds
     * @param repeatAmount   the number of times to repeat, -1 for infinite
     */
    public TimedRepeatingThread(Runnable runnable, long repeatInterval, long repeatAmount) {
        ThreadingManager.getExecutor().execute(() -> {
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
