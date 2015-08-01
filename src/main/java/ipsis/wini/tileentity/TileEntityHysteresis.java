package ipsis.wini.tileentity;

import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.relauncher.Side;
import ipsis.wini.helper.MonitorType;
import ipsis.wini.network.message.MessageHysteresisCfg;
import ipsis.wini.network.message.MessageHysteresisUpdate;
import ipsis.wini.network.message.MessageRedstoneOutputCfg;
import ipsis.wini.reference.Nbt;
import ipsis.wini.utils.CompareFunc;
import ipsis.wini.utils.IRedstoneOutput;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityHysteresis extends TileEntityWini implements IRedstoneOutput {

    MonitorType monitorType;
    SMState currState = SMState.INIT;
    CompareFunc triggerFunc, resetFunc;
    int triggerLevel, resetLevel;
    boolean enabled = true;

    /* Redstone Output */
    IRedstoneOutput.Sense redstoneSense = IRedstoneOutput.Sense.NORMAL;
    IRedstoneOutput.Strength redstoneStrength = Strength.WEAK;
    int redstoneLevel = 15;
    ForgeDirection redstoneOutputFace;

    private TileEntityHysteresis() { }
    public TileEntityHysteresis(MonitorType monitorType) {
        super();
        this.monitorType = monitorType;
        this.redstoneOutputFace = getFacing().getOpposite();

        /**
         * Fake startup
         */
        triggerFunc = CompareFunc.GT;
        triggerLevel = 64;
        resetFunc = CompareFunc.LT;
        resetLevel = 16;

        processEvent(SMEvent.BEGIN);
    }

    public abstract int getCurrentValue(TileEntity te);
    public abstract boolean isAdjacentBlockValid(TileEntity te);

    /**
     * IRedstoneOuptut
     */
    public void setRedstoneStrength(Strength s) {
        redstoneStrength = s;
        dirtyRedstoneCfg();
    }
    public Strength getRedstoneStrength() {
        return redstoneStrength;
    }
    public void setRedstoneSense(Sense s) {
        redstoneSense = s;
        dirtyRedstoneCfg();
    }
    public Sense getRedstoneSense() {
        return redstoneSense;
    }
    public void incRedstoneLevel() {
        redstoneLevel++;
        if (redstoneLevel > 15)
            redstoneLevel = 15;
        dirtyRedstoneCfg();
    }
    public void decRedstoneLevel() {
        redstoneLevel--;
        if (redstoneLevel < 0)
            redstoneLevel = 0;
        dirtyRedstoneCfg();
    }
    public int getRedstoneLevel() {
        return redstoneLevel;
    }
    public void setRedstoneLevel(int v) {
        redstoneLevel = MathHelper.clampI(v, 0, 15);
        dirtyRedstoneCfg();
    }
    public void setRedstoneOutputFace(ForgeDirection f) {
        redstoneOutputFace = f;
        dirtyRedstoneCfg();
    }
    public ForgeDirection getRedstoneOutputFace() {
        return redstoneOutputFace;
    }

    /**
     * State Machine
     */
    enum SMEvent {
        BEGIN, VALID_ADJ_TE, INVALID_ADJ_TE, ENABLED, DISABLED, TRIGGER_MET, RESET_MET
    }

    enum SMState {
        INIT {
            public SMState processEvent(SMEvent e) {
                if (e == SMEvent.BEGIN)
                    return UNCONNECTED;
                return this;
            }
        },
        UNCONNECTED {
            public SMState processEvent(SMEvent e) {
                if (e == SMEvent.VALID_ADJ_TE)
                    return CONNECTED;
                return this;
            }
        },
        CONNECTED {
            public SMState processEvent(SMEvent e) {
                if (e == SMEvent.INVALID_ADJ_TE)
                    return UNCONNECTED;
                else if (e == SMEvent.ENABLED)
                    return RUNNING;
                return this;
            }
        },
        RUNNING {
            public SMState processEvent(SMEvent e) {
                if (e == SMEvent.INVALID_ADJ_TE)
                    return UNCONNECTED;
                else if (e == SMEvent.DISABLED)
                    return CONNECTED;
                else if (e == SMEvent.TRIGGER_MET)
                    return TRIGGERED;
                return this;
            }
        },
        TRIGGERED {
            public SMState processEvent(SMEvent e) {
                if (e == SMEvent.INVALID_ADJ_TE)
                    return UNCONNECTED;
                else if (e == SMEvent.DISABLED)
                    return CONNECTED;
                else if (e == SMEvent.RESET_MET)
                    return RUNNING;
                return this;
            }
        };

        public abstract SMState processEvent(SMEvent e);

        public static SMState getState(int i) {
            i = MathHelper.clampI(i, 0, SMState.values().length - 1);
            return SMState.values()[i];
        }
    }


    void processEvent(SMEvent e) {
        SMState lastState = currState;
        currState = currState.processEvent(e);

        if (currState != lastState) {
            if (currState == SMState.UNCONNECTED)
                runEnterUnconnected();
            else if (currState == SMState.CONNECTED)
                runEnterConnected();
            else if (currState == SMState.RUNNING)
                runEnterRunning();
            else if (currState == SMState.TRIGGERED)
                runEnterTriggered();
        }

        if (currState == SMState.CONNECTED && enabled)
            processEvent(SMEvent.ENABLED);
    }

    /**
     * All the below states will cause both a visual change
     * and a possible change of the redstone output
     */

    void runEnterUnconnected() {
        sendBlockUpdate(xCoord, yCoord, zCoord);
        sendNbrBlockUpdate(xCoord, yCoord, zCoord);
    }

    void runEnterConnected() {
        sendBlockUpdate(xCoord, yCoord, zCoord);
        sendNbrBlockUpdate(xCoord, yCoord, zCoord);
    }

    void runEnterRunning() {
        sendBlockUpdate(xCoord, yCoord, zCoord);
        sendNbrBlockUpdate(xCoord, yCoord, zCoord);
    }

    void runEnterTriggered() {
        sendBlockUpdate(xCoord, yCoord, zCoord);
        sendNbrBlockUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Status
     */
    public boolean isRunning() {
        return currState == SMState.RUNNING || currState == SMState.TRIGGERED;
    }

    public boolean isConnected() {
        return currState == SMState.CONNECTED || currState == SMState.RUNNING || currState == SMState.TRIGGERED;
    }

    public boolean isTriggered() {
        return currState == SMState.TRIGGERED;
    }

    public boolean isEmittingRedstoneSignal() {
        if (!isRunning())
            return false;

        if (!isTriggered() && redstoneSense == IRedstoneOutput.Sense.INVERTED)
            return true;

        if (isTriggered() && redstoneSense == IRedstoneOutput.Sense.NORMAL)
            return true;

        return false;
    }

    public boolean isEmittingStrongRedstoneSignal(ForgeDirection side) {
        return isRedstoneOutputFace(side) && isEmittingStrongRedstoneSignal();
    }

    public boolean isEmittingStrongRedstoneSignal() {
        return redstoneStrength == Strength.STRONG && isEmittingRedstoneSignal();
    }

    public boolean isEmittingWeakRedstoneSignal(ForgeDirection side) {
        return isRedstoneOutputFace(side) && isEmittingWeakRedstoneSignal();
    }

    public boolean isEmittingWeakRedstoneSignal() {
        return redstoneStrength == Strength.WEAK && isEmittingRedstoneSignal();
    }

    public boolean isRedstoneOutputFace(ForgeDirection d) {
        return redstoneOutputFace == d;
    }

    public int getCurrentRedstoneLevel() {
        if (isEmittingRedstoneSignal())
            return redstoneLevel;

        return 0;
    }

    /**
     * Description Packet
     */
    static final int STATE_FLAGS_OUTPUT_FACE = 0x00000007;
    static final int STATE_FLAGS_CONNECTED = 0x00000010;
    static final int STATE_FLAGS_REDSTONE = 0x00000020;
    static final int STATE_FLAGS_RUNNING = 0x00000040;

    public boolean isStateConnected() { return (super.getState() & STATE_FLAGS_CONNECTED) == STATE_FLAGS_CONNECTED ? true : false; }
    public boolean isStateEmittingRedstone() { return (super.getState() & STATE_FLAGS_REDSTONE) == STATE_FLAGS_REDSTONE ? true : false; }
    public boolean isStateRunning() { return (super.getState() & STATE_FLAGS_RUNNING) == STATE_FLAGS_RUNNING ? true : false; }
    public ForgeDirection getStateOutputFace() {
        int side = super.getState() & STATE_FLAGS_OUTPUT_FACE;
        side = MathHelper.clampI(side, 0, 5);
        return ForgeDirection.getOrientation(side);
    }


    @Override
    public int getState() {
        int t = 0;
        t |= (redstoneOutputFace.ordinal() & STATE_FLAGS_OUTPUT_FACE);
        t |= (isConnected() ? STATE_FLAGS_CONNECTED : 0);
        t |= (isEmittingRedstoneSignal() ? STATE_FLAGS_REDSTONE : 0);
        t |= (isRunning() ? STATE_FLAGS_RUNNING : 0);
        setState(t);
        return super.getState();
    }

    /**
     * Update
     */
    public void updateEntity() {
        if (worldObj.isRemote)
            return;

        /* Check the levels every 1s */
        if ((worldObj.getWorldTime() % 20) != 0)
            return;

        TileEntity adjacentTe = BlockHelper.getAdjacentTileEntity(this, getFacing());
        if (currState == SMState.RUNNING) {
            if (triggerFunc.check(triggerLevel, getCurrentValue(adjacentTe)))
                processEvent(SMEvent.TRIGGER_MET);
        } else if (currState == SMState.TRIGGERED) {
            if (resetFunc.check(resetLevel, getCurrentValue(adjacentTe)))
                processEvent(SMEvent.RESET_MET);
        }
    }

    public void onAdjacentUpdate() {
        if (worldObj.isRemote)
            return;

        TileEntity adjacentTe = BlockHelper.getAdjacentTileEntity(this, getFacing());
        processEvent(isAdjacentBlockValid(adjacentTe) ? SMEvent.VALID_ADJ_TE : SMEvent.INVALID_ADJ_TE);
    }

    /**
     * Rotation
     */
    public void rotateBlock(ForgeDirection axis) {
        setFacing(getFacing().getRotation(axis));
        redstoneOutputFace = redstoneOutputFace.getRotation(axis);
        sendBlockUpdate(xCoord, yCoord, zCoord);
        sendNbrBlockUpdate(xCoord, yCoord, zCoord);
        onAdjacentUpdate();
    }

    /**
     * NBT
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        currState = SMState.getState(nbtTagCompound.getByte(Nbt.HYST_SM_STATE));
        enabled = nbtTagCompound.getBoolean(Nbt.HYST_ENABLED);

        triggerLevel = nbtTagCompound.getInteger(Nbt.HYST_TRIGGER_LEVEL);
        resetLevel = nbtTagCompound.getInteger(Nbt.HYST_RESET_LEVEL);
        triggerFunc = CompareFunc.getType(nbtTagCompound.getByte(Nbt.HYST_TRIGGER_FUNC));
        resetFunc = CompareFunc.getType(nbtTagCompound.getByte(Nbt.HYST_RESET_FUNC));

        redstoneLevel = nbtTagCompound.getByte(Nbt.HYST_REDSTONE_LEVEL);
        redstoneOutputFace = ForgeDirection.getOrientation(nbtTagCompound.getByte(Nbt.HYST_REDSTONE_FACE));
        redstoneSense = (nbtTagCompound.getByte(Nbt.HYST_REDSTONE_SENSE) == 1 ? Sense.NORMAL : Sense.INVERTED);
        redstoneStrength = (nbtTagCompound.getByte(Nbt.HYST_REDSTONE_STRENGTH) == 1 ? Strength.STRONG : Strength.WEAK);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Nbt.HYST_SM_STATE, (byte) currState.ordinal());
        nbtTagCompound.setBoolean(Nbt.HYST_ENABLED, enabled);

        nbtTagCompound.setInteger(Nbt.HYST_TRIGGER_LEVEL, triggerLevel);
        nbtTagCompound.setInteger(Nbt.HYST_RESET_LEVEL, resetLevel);
        nbtTagCompound.setByte(Nbt.HYST_TRIGGER_FUNC, (byte) triggerFunc.ordinal());
        nbtTagCompound.setByte(Nbt.HYST_RESET_FUNC, (byte) resetFunc.ordinal());

        nbtTagCompound.setByte(Nbt.HYST_REDSTONE_LEVEL, (byte) redstoneLevel);
        nbtTagCompound.setByte(Nbt.HYST_REDSTONE_FACE, (byte)redstoneOutputFace.ordinal());
        nbtTagCompound.setByte(Nbt.HYST_REDSTONE_SENSE, (byte) (redstoneSense == Sense.NORMAL ? 1 : 0));
        nbtTagCompound.setByte(Nbt.HYST_REDSTONE_STRENGTH, (byte) (redstoneStrength == Strength.STRONG ? 1 : 0));
    }

    /**
     * HysteresisCfg
     */

    public int getTriggerLevel() {
        return this.triggerLevel;
    }
    public void setTriggerLevel(int v) {
        this.triggerLevel = v;
        dirtyHysteresisCfg();
    }
    public int getResetLevel() {
        return this.resetLevel;
    }
    public void setResetLevel(int v) {
        this.resetLevel = v;
        dirtyHysteresisCfg();
    }
    public CompareFunc getTriggerFunc() {
        return this.triggerFunc;
    }
    public void setTriggerFunc(CompareFunc f) {
        this.triggerFunc = f;
        dirtyHysteresisCfg();
    }
    public CompareFunc getResetFunc() {
        return this.resetFunc;
    }
    public void setResetFunc(CompareFunc f) {
        this.resetFunc = f;
        dirtyHysteresisCfg();
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    public void setEnabled(boolean b) {
        this.enabled = b;
        dirtyHysteresisCfg();
    }

    void dirtyHysteresisCfg() {
        if (worldObj == null)
            return;

        if (worldObj.isRemote) {
            /* Client has updated via the gui */
            sendUpdatePacketTo(Side.SERVER, new MessageHysteresisCfg(this));
        } else {
            sendUpdatePacketTo(Side.CLIENT, new MessageHysteresisUpdate(this));
            sendBlockUpdate(this.xCoord, this.yCoord, this.zCoord);
            sendNbrBlockUpdate(this.xCoord, this.yCoord, this.zCoord);
            markDirty();
        }
    }

    public void handleMessageHysteresisCfg(MessageHysteresisCfg m, EntityPlayerMP player) {
        triggerLevel = m.trigger;
        resetLevel = m.reset;
        triggerFunc = CompareFunc.getType(m.triggerFunc);
        resetFunc = CompareFunc.getType(m.resetFunc);
        enabled = m.enabled == 1;
        processEvent(isEnabled() ? SMEvent.ENABLED : SMEvent.DISABLED);
        dirtyHysteresisCfg();
    }

    public void handleMessageRedstoneOutputCfg(MessageRedstoneOutputCfg m, EntityPlayerMP player) {
        redstoneStrength = m.strength == true ? Strength.STRONG : Strength.WEAK;
        redstoneSense = m.sense == true ? Sense.NORMAL : Sense.INVERTED;
        redstoneLevel = m.level;
        dirtyRedstoneCfg();
    }

    /* Client only */
    public void handleMessageHysteresisUpdate(MessageHysteresisUpdate m) {
        triggerLevel = m.trigger;
        resetLevel = m.reset;
        triggerFunc = CompareFunc.getType(m.triggerFunc);
        resetFunc = CompareFunc.getType(m.resetFunc);
        enabled = m.enabled == 1;
        redstoneStrength = m.strength == true ? Strength.STRONG : Strength.WEAK;
        redstoneSense = m.sense == true ? Sense.NORMAL : Sense.INVERTED;
        redstoneLevel = m.level;
    }

    void dirtyRedstoneCfg() {
        if (worldObj == null)
            return;

        if (worldObj.isRemote) {
            /* Client has updated via the gui */
            sendUpdatePacketTo(Side.SERVER,
                    new MessageRedstoneOutputCfg(this.redstoneStrength, this.redstoneSense, this.redstoneLevel, this.xCoord, this.yCoord, this.zCoord));
        } else {
            sendUpdatePacketTo(Side.CLIENT, new MessageHysteresisUpdate(this));
            sendBlockUpdate(this.xCoord, this.yCoord, this.zCoord);
            sendNbrBlockUpdate(this.xCoord, this.yCoord, this.zCoord);
            markDirty();
        }
    }
}
