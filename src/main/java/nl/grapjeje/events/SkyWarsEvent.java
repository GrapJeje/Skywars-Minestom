package nl.grapjeje.events;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import nl.grapjeje.Main;
import nl.grapjeje.game.SkyWarsGame;

public abstract class SkyWarsEvent implements Event {
    @Getter
    private final SkyWarsGame game = Main.getGame();

    public void trigger() {
        MinecraftServer.getGlobalEventHandler().call(this);
    }
}
