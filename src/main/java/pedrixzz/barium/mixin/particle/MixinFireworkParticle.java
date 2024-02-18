package me.flashyreese.mods.sodiumextra.mixin.particle;

import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public class MixinFireworkParticle {
    @Unique
    private static final Identifier FIREWORK_IDENTIFIER = new Identifier("minecraft", "firework");

    @Inject(method = "addExplosionParticle", at = @At(value = "HEAD"), cancellable = true)
    public void addExplosionParticle(double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker, CallbackInfo ci) {
            ci.cancel();
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;setColor(FFF)V"))
    public void tick(Particle instance, float red, float green, float blue) {
        instance.setColor(red, green, blue);
    }
}
