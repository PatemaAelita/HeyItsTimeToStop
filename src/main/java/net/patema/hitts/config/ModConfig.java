package net.patema.hitts.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "heyitstimetostop")
public class ModConfig implements ConfigData {

    @Comment("Should be between 5 and 60.")
    public int minutesBeforeStopping = 15;

    @Override
    public void validatePostLoad() throws ValidationException {
        if (minutesBeforeStopping <5 || minutesBeforeStopping > 60){
            throw new ValidationException("value should be between 5 and 60!");
        }
    }
}
