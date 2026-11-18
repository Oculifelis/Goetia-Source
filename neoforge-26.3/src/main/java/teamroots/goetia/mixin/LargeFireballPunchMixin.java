package teamroots.goetia.mixin;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.*;
@Mixin(AbstractHurtingProjectile.class)
public abstract class LargeFireballPunchMixin {
 @Inject(method="hurtServer",at=@At("HEAD"),cancellable=true)
 private void goetia$punch(ServerLevel level,DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir){if((Object)this instanceof LargeFireball shot&&((LegacyFireballFlight)shot).goetia$hasFlight())cir.setReturnValue(GoetiaFireballs.punch(shot,source));}
}
