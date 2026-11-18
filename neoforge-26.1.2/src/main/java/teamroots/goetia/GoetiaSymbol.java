package teamroots.goetia;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
public final class GoetiaSymbol extends Mob {
 private static final EntityDataAccessor<Boolean> ACTIVE=SynchedEntityData.defineId(GoetiaSymbol.class,EntityDataSerializers.BOOLEAN),TRACKING=SynchedEntityData.defineId(GoetiaSymbol.class,EntityDataSerializers.BOOLEAN),FORCE=SynchedEntityData.defineId(GoetiaSymbol.class,EntityDataSerializers.BOOLEAN);
 private static final EntityDataAccessor<Float> READY=SynchedEntityData.defineId(GoetiaSymbol.class,EntityDataSerializers.FLOAT),FADE=SynchedEntityData.defineId(GoetiaSymbol.class,EntityDataSerializers.FLOAT);
 public static final int[] COSTS={5,9,13,3,11,23};
 public final int kind;public float angle;private GoetiaDemon tracked;
 public GoetiaSymbol(EntityType<? extends GoetiaSymbol> type,net.minecraft.world.level.Level level,int kind){super(type,level);this.kind=kind;}
 public static AttributeSupplier.Builder attributes(){return createMobAttributes().add(Attributes.MAX_HEALTH,1);}
 @Override protected void defineSynchedData(SynchedEntityData.Builder b){super.defineSynchedData(b);b.define(ACTIVE,false);b.define(TRACKING,false);b.define(FORCE,false);b.define(READY,0f);b.define(FADE,1f);}
 @Override protected void checkFallDamage(double dy,boolean ground,net.minecraft.world.level.block.state.BlockState state,net.minecraft.core.BlockPos pos){}
 @Override public boolean onClimbable(){return false;}
 @Override public void travel(net.minecraft.world.phys.Vec3 input){if(isInWater()||isInLava()){travelFlying(input,.02F);return;}var ground=getBlockPosBelowThatAffectsMyMovement();float friction=.91F;if(onGround())friction=level().getBlockState(ground).getFriction(level(),ground,this)*.91F;float factor=.16277137F/(friction*friction*friction);friction=.91F;if(onGround())friction=level().getBlockState(ground).getFriction(level(),ground,this)*.91F;moveRelative(onGround()?.1F*factor:.02F,input);move(net.minecraft.world.entity.MoverType.SELF,getDeltaMovement());setDeltaMovement(getDeltaMovement().scale((double)friction));}
 @Override public boolean isPushable(){return false;}@Override public void push(Entity e){}@Override protected void pushEntities(){}
 public boolean active(){return entityData.get(ACTIVE);}public float ready(){return entityData.get(READY);}public float fade(){return entityData.get(FADE);}public boolean tracking(){return entityData.get(TRACKING);}
 public float renderSpin(){return kind==3?(tracking()?30:5):kind==5?(entityData.get(FORCE)?30:5):30;}
 public boolean activate(Player p){if(active()||p.experienceLevel<=COSTS[kind]||level().isClientSide())return false;p.giveExperienceLevels(-COSTS[kind]);entityData.set(ACTIVE,true);if(kind<3)GoetiaPlayer.set(p,"impurity",GoetiaPlayer.data(p).getIntOr("impurity",0)+random.nextInt(new int[]{3,4,6}[kind])+new int[]{3,5,11}[kind]);return true;}
 @Override protected InteractionResult mobInteract(Player p,InteractionHand hand){if(level().isClientSide())return InteractionResult.SUCCESS;if(hand!=InteractionHand.MAIN_HAND)return InteractionResult.PASS;if(!active())activate(p);else if(kind==3&&tracked!=null){tracked.discard();tracked=null;burst();entityData.set(TRACKING,true);entityData.set(FORCE,true);}else if(kind==5){int value=0;for(var d:demons(wideBox())){value+=demonValue(d);p.setHealth(p.getHealth()-new float[]{1,2,4}[d.kind]);d.discard();}GoetiaPlayer.set(p,"impurity",GoetiaPlayer.data(p).getIntOr("impurity",0)+value);burst();entityData.set(FORCE,true);}return InteractionResult.SUCCESS;}
 public AABB wideBox(){return new AABB(getX()-20.5,getY(),getZ()-20.5,getX()+20.5,getY()+41,getZ()+20.5);}
 private List<GoetiaDemon> demons(AABB box){return level().getEntitiesOfClass(GoetiaDemon.class,box);}
 private int demonValue(GoetiaDemon d){return random.nextInt(new int[]{5,7,11}[d.kind])+new int[]{6,10,22}[d.kind];}
 @Override public void tick(){super.tick();if(getHealth()<1)discard();if(tickCount%2==0){var under=blockPosition().below();for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++){var p=under.offset(x,0,z);if(!level().getBlockState(p).isFaceSturdy(level(),p,Direction.UP))discard();}}
  if(active()&&ready()<1&&!(kind==5&&entityData.get(FORCE))){entityData.set(READY,ready()+.01f);if(ready()>=1)complete();angle+=(kind==3||kind==5?5:30)*ready();}
  if(active()&&ready()>=1){if(kind!=3&&kind!=5){fadeStep();angle+=30*fade()*ready();}else if(kind==3){if(!level().isClientSide())for(var d:demons(new AABB(getX()-1.5,getY(),getZ()-1.5,getX()+1.5,getY()+3,getZ()+1.5)))if(d.getHealth()<=d.getMaxHealth()*.3f){d.trap();tracked=d;entityData.set(TRACKING,true);break;}if(!tracking())angle+=5*fade()*ready();}}
  if(kind==3&&tracking()){if(tracked==null&&(!level().isClientSide()||entityData.get(FORCE)))fadeStep();if(entityData.get(FORCE))fadeStep();if(!level().isClientSide()&&tracked!=null&&tracked.getHealth()>0)tracked.setPos(getX(),tracked.getY(),getZ());angle+=30*fade()*ready();}
  if(kind==5){if(entityData.get(FORCE))fadeStep();if(fade()<1)angle+=30*ready();}
 }
 private void fadeStep(){entityData.set(FADE,fade()-.05f);if(fade()<=0)discard();}
 private void complete(){if(level().isClientSide())return;if(kind<3){var d=GoetiaEntities.DEMONS.get(kind).get().create(level(),net.minecraft.world.entity.EntitySpawnReason.MOB_SUMMONED);if(d!=null){d.setPos(getX(),getY()+.5,getZ());if(level() instanceof ServerLevel s)d.finalizeSpawn(s,s.getCurrentDifficultyAt(blockPosition()),EntitySpawnReason.MOB_SUMMONED,null);level().addFreshEntity(d);}burst();}else if(kind==4)forge();else if(level() instanceof ServerLevel s)s.sendParticles(ParticleTypes.FIREWORK,getX(),getY()+.5,getZ(),80,.5,.5,.5,0);}
 public boolean forge(){if(level().isClientSide())return false;var ds=demons(wideBox());var inputs=level().getEntitiesOfClass(ItemEntity.class,wideBox());if(inputs.isEmpty())return false;int value=0;for(var d:ds)value+=demonValue(d);var input=inputs.get(0);var recipe=teamroots.goetia.recipes.DemonForgeRecipeManager.recipes.get(input.getItem().getItem());if(recipe==null)return false;if(value<recipe.cost){if(level() instanceof ServerLevel s)s.sendParticles(ParticleTypes.SMOKE,getX(),getY()+.5,getZ(),30,.5,.5,.5,0);return false;}for(var d:ds)d.discard();input.discard();level().addFreshEntity(new ItemEntity(level(),getX(),getY()+1,getZ(),recipe.result.copy()));burst();return true;}
 public void burst(){if(level() instanceof ServerLevel s){s.sendParticles(ParticleTypes.FLAME,getX(),getY()+.5,getZ(),30,.5,.5,.5,0);s.sendParticles(ParticleTypes.LARGE_SMOKE,getX(),getY()+.5,getZ(),30,.5,.5,.5,0);s.sendParticles(ParticleTypes.SMOKE,getX(),getY()+.5,getZ(),80,.5,.5,.5,0);}}
 @Override public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput d){super.addAdditionalSaveData(d);d.putBoolean("activated",active());d.putFloat("ready",ready());}
 @Override public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput d){super.readAdditionalSaveData(d);entityData.set(ACTIVE,d.getBooleanOr("activated",false));entityData.set(READY,d.getFloatOr("ready",0F));}
}
