package ipsis.wini.client.renderer.texture;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import ipsis.wini.registry.LocationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
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
            BlockPosition p = LocationRegistry.getInstance().getCurrentLocation();
            if (p != null) {
                /* Orient the compass */
                this.updateCompass(minecraft.theWorld,
                        minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double) minecraft.thePlayer.rotationYaw);
            } else {
                /* Spin the compass */
                super.updateAnimation();
            }
        } else {
            /* Spin the compass */
            super.updateAnimation();
        }
    }

    /**
     * Updates the compass based on the given x,z coords and camera direction
     * Exactly the same as the default compass EXCEPT it looks for a requested location
     *
     * I dont understand any of this :)
     * It is working out which frame of the texture to display.
     */
    public void updateCompass(World world, double posX, double posZ, double rotationYaw) {
        if (!this.framesTextureData.isEmpty()) {

            double d3 = 0.0D;
            BlockPosition p = LocationRegistry.getInstance().getCurrentLocation();
            if (world != null && p != null) {
                double d4 = (double) p.x - posX;
                double d5 = (double) p.z - posZ;
                rotationYaw %= 360.0D;
                d3 = -((rotationYaw - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

                /* TODO if not overworld - do I want this? */
                if (!world.provider.isSurfaceWorld())
                    d3 = Math.random() * Math.PI * 2.0D;
            }

            double d6;

            for (d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D)) {
                ;
            }

            while (d6 >= Math.PI)
                d6 -= (Math.PI * 2D);

            if (d6 < -1.0D)
                d6 = -1.0D;

            if (d6 > 1.0D)
                d6 = 1.0D;

            this.angleDelta += d6 * 0.1D;
            this.angleDelta *= 0.8D;
            this.currentAngle += this.angleDelta;

            int i;
            for (i = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) this.framesTextureData.size()) % this.framesTextureData.size();
                 i < 0;
                 i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {
                ;
            }

            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap((int[][]) this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}
