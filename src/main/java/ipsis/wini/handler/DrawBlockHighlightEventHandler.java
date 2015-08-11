package ipsis.wini.handler;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.wini.item.ItemMagicBlockPlacer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;

public class DrawBlockHighlightEventHandler {

    @SubscribeEvent
    public void onDrawBlockHighlighEvent(DrawBlockHighlightEvent event) {

        if (event.currentItem == null || !(event.currentItem.getItem() instanceof ItemMagicBlockPlacer))
            return;

        EntityPlayer entityPlayer = event.player;
        World world = event.player.worldObj;
        BlockPosition pos = ItemMagicBlockPlacer.getSelectedBlock(event.player.worldObj, event.player);
        if (pos == null)
            return;

        /**
         *  Vanilla block outline render code
         *  But works on air blocks
         */
        GL11.glPushMatrix();
        {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
            GL11.glLineWidth(4.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            float f1 = 0.002F;

            Block block = world.getBlock(pos.x, pos.y, pos.z);
            if (block == Blocks.air) {
                block.setBlockBoundsBasedOnState(world, pos.x, pos.y, pos.z);
                double d0 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double) event.partialTicks;
                double d1 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double) event.partialTicks;
                double d2 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double) event.partialTicks;
                RenderGlobal.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, pos.x, pos.y, pos.z).expand((double) f1, (double) f1, (double) f1).getOffsetBoundingBox(-d0, -d1, -d2), -1);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glPopMatrix();

        event.setCanceled(true);
    }

}
