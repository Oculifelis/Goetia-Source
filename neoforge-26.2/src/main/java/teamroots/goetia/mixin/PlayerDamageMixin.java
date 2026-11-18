package teamroots.goetia.mixin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Player.class)
public abstract class PlayerDamageMixin extends LivingEntity {
 protected PlayerDamageMixin(EntityType<? extends LivingEntity> type,Level level){super(type,level);}
 @Inject(method="actuallyHurt",at=@At("HEAD"))
 private void goetia$releasedPlayerHurtPhase(net.minecraft.server.level.ServerLevel level,DamageSource source,float amount,CallbackInfo ci){var c=damageContainers.peek();c.setNewDamage(teamroots.goetia.GoetiaCombat.hurtAmount(this,source,c.getNewDamage()));if(teamroots.goetia.GoetiaCombat.voracious(source))c.setPostAttackInvulnerabilityTicks(0);}
}
