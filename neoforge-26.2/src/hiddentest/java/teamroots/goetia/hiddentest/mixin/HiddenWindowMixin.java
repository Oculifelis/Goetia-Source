package teamroots.goetia.hiddentest.mixin;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public abstract class HiddenWindowMixin {
    @Shadow public abstract long handle();
    @Inject(method = "<init>", at = @At("TAIL"))
    private void goetia$hideWindow(CallbackInfo ci) {
        GLFW.glfwSetWindowPos(handle(), -4000, -4000);
        GLFW.glfwHideWindow(handle());
        org.slf4j.LoggerFactory.getLogger("goetia.hiddentest").info("GOETIA_HIDDEN_WINDOW_ACTIVE visible=false");
    }
}
