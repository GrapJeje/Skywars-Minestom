package nl.grapjeje;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.crypto.ChatSession;
import net.minestom.server.entity.*;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class FakePlayerEntity extends LivingEntity {
    @Getter
    private static PlayerInfoUpdatePacket.Entry baseInfoPacket = new PlayerInfoUpdatePacket.Entry(UUID.fromString("37819856-6e9a-4866-9a73-922c8c684b21"), "GrapJeje",
            List.of(), false, 0, GameMode.SURVIVAL, Component.text("GrapJeje"), (ChatSession) null, 0, true);

    @Getter
    private String name;
    private String customName = "GrapJeje";

    @Getter
    private final UUID uuid;

    public FakePlayerEntity(Instance instance, Pos pos, String playerName) {
        super(EntityType.PLAYER);
        this.name = playerName;
        this.uuid = UUID.randomUUID();
        this.setHealth(20f);
        this.setInstance(instance, pos);
        Main.getInstanceContainer().sendGroupedPacket(this.getPacket());
        for (@NotNull Player player : Main.getInstanceContainer().getPlayers()) {
            addViewer(player);
        }
    }

    public ServerPacket getPacket() {
        return new PlayerInfoUpdatePacket(EnumSet.of(PlayerInfoUpdatePacket.Action.ADD_PLAYER, PlayerInfoUpdatePacket.Action.UPDATE_LISTED), List.of(this.infoEntry(name, customName)));
    }

    public PlayerInfoUpdatePacket.Entry infoEntry(String playerName, String customName) {
        try {
            PlayerSkin skin = PlayerSkin.fromUsername(playerName);
            List<PlayerInfoUpdatePacket.Property> prop = skin != null ? List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature())) : List.of();
            return new PlayerInfoUpdatePacket.Entry(this.getUuid(), customName, prop, false, 0, GameMode.SURVIVAL, Component.text(customName), (ChatSession) null, 0, true);
        } catch (Exception e) {
            return getBaseInfoPacket();
        }
    }
}
