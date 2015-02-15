package ipsis.wini.utils;

import net.minecraft.util.StatCollector;

public class StringHelper {

    public static String localize(String key) {

        return StatCollector.translateToLocal(key);
    }
}
