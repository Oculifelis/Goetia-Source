package teamroots.goetia.hiddentest.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class NoGrabMixin {
    private static boolean announced;
    @Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
    private void goetia$noGrab(CallbackInfo ci) {
        if (!announced) {
            announced = true;
            org.slf4j.LoggerFactory.getLogger("goetia.hiddentest").info("GOETIA_HIDDEN_GUARD_ACTIVE mouseGrab=cancelled");
        }
        ci.cancel();
    }
}
