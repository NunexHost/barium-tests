package pedrixzz.barium.mixin.particle;

import org.spongepowered.api.mixin.Mixin;
import org.spongepowered.api.mixin.injection.Redirect;
import org.spongepowered.api.mixin.injection.At;
import org.spongepowered.api.mixin.injection.Conditional;
import org.spongepowered.api.logger.Logger;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderParticle;
import net.minecraft.entity.particle.Particle;
import org.apache.logging.log4j.LogManager;

@Mixin(Particle.class)
public class ParticleMixin {

    @Inject
    private static final Logger LOGGER = LogManager.getLogger("MixinParticle");

    @Redirect(
        method = "render",
        at = @At(value = Target.RENDER, ordinal = 0),
        require = Conditional.ENABLED
    )
    private void renderOptimized(Particle particle, Tessellator tessellator, float partialTicks, boolean fancy) {
        try {
            // Optimize particle rendering using 1.20.5+ mappings
            if (particle.isVisible()) {
                ParticleRenderer.renderParticle(particle, tessellator, partialTicks, fancy);
            } else {
                LOGGER.debug("Skipping rendering of invisible particle: {}", particle);
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to optimize particle rendering", e);
            // Fallback to original rendering method
            particle.render(tessellator, partialTicks, fancy);
        }
    }
}
