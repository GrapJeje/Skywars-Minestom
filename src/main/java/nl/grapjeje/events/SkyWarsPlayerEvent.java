package nl.grapjeje.events;

import lombok.Getter;
import nl.grapjeje.game.SkyWarsPlayer;

public abstract class SkyWarsPlayerEvent extends SkyWarsEvent {
    @Getter
    private final SkyWarsPlayer player;

    public SkyWarsPlayerEvent(SkyWarsPlayer player) {
        this.player = player;
    }
}
