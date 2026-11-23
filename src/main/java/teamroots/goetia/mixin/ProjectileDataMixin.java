package teamroots.goetia.mixin;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import teamroots.goetia.LegacyFireballFlight;
/** Fireball subclasses do not consistently call their superclass data initializer. */
@Mixin(Entity.class)
public abstract class ProjectileDataMixin {
 @Redirect(method="<init>",at=@At(value="INVOKE",target="Lnet/minecraft/network/syncher/SynchedEntityData$Builder;build()Lnet/minecraft/network/syncher/SynchedEntityData;"))
 private SynchedEntityData goetia$finishData(SynchedEntityData.Builder builder){if((Object)this instanceof LegacyFireballFlight shot)shot.goetia$definePower(builder);return builder.build();}
}
