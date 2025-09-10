package nl.grapjeje.threading.threads;

import nl.grapjeje.threading.ThreadingManager;

/**
 * Represents a thread that measures the execution time of a Runnable.
 */
public class TimedThread {
    private long startTime;
    private long endTime;

    /**
     * Creates and runs a timed thread.
     *
     * @param runnable the Runnable to execute
     */
    public TimedThread(Runnable runnable) {
        startTime = System.nanoTime();
        ThreadingManager.getExecutor().execute(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTime = System.nanoTime();
        });
    }
}
