package ipsis.wini.utils;

import ipsis.wini.network.message.MessageRedstoneOutputCfg;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRedstoneOutput {

    enum Strength {
        WEAK, STRONG;

        public boolean isWeak() {
            return this == WEAK;
        }

        public boolean isStrong() {
            return this == STRONG;
        }
    }

    enum Sense {
        NORMAL, INVERTED;

        public boolean isNormal(){
            return this == NORMAL;
        }

        public boolean isInverted() {
            return this == INVERTED;
        }
    }

    void setRedstoneStrength(Strength s);
    Strength getRedstoneStrength();
    void setRedstoneSense(Sense s);
    Sense getRedstoneSense();
    void incRedstoneLevel();
    void decRedstoneLevel();
    void setRedstoneLevel(int v);
    int getRedstoneLevel();
    void setRedstoneOutputFace(ForgeDirection f);
    ForgeDirection getRedstoneOutputFace();

    void handleMessageRedstoneOutputCfg(MessageRedstoneOutputCfg m, EntityPlayerMP player);
}
