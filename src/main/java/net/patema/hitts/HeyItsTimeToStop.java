package net.patema.hitts;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.patema.hitts.config.ModConfig;
import net.patema.hitts.listeners.PlayerDetection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeyItsTimeToStop implements ModInitializer {
	public static final String MOD_ID = "hitts";
	public static final Logger LOGGER = LoggerFactory.getLogger(HeyItsTimeToStop.MOD_ID);
	public static ModConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		HeyItsTimeToStop.LOGGER.info("HeyItsTimeToStop initialized!");
		PlayerDetection.register();
	}
}