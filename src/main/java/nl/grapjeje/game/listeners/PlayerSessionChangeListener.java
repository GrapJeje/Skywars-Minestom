package nl.grapjeje.game.listeners;

import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import nl.grapjeje.Listen;
import nl.grapjeje.Listener;
import nl.grapjeje.Main;
import nl.grapjeje.game.SkyWarsGame;
import nl.grapjeje.game.SkyWarsPlayer;
import nl.grapjeje.game.enums.PlayerState;

public class PlayerSessionChangeListener implements Listener {

    @Listen
    public void onJoin(PlayerSpawnEvent e) {
        SkyWarsGame game = Main.getGame();
        game.addPlayer(SkyWarsPlayer.get(e.getPlayer()));
    }

    @Listen
    public void onLeave(PlayerDisconnectEvent e) {
        SkyWarsGame game = Main.getGame();
        game.removePlayer(SkyWarsPlayer.get(e.getPlayer()));
    }
}
