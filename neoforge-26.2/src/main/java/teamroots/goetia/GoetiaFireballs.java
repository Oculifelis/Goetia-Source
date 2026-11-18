package teamroots.goetia;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.Vec3;
public final class GoetiaFireballs {
 public static Vec3 normalize(Vec3 direction){double length=(float)Math.sqrt(direction.lengthSqr());return length<1.0e-4?Vec3.ZERO:direction.scale(1/length);}
 public static void start(AbstractHurtingProjectile shot,Vec3 direction){((LegacyProjectileOwner)shot).goetia$clearOwner();((LegacyFireballFlight)shot).goetia$flight(normalize(direction).scale(.1));shot.setDeltaMovement(Vec3.ZERO);}
 public static boolean punch(AbstractHurtingProjectile shot,net.minecraft.world.damagesource.DamageSource source){if(((LegacyFireballFlight)shot).goetia$invulnerable(source))return false;shot.needsSync=true;shot.hurtMarked=true;var attacker=source.getEntity();if(attacker==null)return false;var look=attacker.getLookAngle();shot.setDeltaMovement(look);((LegacyFireballFlight)shot).goetia$flight(look.scale(.1));if(attacker instanceof net.minecraft.world.entity.LivingEntity)shot.setOwner(attacker);return true;}
 private GoetiaFireballs(){}
}
