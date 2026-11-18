package teamroots.goetia.mixin;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.*;
import teamroots.goetia.LegacyProjectileOwner;
@Mixin(Projectile.class)
public abstract class ProjectileOwnerMixin implements LegacyProjectileOwner {
 @Shadow protected abstract void onHit(net.minecraft.world.phys.HitResult hit);
 @org.spongepowered.asm.mixin.injection.Inject(method="hitTargetOrDeflectSelf",at=@org.spongepowered.asm.mixin.injection.At("HEAD"),cancellable=true)
 private void goetia$releasedImpact(net.minecraft.world.phys.HitResult hit,org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<net.minecraft.world.entity.projectile.ProjectileDeflection> ci){if((Object)this instanceof teamroots.goetia.LegacyFireballFlight flight&&flight.goetia$hasFlight()){onHit(hit);ci.setReturnValue(net.minecraft.world.entity.projectile.ProjectileDeflection.NONE);}}
 @Shadow private UUID ownerUUID;
 @Shadow private Entity cachedOwner;
 @Override public void goetia$clearOwner(){ownerUUID=null;cachedOwner=null;}
}
