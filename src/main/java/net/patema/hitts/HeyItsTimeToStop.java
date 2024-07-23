package net.patema.hitts;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.patema.hitts.utils.PlayerDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeyItsTimeToStop implements ModInitializer {
	public static final String MOD_ID = "hitts";
	public static final Logger LOGGER = LoggerFactory.getLogger(HeyItsTimeToStop.MOD_ID);

	@Override
	public void onInitialize() {
		PlayerDetection.register();

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> PlayerDetection.shutdownScheduler());
	}
}