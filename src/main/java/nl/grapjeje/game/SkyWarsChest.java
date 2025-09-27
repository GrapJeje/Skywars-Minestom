package nl.grapjeje.game;

import lombok.Getter;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import nl.grapjeje.threading.threads.TimedRepeatingDelayedThread;

import java.util.*;

@Getter
public class SkyWarsChest {
    private static List<SkyWarsChest> chests = new ArrayList<>();

    private Pos pos;
    private Inventory inventory;

    private long cooldownEnd = 0;
    private Entity hologram;

    private boolean filled;

    private SkyWarsChest(Pos pos, Inventory inv) {
        this.pos = pos;
        this.inventory = inv;
        this.filled = false;

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
        this.filled = true;
    }

    public void open(Player player) {
        if (!this.isFilled()) {
            this.supply();
            cooldownEnd = System.currentTimeMillis() + 120_000;
            this.startTimer();
        }

        player.openInventory(this.getInventory());
        player.playSound(
                Sound.sound(
                        SoundEvent.BLOCK_CHEST_OPEN,
                        Sound.Source.BLOCK,
                        1f, 1f
                )
        );
    }

    private TimedRepeatingDelayedThread cooldownTextThread;

    private void startTimer() {
        hologram = new Entity(EntityType.TEXT_DISPLAY);
        hologram.setInstance(
                MinecraftServer.getInstanceManager().getInstances().iterator().next(),
                pos.add(0.5, 1.5, 0.5)
        );

        TextDisplayMeta meta = (TextDisplayMeta) hologram.getEntityMeta();
        meta.setText(Component.text("§40:00"));
        meta.setSeeThrough(true);
        meta.setBackgroundColor(0x00000000);

        cooldownTextThread = new TimedRepeatingDelayedThread(() -> {
            long remaining = (cooldownEnd - System.currentTimeMillis()) / 1000;
            if (remaining <= 0) {
                cooldownTextThread.setCancelled(true);
                cooldownTextThread = null;
                this.filled = false;
                this.supply();
                hologram.remove();
                return;
            }

            long minutes = remaining / 60;
            long seconds = remaining % 60;
            String time = String.format("%d:%02d", minutes, seconds);

            meta.setText(Component.text("§4" + time));
        }, 1000, -1, 0);
    }

    public boolean isEmpty() {
        return Arrays.stream(inventory.getItemStacks())
                .allMatch(item -> item == null || item.material() == Material.AIR);
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
