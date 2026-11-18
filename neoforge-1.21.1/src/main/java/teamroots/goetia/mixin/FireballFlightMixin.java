package teamroots.goetia.mixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import teamroots.goetia.LegacyFireballFlight;
@Mixin(AbstractHurtingProjectile.class)
public abstract class FireballFlightMixin extends Projectile implements LegacyFireballFlight {
 @Unique private static final EntityDataAccessor<CompoundTag> GOETIA_POWER=SynchedEntityData.defineId(AbstractHurtingProjectile.class,EntityDataSerializers.COMPOUND_TAG);
 @Shadow protected abstract float getInertia();
 @Shadow protected abstract float getLiquidInertia();
 protected FireballFlightMixin(EntityType<? extends Projectile> type,Level level){super(type,level);}
 @Override public void goetia$definePower(SynchedEntityData.Builder builder){builder.define(GOETIA_POWER,new CompoundTag());}
 @Override public void goetia$flight(Vec3 power){var d=new CompoundTag();d.putDouble("x",power.x);d.putDouble("y",power.y);d.putDouble("z",power.z);entityData.set(GOETIA_POWER,d);}
 @Override public boolean goetia$hasFlight(){return !entityData.get(GOETIA_POWER).isEmpty();}
 @Override public Vec3 goetia$power(){var d=entityData.get(GOETIA_POWER);return new Vec3(d.getDouble("x"),d.getDouble("y"),d.getDouble("z"));}
 @ModifyArg(method="tick",at=@At(value="INVOKE",target="Lnet/minecraft/world/entity/projectile/AbstractHurtingProjectile;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"),index=0)
 private Vec3 goetia$fixedAcceleration(Vec3 ordinary){return goetia$hasFlight()?getDeltaMovement().add(goetia$power()).scale(isInWater()?getLiquidInertia():getInertia()):ordinary;}
 @Inject(method="addAdditionalSaveData",at=@At("TAIL"))
 private void goetia$savePower(CompoundTag d,CallbackInfo ci){if(goetia$hasFlight())d.put("goetia_flight",entityData.get(GOETIA_POWER).copy());}
 @Inject(method="readAdditionalSaveData",at=@At("TAIL"))
 private void goetia$loadPower(CompoundTag d,CallbackInfo ci){if(d.contains("goetia_flight")){entityData.set(GOETIA_POWER,d.getCompound("goetia_flight").copy());((teamroots.goetia.LegacyProjectileOwner)this).goetia$clearOwner();}}
 @Inject(method="onDeflection",at=@At("TAIL"))
 private void goetia$deflected(net.minecraft.world.entity.Entity attacker,boolean redirected,CallbackInfo ci){if(goetia$hasFlight()&&attacker instanceof LivingEntity){var look=attacker.getLookAngle();setDeltaMovement(look);goetia$flight(look.scale(.1));setOwner(attacker);}}
}
