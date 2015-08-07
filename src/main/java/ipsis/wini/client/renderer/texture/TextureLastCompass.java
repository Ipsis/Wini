package ipsis.wini.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class TextureLastCompass extends TextureAtlasSprite {

    public double currentAngle;
    public double angleDelta;

    public TextureLastCompass() {
        super(Textures.RESOURCE_PREFIX + Names.Items.ITEM_LAST_COMPASS);
    }

    public void updateAnimation() {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.theWorld != null && minecraft.thePlayer != null) {

            ChunkPosition p = minecraft.theWorld.findClosestStructure("Stronghold",
                    (int)minecraft.thePlayer.posX, (int)minecraft.thePlayer.posY, (int)minecraft.thePlayer.posZ);

            LogHelper.info(p);
            if (p != null)
                this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double)minecraft.thePlayer.rotationYaw, false, false);
            else
            super.updateAnimation();
        } else {
            super.updateAnimation();
        }
    }

    /**
     * Updates the compass based on the given x,z coords and camera direction
     * Exactly the same as the default compass EXCEPT it looks for a stronghold!
     */
    public void updateCompass(World world, double posX, double posZ, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_)
    {
        //if (!this.framesTextureData.isEmpty())
        {
            double d3 = 0.0D;
            LogHelper.info("updateCompass: " + posX + " " + posZ + " " + p_94241_6_ + " " + p_94241_8_ + " " + p_94241_9_);

            ChunkPosition chunkposition;
            if (world != null && !p_94241_8_ && (chunkposition = world.findClosestStructure("Stronghold", (int)posX, 32, (int)posZ)) != null)
            {
                LogHelper.info("updateCompass " + world + " " + chunkposition.chunkPosX + " " + chunkposition.chunkPosZ);
                double d4 = (double)chunkposition.chunkPosX - posX;
                double d5 = (double)chunkposition.chunkPosZ - posZ;
                p_94241_6_ %= 360.0D;
                d3 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

                if (!world.provider.isSurfaceWorld())
                {
                    d3 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (p_94241_9_)
            {
                this.currentAngle = d3;
            }
            else
            {
                double d6;

                for (d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D))
                {
                    ;
                }

                while (d6 >= Math.PI)
                {
                    d6 -= (Math.PI * 2D);
                }

                if (d6 < -1.0D)
                {
                    d6 = -1.0D;
                }

                if (d6 > 1.0D)
                {
                    d6 = 1.0D;
                }

                this.angleDelta += d6 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;

            for (i = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size())
            {
                ;
            }

            if (i != this.frameCounter)
            {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}
