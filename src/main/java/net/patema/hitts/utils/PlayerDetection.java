package net.patema.hitts.utils;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.patema.hitts.HeyItsTimeToStop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerDetection {

    private static int tickCounter = 0;
    private static final Object lock = new Object(); // Verrou pour la synchronisation
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> cronJobFuture;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(PlayerDetection::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        int playerCount = server.getPlayerManager().getPlayerList().size();

        synchronized (lock) {
            if (playerCount == 0 && cronJobFuture == null) {
                startCronJob(server);
            } else if (playerCount > 0 && cronJobFuture != null) {
                cancelCronJob();
            }
        }
    }

    private static void startCronJob(MinecraftServer server) {
        HeyItsTimeToStop.LOGGER.info("Le serveur s'arrêtera dans 5 min");
        cronJobFuture = scheduler.schedule(() -> {
            synchronized (lock) {
                HeyItsTimeToStop.LOGGER.info("Stopping the server!");
                server.stop(false);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private static void cancelCronJob() {
        synchronized (lock) {
            if (cronJobFuture != null) {
                cronJobFuture.cancel(true);
                cronJobFuture = null;
                HeyItsTimeToStop.LOGGER.info("Cron job annulé : un joueur s'est connecté.");
            }
        }
    }

    public static void shutdownScheduler() {
        scheduler.shutdownNow(); // Essaye d'arrêter toutes les tâches en cours et d'arrêter le service
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
