package nl.grapjeje;

import lombok.Getter;
import lombok.Setter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;

public class Main {
    @Getter
    @Setter
    private static InstanceContainer instanceContainer;

    public static void main(String[] args) {
        // Initialize server
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.setBrandName("Skywars-Minestom");

        // Initialize instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer(DimensionType.OVERWORLD);

        instance.setGenerator(unit -> {
            Point start = unit.absoluteStart();
            Point end = unit.absoluteEnd();

            for (int x = start.blockX(); x < end.blockX(); x++) {
                for (int z = start.blockZ(); z < end.blockZ(); z++) {
                    unit.modifier().setBlock(x, 0, z, Block.GRASS_BLOCK);
                }
            }
        });
        setInstanceContainer(instance);

        // Register Listeners
        new ListenManager().init();

        // Start
        server.start("0.0.0.0", 25565);
    }
}
