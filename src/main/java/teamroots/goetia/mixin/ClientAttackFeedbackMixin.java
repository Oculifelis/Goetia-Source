package teamroots.goetia.mixin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.GoetiaCombat;
@Mixin(Entity.class)
public abstract class ClientAttackFeedbackMixin {
 @Inject(method="hurtClient",at=@At("HEAD"))
 private void goetia$feedback(DamageSource source,CallbackInfoReturnable<Boolean> cir){if((Object)this instanceof LivingEntity entity&&entity.level().isClientSide())GoetiaCombat.attackHead(entity,source);}
}
