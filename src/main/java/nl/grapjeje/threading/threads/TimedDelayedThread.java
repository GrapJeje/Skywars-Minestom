package nl.grapjeje.threading.threads;

import nl.grapjeje.threading.ThreadingManager;
import nl.grapjeje.threading.TickTimer;

/**
 * Represents a thread that executes a Runnable after a specified delay and measures execution time.
 */
public class TimedDelayedThread {
    private long startTime;
    private long endTime;

    /**
     * Creates and runs a delayed thread.
     *
     * @param runnable the Runnable to execute
     * @param waitTime the delay in milliseconds before running the Runnable
     */
    public TimedDelayedThread(Runnable runnable, long waitTime) {
        ThreadingManager.getExecutor().execute(() -> {
            TickTimer.sleep(waitTime);
            startTime = System.nanoTime();
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTime = System.nanoTime();
        });
    }
}
