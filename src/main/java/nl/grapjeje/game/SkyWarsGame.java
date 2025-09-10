package nl.grapjeje.game;

import lombok.Getter;
import lombok.Setter;
import nl.grapjeje.game.enums.GameState;
import nl.grapjeje.threading.threads.TimedRepeatingDelayedThread;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsGame {
    @Getter
    private List<SkyWarsPlayer> players = new ArrayList<>();
    @Getter @Setter
    private GameState gameState;

    private long tickTime = 0;

    public SkyWarsGame() {
        this.gameState = GameState.LOBBY;

        new TimedRepeatingDelayedThread(
                () -> tick(System.currentTimeMillis()),
                50, -1, 0
        );
    }

    private void tick(long time) {
        this.tickTime = time;
    }

    public void addPlayer(SkyWarsPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(SkyWarsPlayer player) {
        this.players.remove(player);
    }
}
