package teamroots.goetia.mixin;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamroots.goetia.GoetiaConfig;
import teamroots.goetia.LegacyPlayerState;
@Mixin(Player.class)
public abstract class PlayerStateMixin implements LegacyPlayerState {
 @Unique private final int goetia$alignmentThreshold=GoetiaConfig.THRESHOLD.get();
 @Override public int goetia$threshold(){return goetia$alignmentThreshold;}
}
