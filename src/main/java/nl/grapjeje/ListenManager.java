package nl.grapjeje;

import nl.grapjeje.listeners.PlayerDamageListener;
import nl.grapjeje.listeners.PlayerLoginListener;

public class ListenManager {

    public void init() {
        new PlayerLoginListener().register();
        new PlayerDamageListener().register();
    }
}
