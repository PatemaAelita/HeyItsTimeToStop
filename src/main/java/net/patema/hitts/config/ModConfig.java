package net.patema.hitts.config;

import net.patema.hitts.HeyItsTimeToStop;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = HeyItsTimeToStop.MOD_ID)
@Config(name = "heyitstimetostop", wrapperName = "HittsConfig")
public class ModConfig {
    public int minutesBeforeStopping = 15;
}
