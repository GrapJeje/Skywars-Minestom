package nl.grapjeje.listeners;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;
import nl.grapjeje.Listen;
import nl.grapjeje.Listener;
import nl.grapjeje.game.SkyWarsChest;

import java.util.HashMap;
import java.util.Map;

public class ChestListener implements Listener {

    @Listen
    public void onInteract(PlayerBlockInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (!block.compare(Block.CHEST)) return;
        Pos chestPos = e.getBlockPosition().asPos();

        SkyWarsChest chest = SkyWarsChest.get(chestPos);
        chest.open(player);
    }
}
