package ipsis.wini.reference;

public class Lang {

    private static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    private static final String TAG_GUI = "gui." + Reference.MOD_ID + ":";
    private static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";

    public static final String TAG_TAB = "itemGroup." + Reference.MOD_ID + ":" + Reference.MOD_NAME;

    public static class Config {
        public static final String HYST_UPDATE_RATE = TAG_CONFIG + "hyst.updateRate";
        public static final String BLOCK_SCEPTRE_ENABLED = TAG_CONFIG + "blockSceptreEnabled";
        public static final String COMP_DROP_TYPE = TAG_CONFIG + "compactorDropType";
    }

    public static class Gui {

        public static final String TITLE_HYSTERICAL_INV = TAG_GUI + "inv";
        public static final String TITLE_HYSTERICAL_RF = TAG_GUI + "rf";
        public static final String TITLE_HYSTERICAL_FLUID = TAG_GUI + "fluid";

        public static final String TAB_INFO = TAG_GUI + "tab.info";
        public static final String TAB_ENERGY = TAG_GUI + "tab.energy";
        public static final String TAB_TANKS = TAG_GUI + "tab.tanks";
        public static final String TAB_REDSTONE = TAG_GUI + "tab.redstone";

        public static final String INFO_HYSTERICAL = TAG_GUI + "info.hysteresis";

        public static final String TIP_STRONG_RS = TAG_GUI + "tooltip.strongRs";
        public static final String TIP_WEAK_RS = TAG_GUI + "tooltip.weakRs";
        public static final String TIP_NORM_SENSE = TAG_GUI + "tooltip.normalSense";
        public static final String TIP_INV_SENSE = TAG_GUI + "tooltip.invertedSense";
        public static final String TIP_INC_RS = TAG_GUI + "tooltip.incRs";
        public static final String TIP_DEC_RS = TAG_GUI + "tooltip.decRs";

        public static final String TIP_HYST_GT = TAG_GUI + "tooltip.gt";
        public static final String TIP_HYST_GTE = TAG_GUI + "tooltip.gte";
        public static final String TIP_HYST_EQ = TAG_GUI + "tooltip.eq";
        public static final String TIP_HYST_LT = TAG_GUI + "tooltip.lt";
        public static final String TIP_HYST_LTE = TAG_GUI + "tooltip.lte";


        public static final String TIP_TRIG_SAVE =TAG_GUI + "tooltip.triggerSave";
        public static final String TIP_RESET_SAVE =TAG_GUI + "tooltip.resetSave";
        public static final String TIP_RUNNING = TAG_GUI + "tooltip.running";
        public static final String TIP_STOPPED = TAG_GUI + "tooltip.stopped";
    }


    public static class Tooltips {

        public static final String ITEM_VOID_BAG = TAG_TOOLTIP + Names.Items.ITEM_VOID_BAG;
        public static final String ITEM_VOID_BAG_BIG = TAG_TOOLTIP + Names.Items.ITEM_VOID_BAG_BIG;
        public static final String ITEM_VOID_BAG_LOCKED = TAG_TOOLTIP + "bagLocked";
        public static final String ITEM_VOID_BAG_UNLOCKED = TAG_TOOLTIP + "bagUnlocked";
        public static final String ITEM_MAGIC_BLOCK_PLACER = TAG_TOOLTIP + Names.Items.ITEM_MAGIC_BLOCK_PLACER;

    }
}
