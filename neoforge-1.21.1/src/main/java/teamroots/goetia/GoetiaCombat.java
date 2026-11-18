package teamroots.goetia;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
/** The original attack callback precedes vanilla hurt-immunity checks. */
public final class GoetiaCombat {
 private static final java.util.Random RANDOM=new java.util.Random();
 public static boolean attackHead(LivingEntity victim,DamageSource source){if(victim.level().isClientSide){Goetia.attackVisual.accept(victim,source);return false;}
  Player attacker=source.is(DamageTypes.PLAYER_ATTACK)&&source.getEntity() instanceof Player p?p:null;
  if(attacker!=null){var d=GoetiaPlayer.data(attacker);if(d.contains("fallen_armor"))d.putInt("fallen_armor",d.getInt("fallen_armor")+60);if(d.contains("burning_touch"))victim.igniteForSeconds(5);if(d.contains("chained_strikes"))for(var target:attacker.level().getEntitiesOfClass(LivingEntity.class,new AABB(victim.getX()-2.5,victim.getY()-2,victim.getZ()-2.5,victim.getX()+2.5,victim.getY()+2,victim.getZ()+2.5)))if(!target.getUUID().equals(attacker.getUUID())&&!target.getUUID().equals(victim.getUUID())){target.hurt(attacker.damageSources().generic(),3);target.setLastHurtByMob(attacker);}}
  boolean cancelled=false;if(victim instanceof Player p){var d=GoetiaPlayer.data(p);if(d.contains("fallen_armor")&&RANDOM.nextInt(2)==0)cancelled=true;if(d.contains("rebuke")&&source.getEntity() instanceof LivingEntity other){var v=p.position().subtract(other.position()).normalize();other.knockback(.55,v.x,v.z);other.igniteForSeconds(3);}}
  if(!cancelled&&attacker!=null&&!(victim instanceof Player)){var d=GoetiaPlayer.data(attacker);if(d.contains("smite")){victim.hurt(attacker.damageSources().magic(),victim.getHealth());d.remove("smite");}}
  return cancelled;
 }
 public static float hurtAmount(LivingEntity victim,DamageSource source,float amount){if(source.is(DamageTypes.PLAYER_ATTACK)&&source.getEntity() instanceof Player p){var d=GoetiaPlayer.data(p);if(d.contains("voracious_strikes")&&p.getMainHandItem().isEmpty()){victim.invulnerableTime=0;amount=2;}if(d.contains("inner_fire_grace"))amount+=amount*.4f;}return amount;}
 private GoetiaCombat(){}
}
