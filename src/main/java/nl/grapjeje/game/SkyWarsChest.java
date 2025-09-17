package nl.grapjeje.game;

import lombok.Getter;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;

import java.util.*;

@Getter
public class SkyWarsChest {
    private static List<SkyWarsChest> chests = new ArrayList<>();

    private Pos pos;
    private Inventory inventory;

    private SkyWarsChest(Pos pos, Inventory inv) {
        this.pos = pos;
        this.inventory = inv;

        chests.add(this);
    }

    public static SkyWarsChest get(Pos pos) {
        return chests.stream()
                .filter(sc -> sc.getPos().equals(pos))
                .findFirst()
                .orElse(new SkyWarsChest(pos, new Inventory(InventoryType.CHEST_3_ROW, "Chest")));
    }

    public void supply() {
        Random random = new Random();
        inventory.clear();

        int itemsToAdd = 3 + random.nextInt(4);

        Set<Integer> usedSlots = new HashSet<>();
        int maxSlots = inventory.getSize();

        for (int i = 0; i < itemsToAdd; i++) {
            ItemStack item = itemTable.getRandomItem();
            if (item == null) continue;

            int slot;
            do {
                slot = random.nextInt(maxSlots);
            } while (usedSlots.contains(slot));

            usedSlots.add(slot);
            inventory.setItemStack(slot, item);
        }
    }

    public void open(Player player) {
        this.supply();

        player.openInventory(this.getInventory());
        player.playSound(
                Sound.sound(
                        SoundEvent.BLOCK_CHEST_OPEN,
                        Sound.Source.BLOCK,
                        1f, 1f
                )
        );
    }

    public class itemTable {
        @Getter
        private static Map<ItemStack, Integer> items = new HashMap<>();
        static {
            // Weapons
            items.put(ItemStack.of(Material.WOODEN_SWORD), 30);
            items.put(ItemStack.of(Material.STONE_SWORD), 20);
            items.put(ItemStack.of(Material.IRON_SWORD), 10);

            // Armor
            items.put(ItemStack.of(Material.LEATHER_HELMET), 25);
            items.put(ItemStack.of(Material.LEATHER_CHESTPLATE), 20);
            items.put(ItemStack.of(Material.LEATHER_LEGGINGS), 15);
            items.put(ItemStack.of(Material.LEATHER_BOOTS), 15);

            // Blocks
            items.put(ItemStack.of(Material.OAK_PLANKS, 16), 40);
            items.put(ItemStack.of(Material.COBBLESTONE, 16), 35);

            // Food
            items.put(ItemStack.of(Material.APPLE, 3), 30);
            items.put(ItemStack.of(Material.BREAD, 3), 25);

            // Bow & Arrows
            items.put(ItemStack.of(Material.BOW), 15);
            items.put(ItemStack.of(Material.ARROW, 16), 25);
        }

        private static ItemStack getRandomItem() {
            int totalWeight = items.values().stream().mapToInt(Integer::intValue).sum();
            int rand = new Random().nextInt(totalWeight);
            int cumulative = 0;

            for (Map.Entry<ItemStack, Integer> entry : items.entrySet()) {
                cumulative += entry.getValue();
                if (rand < cumulative) return entry.getKey();
            }
            return null;
        }
    }
}
