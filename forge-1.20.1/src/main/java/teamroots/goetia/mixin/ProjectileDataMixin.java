package teamroots.goetia.mixin;
@org.spongepowered.asm.mixin.Mixin(net.minecraft.world.entity.Entity.class)
public abstract class ProjectileDataMixin {
 @org.spongepowered.asm.mixin.injection.Inject(method="<init>",at=@org.spongepowered.asm.mixin.injection.At("TAIL"))
 private void goetia$finishData(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci){if((Object)this instanceof teamroots.goetia.LegacyFireballFlight shot)shot.goetia$definePower();}
 @org.spongepowered.asm.mixin.injection.Inject(method="onSyncedDataUpdated(Lnet/minecraft/network/syncher/EntityDataAccessor;)V",at=@org.spongepowered.asm.mixin.injection.At("TAIL"))
 private void goetia$receivePower(net.minecraft.network.syncher.EntityDataAccessor<?> key,org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci){if((Object)this instanceof teamroots.goetia.LegacyFireballFlight shot)shot.goetia$receivePower(key);}
}
