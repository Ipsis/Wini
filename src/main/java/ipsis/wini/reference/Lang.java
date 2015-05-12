package ipsis.wini.reference;

public class Lang {

    private static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    private static final String TAG_GUI = "gui." + Reference.MOD_ID + ":";
    private static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";

    public static final String TAG_TAB = "itemGroup." + Reference.MOD_ID + ":" + Reference.MOD_NAME;

    public static class Config {

    }

    public static class Gui {

        public static final String TITLE_HYSTERICAL_INV = TAG_GUI + "inv";
        public static final String TITLE_HYSTERICAL_RF = TAG_GUI + "rf";
        public static final String TITLE_HYSTERICAL_FLUID = TAG_GUI + "fluid";

        public static final String TAB_INFO = TAG_GUI + "tab.info";
        public static final String TAB_ENERGY = TAG_GUI + "tab.energy";
        public static final String TAB_TANKS = TAG_GUI + "tab.tanks";

        public static final String INFO_HYSTERICAL = TAG_GUI + "info.hysterical";
    }


    public static class Tooltips {

        public static final String ITEM_VOID_BAG = TAG_TOOLTIP + Names.Items.ITEM_VOID_BAG;
        public static final String ITEM_VOID_BAG_BIG = TAG_TOOLTIP + Names.Items.ITEM_VOID_BAG_BIG;
        public static final String ITEM_VOID_BAG_LOCKED = TAG_TOOLTIP + "bagLocked";
        public static final String ITEM_VOID_BAG_UNLOCKED = TAG_TOOLTIP + "bagUnlocked";

    }
}
