package pedrixzz.barium.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_355;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(
   value = {class_355.class},
   priority = 10
)
public abstract class PlayerListMixin {
   @Redirect(
      method = {"render"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/MinecraftClient;isInSingleplayer()Z"
)
   )
   private boolean renderHeads(class_310 instance) {
      return true;
   }
}
