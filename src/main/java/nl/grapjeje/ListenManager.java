package nl.grapjeje;

import nl.grapjeje.game.listeners.PlayerSessionChangeListener;
import nl.grapjeje.listeners.ChestListener;
import nl.grapjeje.listeners.PlayerDamageListener;
import nl.grapjeje.listeners.PlayerLoginListener;

public class ListenManager {

    public void init() {
        // Main
        new PlayerLoginListener().register();
        new PlayerDamageListener().register();
        new ChestListener().register();

        // Game
        new PlayerSessionChangeListener().register();
    }
}
