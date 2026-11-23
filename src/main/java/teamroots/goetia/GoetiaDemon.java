package teamroots.goetia;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;
public final class GoetiaDemon extends Monster {
 private static final EntityDataAccessor<Boolean> TRAPPED=SynchedEntityData.defineId(GoetiaDemon.class,EntityDataSerializers.BOOLEAN);
 public final int kind;private boolean holyBurn;
 public GoetiaDemon(EntityType<? extends GoetiaDemon> t,Level w,int kind){super(t,w);this.kind=kind;xpReward=new int[]{5,10,20}[kind];}
 public static AttributeSupplier.Builder attributes(int k){return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE,24).add(Attributes.MOVEMENT_SPEED,new double[]{.72,.55,.5}[k]).add(Attributes.ATTACK_DAMAGE,k==0?3:6).add(Attributes.MAX_HEALTH,new double[]{28,52,92}[k]);}
 @Override protected void defineSynchedData(SynchedEntityData.Builder b){super.defineSynchedData(b);b.define(TRAPPED,false);}
 @Override protected void registerGoals(){goalSelector.addGoal(0,new FloatGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,.46,true));goalSelector.addGoal(5,new MoveTowardsRestrictionGoal(this,.46));goalSelector.addGoal(7,new RandomStrollGoal(this,.46));goalSelector.addGoal(8,new LookAtPlayerGoal(this,Player.class,8));goalSelector.addGoal(8,new RandomLookAroundGoal(this));targetSelector.addGoal(2,new NearestAttackableTargetGoal<>(this,Player.class,true));}
 public boolean trapped(){return entityData.get(TRAPPED);}public void trap(){entityData.set(TRAPPED,true);}
 public void holyWater(){holyBurn=true;igniteForSeconds(3);}
 @Override public boolean fireImmune(){return !holyBurn;}
 @Override public void tick(){super.tick();if(!isOnFire())holyBurn=false;if(level().isClientSide()){for(int i=0;i<(kind==0?1:4);i++)level().addParticle(ParticleTypes.SMOKE,getRandomX(.5),getRandomY(),getRandomZ(.5),0,.01,0);}else if(kind>0&&tickCount%80==0&&random.nextBoolean()&&getTarget()!=null&&!trapped()&&isAlive()){
  var target=getTarget();var aim=GoetiaFireballs.normalize(target.position().subtract(position())).scale(.01);var look=getLookAngle();if(kind==1){var shot=new SmallFireball(level(),this,aim);shot.setOwner(null);GoetiaFireballs.start(shot,aim);shot.setPos(getX()+look.x,getY()+1.8+look.y,getZ()+look.z);level().addFreshEntity(shot);}else{var shot=new LargeFireball(level(),this,aim,1);shot.setOwner(null);GoetiaFireballs.start(shot,aim);shot.setPos(getX()+look.x*2,getY()+1.8+look.y*2,getZ()+look.z*2);level().addFreshEntity(shot);}}
 }
 @Override protected void dropCustomDeathLoot(net.minecraft.server.level.ServerLevel level,DamageSource source,boolean hit){super.dropCustomDeathLoot(level,source,hit);int looting=source.getEntity() instanceof net.minecraft.world.entity.LivingEntity killer?net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentLevel(level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getOrThrow(net.minecraft.world.item.enchantment.Enchantments.LOOTING),killer):0;
  if(kind==0){rollDrop(level,net.minecraft.world.item.Items.BONE,1+looting,2);rollDrop(level,GoetiaContent.item("imp_tallow"),1+looting,3);}else if(kind==1){rollDrop(level,net.minecraft.world.item.Items.BONE,2+looting,2);rollDrop(level,GoetiaContent.item("demon_hide"),2+looting,3);}else{spawnAtLocation(level,new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.BONE),.5f);rollDrop(level,net.minecraft.world.item.Items.BONE,3+looting,2);spawnAtLocation(level,new net.minecraft.world.item.ItemStack(GoetiaContent.item("demon_hide")),.5f);rollDrop(level,GoetiaContent.item("demon_hide"),3+looting,2);rollDrop(level,net.minecraft.world.item.Items.IRON_INGOT,3+looting,2);rollDrop(level,GoetiaContent.item("demon_horn"),1,2);}
 }
 private void rollDrop(net.minecraft.server.level.ServerLevel level,net.minecraft.world.item.Item item,int trials,int bound){for(int i=0;i<trials;i++)if(random.nextInt(bound)==0)spawnAtLocation(level,new net.minecraft.world.item.ItemStack(item),.5f);}
 @Override public boolean hurtServer(net.minecraft.server.level.ServerLevel server,DamageSource source,float amount){float old=getHealth();super.hurtServer(server,source,amount);if(!level().isClientSide()&&old>0&&getHealth()<=0&&source.getEntity() instanceof Player p)GoetiaPlayer.add(p,"impurity",random.nextInt(new int[]{3,4,6}[kind])+new int[]{3,5,11}[kind],false);return true;}
 @Override public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput d){super.addAdditionalSaveData(d);d.putBoolean("trapped",trapped());}
 @Override public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput d){super.readAdditionalSaveData(d);entityData.set(TRAPPED,d.getBooleanOr("trapped",false));}
}
