package teamroots.goetia.mixin;
@org.spongepowered.asm.mixin.Mixin(net.minecraft.world.entity.projectile.AbstractHurtingProjectile.class)
public abstract class LargeFireballPunchMixin {
 @org.spongepowered.asm.mixin.injection.Inject(method="hurt",at=@org.spongepowered.asm.mixin.injection.At("HEAD"),cancellable=true)
 private void goetia$punch(net.minecraft.world.damagesource.DamageSource source,float amount,org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> ci){if((Object)this instanceof net.minecraft.world.entity.projectile.LargeFireball shot&&((teamroots.goetia.LegacyFireballFlight)shot).goetia$hasFlight())ci.setReturnValue(teamroots.goetia.GoetiaFireballs.punch(shot,source));}
}
