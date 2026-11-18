package teamroots.goetia.mixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import teamroots.goetia.*;
@Mixin(AbstractHurtingProjectile.class)
public abstract class FireballFlightMixin extends Projectile implements LegacyFireballFlight {
 @Shadow protected abstract float getInertia();
 @Shadow protected abstract float getLiquidInertia();
 protected FireballFlightMixin(EntityType<? extends Projectile> type,Level level){super(type,level);}
 @Override public void goetia$flight(Vec3 power){var d=new CompoundTag();d.putDouble("x",power.x);d.putDouble("y",power.y);d.putDouble("z",power.z);setData(GoetiaFireballData.POWER,d);}
 @Override public boolean goetia$hasFlight(){var d=getExistingDataOrNull(GoetiaFireballData.POWER);return d!=null&&!d.isEmpty();}
 @Override public boolean goetia$invulnerable(DamageSource source){return isInvulnerableToBase(source);}
 @Override public Vec3 goetia$power(){var d=getExistingDataOrNull(GoetiaFireballData.POWER);if(d==null)return Vec3.ZERO;return new Vec3(d.getDoubleOr("x",0),d.getDoubleOr("y",0),d.getDoubleOr("z",0));}
 // Marked shots retain the released pre-movement fluid update, displacement, then acceleration.
 @Shadow protected abstract net.minecraft.world.level.ClipContext.Block getClipType();
 @Shadow protected abstract boolean shouldBurn();
 @Shadow protected abstract net.minecraft.core.particles.ParticleOptions getTrailParticle();
 @Inject(method="tick",at=@At("HEAD"),cancellable=true)
 private void goetia$releasedFlight(CallbackInfo ci){if(!goetia$hasFlight())return;var owner=getOwner();if(level().isClientSide()||(owner==null||!owner.isRemoved())&&level().hasChunkAt(blockPosition())){
  super.tick();
  if(shouldBurn())igniteForSeconds(1);
  var hit=net.minecraft.world.entity.projectile.ProjectileUtil.getHitResultOnMoveVector(this,this::canHitEntity,getClipType());
  if(hit.getType()!=net.minecraft.world.phys.HitResult.Type.MISS&&!net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this,hit))onHit(hit);
  applyEffectsFromBlocks();
  var motion=getDeltaMovement();double x=getX()+motion.x,y=getY()+motion.y,z=getZ()+motion.z;
  net.minecraft.world.entity.projectile.ProjectileUtil.rotateTowardsMovement(this,.2F);
  float inertia;
  if(isInWater()){for(int i=0;i<4;i++)level().addParticle(net.minecraft.core.particles.ParticleTypes.BUBBLE,x-motion.x*.25,y-motion.y*.25,z-motion.z*.25,motion.x,motion.y,motion.z);inertia=getLiquidInertia();}else inertia=getInertia();
  setDeltaMovement(motion.add(goetia$power()).scale((double)inertia));
  var trail=getTrailParticle();if(trail!=null)level().addParticle(trail,x,y+.5,z,0,0,0);
  setPos(x,y,z);
 }else discard();ci.cancel();}
 @Inject(method="addAdditionalSaveData",at=@At("TAIL"))
 private void goetia$savePower(ValueOutput output,CallbackInfo ci){if(goetia$hasFlight())output.store("goetia_flight",CompoundTag.CODEC,getExistingDataOrNull(GoetiaFireballData.POWER).copy());}
 @Inject(method="readAdditionalSaveData",at=@At("TAIL"))
 private void goetia$loadPower(ValueInput input,CallbackInfo ci){input.read("goetia_flight",CompoundTag.CODEC).ifPresent(d->{setData(GoetiaFireballData.POWER,d.copy());((LegacyProjectileOwner)this).goetia$clearOwner();});}
 @Inject(method="onDeflection",at=@At("TAIL"))
 private void goetia$deflected(boolean byAttack,CallbackInfo ci){var attacker=((LegacyProjectileOwner)this).goetia$deflector();if(goetia$hasFlight()&&attacker instanceof LivingEntity){var look=attacker.getLookAngle();setDeltaMovement(look);goetia$flight(look.scale(.1));setOwner(attacker);}}
}
