package nl.grapjeje.game.enums;

import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.enchant.Enchantment;

import java.util.List;
import java.util.Random;

public enum ItemTable {
    WOODEN_SWORD(Material.WOODEN_SWORD, 1, 1, 35, null),
    STONE_SWORD(Material.STONE_SWORD, 1, 1, 25, null),
    IRON_SWORD(Material.IRON_SWORD, 1, 1, 15, new Integer[]{1,2}),
    BOW(Material.BOW, 1, 1, 20, new Integer[]{1,2}),
    ARROW(Material.ARROW, 8, 16, 40, null),

    LEATHER_HELMET(Material.LEATHER_HELMET, 1, 1, 30, null),
    LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, 1, 1, 25, null),
    LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, 1, 1, 20, null),
    LEATHER_BOOTS(Material.LEATHER_BOOTS, 1, 1, 20, null),

    CHAIN_HELMET(Material.CHAINMAIL_HELMET, 1, 1, 15, null),
    CHAIN_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE, 1, 1, 10, null),
    CHAIN_LEGGINGS(Material.CHAINMAIL_LEGGINGS, 1, 1, 10, null),
    CHAIN_BOOTS(Material.CHAINMAIL_BOOTS, 1, 1, 10, null),

    IRON_HELMET(Material.IRON_HELMET, 1, 1, 8, null),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 1, 1, 6, null),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, 1, 1, 5, null),
    IRON_BOOTS(Material.IRON_BOOTS, 1, 1, 5, null),

    GOLDEN_APPLE(Material.GOLDEN_APPLE, 1, 3, 70, null),
    GOLDEN_ENCHANTED_APPLE(Material.ENCHANTED_GOLDEN_APPLE, 1, 1, 1, null),

    OAK_PLANKS(Material.OAK_PLANKS, 16, 32, 40, null),
    COBBLESTONE(Material.COBBLESTONE, 16, 32, 35, null),

    WATER_BUCKET(Material.WATER_BUCKET, 1, 1, 10, null),
    LAVA_BUCKET(Material.LAVA_BUCKET, 1, 1, 5, null),
    SNOWBALL(Material.SNOWBALL, 4, 8, 15, null),

    EGG(Material.EGG, 4, 8, 15, null),
    ENDER_PEARL(Material.ENDER_PEARL, 1, 1, 5, null),

    DIAMOND_SWORD(Material.DIAMOND_SWORD, 1, 1, 3, new Integer[]{1,2,3}),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, 1, 1, 2, new Integer[]{1,2}),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 1, 1, 1, new Integer[]{1,2});

    public final Material mat;
    public final int amount_1;
    public final int amount_2;
    public final double chance;
    public final Integer[] enchantmentLvls;

    ItemTable(Material mat, int amount_1, int amount_2, double chance, Integer[] enchantmentLvls) {
        this.mat = mat;
        this.amount_1 = amount_1;
        this.amount_2 = amount_2;
        this.chance = chance;
        this.enchantmentLvls = enchantmentLvls;
    }

    public int randomAmount() {
        return new Random().nextInt(amount_2 - amount_1 + 1) + amount_1;
    }

    public boolean doesItemSpawn() {
        return this.spawns(this.chance);
    }

    public boolean doesItemSpawn(double booster) {
        double chance = booster + this.chance;
        return this.spawns(chance);
    }

    private boolean spawns(double chance) {
        double roll = new Random().nextDouble() * 100;
        return roll < chance;
    }

    public int randomEnchantmentLvl() {
        if (enchantmentLvls == null || enchantmentLvls.length == 0) return 0;
        return enchantmentLvls[new Random().nextInt(enchantmentLvls.length)];
    }

    public static ItemStack getRandomItemStack() {
        ItemTable[] items = ItemTable.values();
        Random random = new Random();

        ItemTable selected = items[random.nextInt(items.length)];
        if (!selected.doesItemSpawn()) return null;

        // Random amount
        int amount = selected.randomAmount();
        ItemStack stack = ItemStack.of(selected.mat, amount);

        // Voeg default enchantments toe via ItemMeta
        int level = selected.randomEnchantmentLvl();
        if (level == 0) level = 1;

//        switch (stack.material()) {
//            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, DIAMOND_SWORD -> {
//                stack.with(DataComponents.ENCHANTMENTS);
//            }
//            case BOW -> {
//                EnchantedItemMeta meta = (EnchantedItemMeta) stack.meta();
//                meta.addEnchantment(Enchantment.ARROW_DAMAGE, level); // Power
//                stack.setMeta(meta);
//            }
//            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS,
//                 Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
//                 IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
//                 DIAMOND_HELMET, DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS -> {
//                EnchantedItemMeta meta = (EnchantedItemMeta) stack.meta();
//                meta.addEnchantment(Enchantment.PROTECTION, level);
//                stack.setMeta(meta);
//            }
//            default -> { /* andere items geen enchantment */ }
//        }

        return stack;
    }


//    public static ItemStack createEnchantedItem(Material mat, int amount, Enchantment enchantment, int level) {
//        ItemStack item = ItemStack.builder(mat)
//                .amount(amount)
//                .set(DataComponents.ENCHANTMENTS, List.of(new Component(enchantment, level)))
//                .build();
//        return item;
//    }
}
