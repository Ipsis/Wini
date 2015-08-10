package ipsis.wini.handler;

import cofh.lib.util.helpers.StringHelper;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Settings;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration configuration;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {

        Settings.hystUpdateRate = configuration.get(Configuration.CATEGORY_GENERAL,
                "hysteresisUpdateRate", Settings.DEF_HYST_UPDATE_RATE,
                StringHelper.localize(Lang.Config.HYST_UPDATE_RATE)).getInt(Settings.DEF_HYST_UPDATE_RATE);
        Settings.blockSceptreEnabled = configuration.get(Configuration.CATEGORY_GENERAL,
                "blockSceptreEnabled", Settings.DEF_BLOCK_SCEPTRE_ENABLED,
                StringHelper.localize(Lang.Config.BLOCK_SCEPTRE_ENABLED)).getBoolean(Settings.DEF_BLOCK_SCEPTRE_ENABLED);
        Settings.compactorDropType = configuration.get(Configuration.CATEGORY_GENERAL,
                "compactorDropType", Settings.DEF_COMP_DROP_TYPE,
                StringHelper.localize(Lang.Config.COMP_DROP_TYPE)).getInt(Settings.DEF_COMP_DROP_TYPE);

        if (configuration.hasChanged())
            configuration.save();
    }

}
