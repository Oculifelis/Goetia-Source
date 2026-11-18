package teamroots.goetia.mixin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LivingEntity.class)
public abstract class LivingAttackMixin {
 @Inject(method="tick",at=@At("HEAD"))
 private void goetia$livingTick(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci){if((Object)this instanceof net.minecraft.world.entity.player.Player p)teamroots.goetia.GoetiaEvents.livingTick(p);}
 @org.spongepowered.asm.mixin.Shadow protected java.util.Stack<net.neoforged.neoforge.common.damagesource.DamageContainer> damageContainers;
 @Inject(method="actuallyHurt",at=@At("HEAD"))
 private void goetia$releasedHurtPhase(net.minecraft.server.level.ServerLevel level,DamageSource source,float amount,org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci){var c=damageContainers.peek();c.setNewDamage(teamroots.goetia.GoetiaCombat.hurtAmount((LivingEntity)(Object)this,source,c.getNewDamage()));if(teamroots.goetia.GoetiaCombat.voracious(source))c.setPostAttackInvulnerabilityTicks(0);}
 @Inject(method="hurtServer",at=@At("HEAD"),cancellable=true)
 private void goetia$attackBeforeImmunity(net.minecraft.server.level.ServerLevel level,DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir){if(teamroots.goetia.GoetiaCombat.attackHead((LivingEntity)(Object)this,source))cir.setReturnValue(false);}
}
