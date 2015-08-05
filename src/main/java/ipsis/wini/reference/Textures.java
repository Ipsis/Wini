package ipsis.wini.reference;

public class Textures {

    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Gui {

        public static final String SHEET_LOCATION = "textures/gui/";

        public static final String TORCH_POUCH = RESOURCE_PREFIX + SHEET_LOCATION + "torchPouch.png";
        public static final String VOID_BAG = RESOURCE_PREFIX + SHEET_LOCATION + "voidBag.png";
        public static final String VOID_BAG_BIG = RESOURCE_PREFIX + SHEET_LOCATION + "voidBagBig.png";

        public static final String HYSTERESIS = RESOURCE_PREFIX + SHEET_LOCATION + "hysteresis.png";

        public static final String TAB_RIGHT = SHEET_LOCATION + "elements/Tab_Right.png";
        public static final String TAB_LEFT = SHEET_LOCATION + "elements/Tab_Left.png";

        public static final int ENERGY_TAB_BACKGROUND = 0xFF8E56;
        public static final int INFO_TAB_BACKGROUND = 0xC6EAFF;
        public static final int TANKS_TAB_BACKGROUND = 0xC6EAFF;
        public static final int CONFIG_TAB_BACKGROUND = 0xC6EAFF;
        public static final int REDSTONE_TAB_BACKGROUND = 0xC6EAFF;
    }
}
