package net.patema.hitts;

import net.fabricmc.api.ModInitializer;

import net.patema.hitts.config.HittsConfig;
import net.patema.hitts.listeners.PlayerDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeyItsTimeToStop implements ModInitializer {
	public static final String MOD_ID = "hitts";
	public static final Logger LOGGER = LoggerFactory.getLogger(HeyItsTimeToStop.MOD_ID);
	public static final HittsConfig CONFIG = HittsConfig.createAndLoad();

	@Override
	public void onInitialize() {
		final int miaou = CONFIG.minutesBeforeStopping();

		HeyItsTimeToStop.LOGGER.info("HeyItsTimeToStop initialized!");
		PlayerDetection.register();

		HeyItsTimeToStop.LOGGER.info(String.valueOf(miaou));
	}
}