package teamroots.goetia.mixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import teamroots.goetia.*;
@Mixin(AbstractHurtingProjectile.class)
public abstract class FireballFlightMixin extends net.minecraft.world.entity.projectile.Projectile implements LegacyFireballFlight {
 protected FireballFlightMixin(net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.Projectile> type,net.minecraft.world.level.Level level){super(type,level);}
 // Native fields and vanilla power NBT are authoritative; this tag mirrors them to tracking clients.
 @Unique private static final EntityDataAccessor<CompoundTag> GOETIA_SHOT=SynchedEntityData.defineId(AbstractHurtingProjectile.class,EntityDataSerializers.COMPOUND_TAG);
 @Shadow public double xPower;@Shadow public double yPower;@Shadow public double zPower;
 @Override public void goetia$definePower(){getEntityData().define(GOETIA_SHOT,new CompoundTag());}
 @Override public void goetia$flight(Vec3 power){xPower=power.x;yPower=power.y;zPower=power.z;var mirror=new CompoundTag();mirror.putDouble("x",xPower);mirror.putDouble("y",yPower);mirror.putDouble("z",zPower);getEntityData().set(GOETIA_SHOT,mirror);}
 @Override public boolean goetia$hasFlight(){return !getEntityData().get(GOETIA_SHOT).isEmpty();}
 @Override public Vec3 goetia$power(){return new Vec3(xPower,yPower,zPower);}
 @Override public void goetia$receivePower(net.minecraft.network.syncher.EntityDataAccessor<?> key){if(key!=GOETIA_SHOT)return;var mirror=getEntityData().get(GOETIA_SHOT);if(!mirror.isEmpty()){xPower=mirror.getDouble("x");yPower=mirror.getDouble("y");zPower=mirror.getDouble("z");}}
 @Inject(method="addAdditionalSaveData",at=@At("TAIL")) private void goetia$save(CompoundTag data,CallbackInfo ci){if(goetia$hasFlight())data.putBoolean("goetia_flight",true);}
 @Inject(method="readAdditionalSaveData",at=@At("TAIL")) private void goetia$load(CompoundTag data,CallbackInfo ci){if(data.getBoolean("goetia_flight")){goetia$flight(new Vec3(xPower,yPower,zPower));((LegacyProjectileOwner)this).goetia$clearOwner();}}
}
