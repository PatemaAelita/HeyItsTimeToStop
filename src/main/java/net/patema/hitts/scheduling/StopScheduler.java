package net.patema.hitts.scheduling;

import net.minecraft.server.MinecraftServer;
import net.patema.hitts.HeyItsTimeToStop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StopScheduler {

    private static final int userConfigMinutes = HeyItsTimeToStop.CONFIG.minutesBeforeStopping();
    private static final Object lock = new Object();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> cronJobFuture;


    public static void startAutoStopper(MinecraftServer server) {
        if (cronJobFuture == null) {
            HeyItsTimeToStop.LOGGER.info("No player detected! The server will stop in "+userConfigMinutes+" minutes.");
            cronJobFuture = scheduler.schedule(() -> {
                synchronized (lock) {
                    server.stop(false);
                }
            }, userConfigMinutes, TimeUnit.MINUTES);
        }
    }

    public static void cancelAutoStopper() {
        synchronized (lock) {
            if (cronJobFuture != null) {
                cronJobFuture.cancel(true);
                cronJobFuture = null;
                HeyItsTimeToStop.LOGGER.info("A player has joined the server, the stop task was cancelled.");
            }
        }
    }

    public static void shutdownScheduler() {
        scheduler.shutdownNow();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Scheduler did not terminate in the specified time.");
            }
        } catch (InterruptedException ex) {
            System.err.println("Interrupted while waiting for scheduler termination.");
            Thread.currentThread().interrupt();
        }
    }
}
