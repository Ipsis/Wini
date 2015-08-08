package ipsis.wini.registry;

import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * So far only the overworld stronghold is for getClosestStructure, all the ChunkProviders return null
 * for everything else.
 * Would probably need to do some custom lookup in the generated chunks.
 * That needs some thought!
 */

@SideOnly(Side.CLIENT)
public class LocationRegistry {

    public enum StructureType {
        SPAWN("Spawn", "Spawn"),
        STRONGHOLD("Stronghold", "End Portal");
        /*
        VILLAGE("Village"),
        MINESHAFT("Mineshaft"),
        FORTRESS("Fortress", "Nether Fortress");*/

        /* Internal name registered with MC */
        public final String mcName;
        public final String displayName;

        /* This must be in-sync with the enum list above! */
        //public static final StructureType validStructureTypes[] = { SPAWN, STRONGHOLD, VILLAGE, MINESHAFT, FORTRESS };
        public static final StructureType validStructureTypes[] = { SPAWN, STRONGHOLD };

        private StructureType(String mcName) {
            this(mcName, mcName);
        }
        private StructureType(String mcName, String displayName) {
            this.mcName = mcName;
            this.displayName = displayName;
        }

        public StructureType getNext() {
            int next = this.ordinal() + 1;
            if (next >= validStructureTypes.length)
                next = 0;
            return validStructureTypes[next];
        }

        public static StructureType getStructureType(int id) {
            id = MathHelper.clampI(id, 0, validStructureTypes.length - 1);
            return validStructureTypes[id];
        }
    }

    private static LocationRegistry registry;
    private static BlockPosition pos;
    private static boolean isValid;

    public static LocationRegistry getInstance() {
        if (registry == null)
            registry = new LocationRegistry();
        return registry;
    }

    public void setLocation(int x, int y, int z, boolean valid) {
        pos = new BlockPosition(x, y, z);
        isValid = valid;
    }

    public void clearLocation() {
        pos = null;
        isValid = false;
    }

    public BlockPosition getCurrentLocation() {
        return isValid ? pos : null;
    }

}
