package teamroots.goetia;
import net.minecraft.world.phys.Vec3;
public interface LegacyFireballFlight {
 void goetia$definePower(net.minecraft.network.syncher.SynchedEntityData.Builder builder);
 void goetia$flight(Vec3 power);
 boolean goetia$hasFlight();
 Vec3 goetia$power();
}
