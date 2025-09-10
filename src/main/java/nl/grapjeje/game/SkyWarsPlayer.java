package nl.grapjeje.game;

import lombok.Getter;
import lombok.Setter;
import net.minestom.server.entity.Player;
import nl.grapjeje.game.enums.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsPlayer {
    private static final List<SkyWarsPlayer> initPlayers = new ArrayList<>();

    @Getter
    private Player basePlayer;
    @Getter
    @Setter
    private PlayerState state;

    private SkyWarsPlayer(Player player, PlayerState playerState) {
        this.basePlayer = player;
        this.state = playerState;

        initPlayers.add(this);
    }

    public static SkyWarsPlayer get(Player player) {
        SkyWarsPlayer temp = initPlayers.stream()
                .filter(sp -> sp.getBasePlayer().equals(player))
                .findFirst()
                .orElse(null);
        if (temp == null) return new SkyWarsPlayer(player, PlayerState.IN_LOBBY);
        else return temp;
    }
}
