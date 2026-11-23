package teamroots.goetia.mixin;
@org.spongepowered.asm.mixin.Mixin(net.minecraft.world.entity.LivingEntity.class)
public abstract class LivingAttackMixin {
 @org.spongepowered.asm.mixin.injection.Inject(method="tick",at=@org.spongepowered.asm.mixin.injection.At("HEAD"))
 private void goetia$livingTick(org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci){if((Object)this instanceof net.minecraft.world.entity.player.Player p)teamroots.goetia.GoetiaEvents.livingTick(p);}
}
