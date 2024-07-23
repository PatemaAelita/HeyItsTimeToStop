package net.patema.hitts.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "heyitstimetostop")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Cooldown cooldown = new Cooldown();

    public static class Cooldown {
        public int minutesBeforeStopping = 15;
    }
}
