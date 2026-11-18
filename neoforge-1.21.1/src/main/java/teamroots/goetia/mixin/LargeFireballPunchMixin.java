package teamroots.goetia.mixin;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.*;
@Mixin(Fireball.class)
public abstract class LargeFireballPunchMixin {
 @Inject(method="hurt",at=@At("HEAD"),cancellable=true)
 private void goetia$punch(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir){if((Object)this instanceof LargeFireball shot&&((LegacyFireballFlight)shot).goetia$hasFlight())cir.setReturnValue(GoetiaFireballs.punch(shot,source));}
}
