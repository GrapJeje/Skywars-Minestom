package nl.grapjeje.threading;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadingManager {
    private static MyExecutorService executor;

    public ThreadingManager() {
        executor = new MyExecutorService();
    }

    public static MyExecutorService getExecutor() {
        return executor;
    }

    public class MyExecutorService {

        private ExecutorService executor;
        @Getter
        private List<Future<?>> futures = new ArrayList<>();

        public MyExecutorService() {
            executor = Executors.newVirtualThreadPerTaskExecutor();
        }

        public void execute(Runnable r) {
            Future<?> f = executor.submit(r);
            futures.add(f);
        }

        public int getThreadsRunning() {
            for (Future<?> future : new ArrayList<>(futures)) {
                if (future.isDone() || future.isCancelled()) {
                    futures.remove(future);
                }
            }
            return futures.size();
        }
    }
}
