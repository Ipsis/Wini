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

@SideOnly(Side.CLIENT)
public class LocationRegistry {

    public enum StructureType {
        SPAWN("Spawn", "Spawn"),
        STRONGHOLD("Stronghold", "End Portal"),
        VILLAGE("Village"),
        MINESHAFT("Mineshaft"),
        FORTRESS("Fortress", "Nether Fortress");

        /* Internal name registered with MC */
        public final String mcName;
        public final String displayName;

        /* This must be in-sync with the enum list above! */
        public static final StructureType validStructureTypes[] = { SPAWN, STRONGHOLD, VILLAGE, MINESHAFT, FORTRESS };

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

    public static ChunkPosition getLocationChunkPosition(StructureType type, World world, int x, int y, int z) {

        ChunkPosition pos = null;
        if (world != null) {
            if (type == StructureType.SPAWN) {
                ChunkCoordinates chunkcoordinates = world.getSpawnPoint();
                pos = new ChunkPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
            } else {
                pos = world.findClosestStructure(type.mcName, x, y, z);
            }
        }

        return pos;
    }

}
