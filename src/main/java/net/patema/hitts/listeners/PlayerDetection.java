package net.patema.hitts.listeners;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.patema.hitts.scheduling.StopScheduler;

public class PlayerDetection {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(PlayerDetection::onServerTick);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> StopScheduler.shutdownScheduler());
    }

    private static void onServerTick(MinecraftServer server) {
        int playerCount = server.getPlayerManager().getPlayerList().size();

        if (playerCount == 0) {
            StopScheduler.startAutoStopper(server);
        } else {
            StopScheduler.cancelAutoStopper();
        }
    }
}
