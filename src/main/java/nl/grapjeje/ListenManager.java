package nl.grapjeje;

import nl.grapjeje.game.listeners.PlayerSessionChangeListener;
import nl.grapjeje.listeners.PlayerDamageListener;
import nl.grapjeje.listeners.PlayerLoginListener;

public class ListenManager {

    public void init() {
        // Main
        new PlayerLoginListener().register();
        new PlayerDamageListener().register();

        // Game
        new PlayerSessionChangeListener().register();
    }
}
