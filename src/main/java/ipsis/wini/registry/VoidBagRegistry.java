package ipsis.wini.registry;

import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.oss.util.LogHelper;
import ipsis.wini.item.ItemVoidBag;
import ipsis.wini.utils.ComparableItemStackBlockSafe;
import ipsis.wini.utils.ItemContainerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.*;

public class VoidBagRegistry {

    private VoidBagRegistry() {
        playerMap = new HashMap<UUID, PlayerBags>();
    }
    private static VoidBagRegistry registry;

    private HashMap<UUID, PlayerBags> playerMap;


    public static VoidBagRegistry getInstance() {
        if (registry == null)
            registry = new VoidBagRegistry();
        return registry;
    }

    public void addPlayer(EntityPlayer entityPlayer) {
        if (entityPlayer == null)
            return;

        UUID playerUUID = entityPlayer.getUniqueID();
        LogHelper.info("addPlayer:" + playerUUID);
        if (!playerMap.containsKey(playerUUID))
            playerMap.put(playerUUID, new PlayerBags());
    }

    public void delPlayer(EntityPlayer entityPlayer) {
        UUID playerUUID = entityPlayer.getUniqueID();
        LogHelper.info("delPlayer:" + playerUUID);
        playerMap.remove(playerUUID);
    }

    public boolean hasBag(EntityPlayer entityPlayer, ItemStack bagStack) {
        if (entityPlayer == null || bagStack == null || !(bagStack.getItem() instanceof ItemVoidBag))
            return false;

        UUID playerUUID = entityPlayer.getUniqueID();
        if (!playerMap.containsKey(playerUUID))
            return false;

        UUID bagUUID = ItemVoidBag.getBagUUID(bagStack);
        return playerMap.get(playerUUID).hasBag(bagUUID);
    }

    public void addBag(EntityPlayer entityPlayer, ItemStack bagStack) {

        LogHelper.info("addBag");
        if (entityPlayer == null || bagStack == null || !(bagStack.getItem() instanceof ItemVoidBag))
            return;

        UUID playerUUID = entityPlayer.getUniqueID();
        if (!playerMap.containsKey(playerUUID))
            return;

        UUID bagUUID = ItemVoidBag.getBagUUID(bagStack);
        List<ItemStack> bagContents = ItemContainerHelper.getContents(bagStack);
        playerMap.get(playerUUID).addBag(bagUUID, bagContents);
    }

    public boolean hasMatch(EntityPlayer entityPlayer, ItemStack itemStack) {

        if (entityPlayer == null || itemStack == null)
            return false;

        return playerMap.get(entityPlayer.getUniqueID()).hashMatch(itemStack);
    }

    public void purgeOldBags(EntityPlayer entityPlayer, List<UUID> validBags) {

        if (entityPlayer == null || validBags == null)
            return;

        PlayerBags playerBags = playerMap.get(entityPlayer.getUniqueID());
        playerBags.purgeOldBags(validBags);
    }


    private class PlayerBags {

        private HashMap<UUID, Set<ComparableItemStackBlockSafe>> bags;
        public PlayerBags() {
            bags = new HashMap<UUID, Set<ComparableItemStackBlockSafe>>();
        }

        public boolean hasBag(UUID bagUUID) {
            return bags.containsKey(bagUUID);
        }

        public void addBag(UUID bagUUID, List<ItemStack> bagContents) {
            assert(bagUUID != null && bagContents != null);

            HashSet<ComparableItemStackBlockSafe> newBagContents = new HashSet<ComparableItemStackBlockSafe>();
            for (ItemStack currStack : bagContents) {
                if (currStack != null)
                    newBagContents.add(new ComparableItemStackBlockSafe(currStack));
            }
            bags.put(bagUUID, newBagContents);
        }

        public void delBag(UUID bagUUID) {
            bags.remove(bagUUID);
        }

        public boolean hashMatch(ItemStack itemStack) {
            assert(itemStack != null);

            ComparableItemStackSafe compareStack = new ComparableItemStackSafe(itemStack);
            for (Set<ComparableItemStackBlockSafe> currBag : bags.values()) {
                if (currBag.contains(compareStack))
                    return true;
            }

            return false;
        }

        public void purgeOldBags(List<UUID> validBags) {

            Iterator<UUID> iter = bags.keySet().iterator();
            while (iter.hasNext()) {
                UUID uuid = iter.next();
                if (validBags.contains(uuid) == false)
                    iter.remove();
            }
        }
    }
}
