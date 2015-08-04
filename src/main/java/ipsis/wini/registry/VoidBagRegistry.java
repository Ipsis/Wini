package ipsis.wini.registry;

import ipsis.oss.util.LogHelper;
import ipsis.wini.item.ItemVoidBag;
import ipsis.wini.utils.ComparableItemStackBlockSafe;
import ipsis.wini.utils.ItemContainerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.*;

public class VoidBagRegistry {

    private static VoidBagRegistry registry;
    private Map<UUID, PlayerBagRegistry> playerMap;

    private VoidBagRegistry() {
        playerMap = new HashMap<UUID, PlayerBagRegistry>();
    }

    public static VoidBagRegistry getInstance() {
        if (registry == null)
            registry = new VoidBagRegistry();
        return registry;
    }

    /* Debug */
    private void dumpRegistry() {
        for (PlayerBagRegistry playerBagRegistry : playerMap.values()) {
            LogHelper.info("dumpRegistry: " + playerBagRegistry.playerUUID);
            for (VoidBag voidBag : playerBagRegistry.registry.values()) {
                LogHelper.info("dumpRegistry: " + voidBag);
            }
        }
    }

    /**
     * Adds a new player, overwriting current information if it exists
     */
    public void setPlayer(EntityPlayer entityPlayer) {
        if (entityPlayer != null)
            playerMap.put(entityPlayer.getUniqueID(), new PlayerBagRegistry(entityPlayer.getUniqueID()));
    }

    public void delPlayer(EntityPlayer entityPlayer) {
        if (entityPlayer != null)
            playerMap.remove(entityPlayer.getUniqueID());
    }

    /**
     * Add a new bag and extracts the contents
     */
    public void setBag(EntityPlayer entityPlayer, ItemStack bagStack) {
        if (entityPlayer == null || bagStack == null || !(bagStack.getItem() instanceof ItemVoidBag))
            return;

        boolean isLocked = ItemVoidBag.isLocked(bagStack);
        PlayerBagRegistry playerEntry = playerMap.get(entityPlayer.getUniqueID());
        if (playerEntry != null) {
            UUID bagUUID = ItemVoidBag.getBagUUID(bagStack);
            VoidBag bag = new VoidBag(bagUUID, ItemContainerHelper.getContents(bagStack));
            bag.locked = isLocked;
            playerEntry.registry.put(bagUUID, bag);
        }
    }

    public boolean hasBag(EntityPlayer entityPlayer, ItemStack bagStack) {
        if (entityPlayer != null && bagStack != null && bagStack.getItem() instanceof ItemVoidBag) {
            PlayerBagRegistry playerEntry = playerMap.get(entityPlayer.getUniqueID());
            if (playerEntry != null)
                return playerEntry.registry.containsKey(ItemVoidBag.getBagUUID(bagStack));
        }

        return false;
    }

    public void purgeBags(EntityPlayer entityPlayer, List<UUID> validBagList) {
        if (entityPlayer != null) {
            PlayerBagRegistry playerEntry = playerMap.get(entityPlayer.getUniqueID());
            if (playerEntry != null)
                playerEntry.registry.keySet().retainAll(validBagList);
        }
    }

    public boolean hasMatch(EntityPlayer entityPlayer, ItemStack stack) {
        if (entityPlayer != null && stack != null) {
            PlayerBagRegistry playerEntry = playerMap.get(entityPlayer.getUniqueID());
            if (playerEntry != null)
                return playerEntry.hasMatch(stack);
        }

        return false;
    }

    public void setBagLock(EntityPlayer entityPlayer, ItemStack bagStack) {
        if (entityPlayer == null || bagStack == null || !(bagStack.getItem() instanceof ItemVoidBag))
            return;

        boolean isLocked = ItemVoidBag.isLocked(bagStack);
        PlayerBagRegistry playerEntry = playerMap.get(entityPlayer.getUniqueID());
        if (playerEntry != null) {
            VoidBag bag = playerEntry.registry.get(ItemVoidBag.getBagUUID(bagStack));
            if (bag != null)
                bag.locked = isLocked;
        }
    }


    /**
     * PlayerBagRegistry
     * Player UUID and a set of the void bags the player have
     */
    private class PlayerBagRegistry {
        public UUID playerUUID;
        public Map<UUID, VoidBag> registry;

        private PlayerBagRegistry() { }
        public PlayerBagRegistry(UUID playerUUID) {
            this.playerUUID = playerUUID;
            registry = new HashMap<UUID, VoidBag>();
        }

        public boolean hasMatch(ItemStack stack) {
            ComparableItemStackBlockSafe compareStack = new ComparableItemStackBlockSafe(stack);
            for (VoidBag bag : registry.values()) {
                if (!bag.locked && bag.contents.contains(compareStack))
                    return true;
            }

            return false;
        }
    }

    /**
     * VoidBag
     * Bag UUID and a set of the bag contents
     */
    private class VoidBag {
        public UUID bagUUID;
        boolean locked;
        Set<ComparableItemStackBlockSafe> contents;

        private VoidBag() { }
        public VoidBag(UUID bagUUID, List<ItemStack> items) {
            this.bagUUID = bagUUID;
            locked = false;
            contents = new HashSet<ComparableItemStackBlockSafe>();

            for (ItemStack itemStack : items) {
                if (itemStack != null)
                    contents.add(new ComparableItemStackBlockSafe(itemStack));
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(bagUUID).append(" ").append(locked).append(" ");
            for (ComparableItemStackBlockSafe stack : contents)
                sb.append(stack.toItemStack()).append(" ");

            return sb.toString();
        }
    }
}
