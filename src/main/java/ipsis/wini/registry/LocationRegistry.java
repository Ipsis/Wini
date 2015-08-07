package ipsis.wini.registry;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LocationRegistry {

    public enum StructureType {
        STRONGHOLD("Stronghold");

        public final String name;

        private StructureType(String name) {
            this.name = name;
        }
    }

    private static LocationRegistry registry;
    private static BlockPosition pos;

    public static LocationRegistry getInstance() {
        if (registry == null)
            registry = new LocationRegistry();
        return registry;
    }

    public void addLocation(int x, int y, int z) {
        pos = new BlockPosition(x, y, z);
    }

    public BlockPosition getCurrentLocation() {
        return pos;
    }
}
