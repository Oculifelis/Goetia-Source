package teamroots.goetia.mixin;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.GoetiaFluids;
@Mixin(Entity.class)
public abstract class HolyWaterQueriesMixin {
 @Inject(method="getFluidHeight(Lnet/minecraft/tags/TagKey;)D",at=@At("RETURN"),cancellable=true)
 private void goetia$height(TagKey<Fluid> tag,CallbackInfoReturnable<Double> cir){if(tag==FluidTags.WATER&&GoetiaFluids.TYPE.isBound())cir.setReturnValue(Math.max(cir.getReturnValue(),((Entity)(Object)this).getFluidHeight(GoetiaFluids.TYPE.get())));}
 @Inject(method="isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z",at=@At("RETURN"),cancellable=true)
 private void goetia$eyes(TagKey<Fluid> tag,CallbackInfoReturnable<Boolean> cir){if(tag==FluidTags.WATER&&GoetiaFluids.TYPE.isBound()&&((Entity)(Object)this).isEyeInFluid(GoetiaFluids.TYPE.get()))cir.setReturnValue(true);}
 @Inject(method="updateFluidInteraction()Z",at=@At("RETURN"))
 private void goetia$releasedExtinguish(CallbackInfoReturnable<Boolean> cir){if(GoetiaFluids.TYPE.isBound()){var entity=(Entity)(Object)this;if(entity.getFluidHeight(GoetiaFluids.TYPE.get())>0)entity.clearFire();}}
}
