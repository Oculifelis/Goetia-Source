package teamroots.goetia;
import java.util.UUID;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
public final class GoetiaBloodProjectile extends Entity {
 private int lifetime;private UUID owner;private LivingEntity target;
 public GoetiaBloodProjectile(EntityType<? extends GoetiaBloodProjectile> t,Level w){super(t,w);setInvisible(true);}
 @Override protected void defineSynchedData(SynchedEntityData.Builder b){}
 @Override protected void readAdditionalSaveData(CompoundTag d){discard();}
 @Override protected void addAdditionalSaveData(CompoundTag d){}
 @Override public boolean isPickable(){return true;}
 @Override public void push(Entity e){if(e instanceof LivingEntity l&&!e.getUUID().equals(owner)){l.hurt(damageSources().generic(),3);l.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,1));l.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,400,1));}}
 @Override public void tick(){if(++lifetime>100||horizontalCollision||verticalCollision)discard();if(target==null)discard();else setDeltaMovement(target.position().subtract(position()).normalize().scale(.05f));for(int i=0;i<2;i++)level().addParticle(new DustParticleOptions(new org.joml.Vector3f(1,0,0),1),getX()+.1*(random.nextFloat()-.5),getY()+.1*(random.nextFloat()-.5),getZ()+.1*(random.nextFloat()-.5),0,0,0);}
 public void initCustom(Player caster,double x,double y,double z){setPos(x,y,z);var targets=level().getEntitiesOfClass(LivingEntity.class,new net.minecraft.world.phys.AABB(x-13,y-13,z-13,x+13,y+13,z+13),v->!v.getUUID().equals(caster.getUUID()));if(targets.isEmpty())discard();else target=targets.get(random.nextInt(targets.size()));owner=caster.getUUID();}
}
