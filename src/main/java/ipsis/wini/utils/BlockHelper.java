package ipsis.wini.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockHelper {

    /**
     * From block piston determineOrientation
     */
    public static int determineXYZPlaceFacing(World world, int x, int y, int z, EntityLivingBase living)
    {
        if (MathHelper.abs((float) living.posX - (float) x) < 2.0F && MathHelper.abs((float)living.posZ - (float)z) < 2.0F)
        {
            double d0 = living.posY + 1.82D - (double)living.yOffset;

            if (d0 - (double)y > 2.0D)
            {
                return 1;
            }

            if ((double)y - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}
