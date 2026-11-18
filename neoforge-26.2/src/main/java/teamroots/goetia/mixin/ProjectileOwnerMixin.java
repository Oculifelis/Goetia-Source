package teamroots.goetia.mixin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.*;
@Mixin(Projectile.class)
public abstract class ProjectileOwnerMixin implements LegacyProjectileOwner {
 @Shadow public abstract void setOwner(Entity owner);
 @Unique private Entity goetia$activeDeflector;
 @Override public void goetia$clearOwner(){setOwner((Entity)null);}
 @Override public Entity goetia$deflector(){return goetia$activeDeflector;}
 @Inject(method="deflect",at=@At("HEAD"))
 private void goetia$rememberDeflector(ProjectileDeflection deflection,Entity deflector,EntityReference<Entity> owner,boolean byAttack,CallbackInfoReturnable<Boolean> cir){if((Object)this instanceof LegacyFireballFlight flight&&flight.goetia$hasFlight())goetia$activeDeflector=deflector;}
 @Inject(method="deflect",at=@At("RETURN"))
 private void goetia$releaseDeflector(ProjectileDeflection deflection,Entity deflector,EntityReference<Entity> owner,boolean byAttack,CallbackInfoReturnable<Boolean> cir){goetia$activeDeflector=null;}
}
