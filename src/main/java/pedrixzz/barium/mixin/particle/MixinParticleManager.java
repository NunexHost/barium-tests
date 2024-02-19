package pedrixzz.barium.mixin.particle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.particle.ParticleManager;

import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {
	@Inject(method = "registerDefaultFactories()V", at = @At("RETURN"))
	private void onRegisterDefaultFactories(CallbackInfo info) {
		ParticleFactoryRegistryImpl.INSTANCE.initialize((ParticleManager) (Object) this);
	}

	// Otimização: cachear a instância do ParticleFactoryRegistry
	private final ParticleFactoryRegistryImpl particleFactoryRegistry = ParticleFactoryRegistryImpl.INSTANCE;

	@Inject(method = "addParticle", at = @At("HEAD"))
	private void onAddParticle(net.minecraft.client.particle.Particle particle, CallbackInfo info) {
		// Otimização: evitar a chamada virtual `ParticleManager.getParticleFactory`
		particleFactoryRegistry.getParticleFactory(particle.getType()).add(particle, (ParticleManager) (Object) this);
	}
}
