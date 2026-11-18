package teamroots.goetia;
import net.minecraft.world.phys.Vec3;
public interface LegacyFireballFlight {
 void goetia$flight(Vec3 power);
 boolean goetia$hasFlight();
 boolean goetia$invulnerable(net.minecraft.world.damagesource.DamageSource source);
 Vec3 goetia$power();
}
