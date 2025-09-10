package nl.grapjeje.listeners;

import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.entity.EntityAttackEvent;
import nl.grapjeje.FakePlayerEntity;
import nl.grapjeje.Listen;
import nl.grapjeje.Listener;

public class PlayerDamageListener implements Listener {

    @Listen
    public void onEntityAttack(EntityAttackEvent e) {
        if (!(e.getEntity() instanceof Player attacker)) return;

        if (e.getTarget() instanceof Player attacked) {
            this.damage(attacked);
            attacker.sendMessage("Je hebt " + attacked.getUsername() + " geraakt!");
            return;
        }

        if (e.getTarget() instanceof FakePlayerEntity attacked) {
            this.damage(attacked);
            attacker.sendMessage("Je hebt " + attacked.getName() + " geraakt!");
            return;
        }
    }

    private void damage(LivingEntity entity) {
        entity.damage(DamageType.PLAYER_ATTACK, 1f);
    }
}
