package pedrixzz.barium.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.ColorResolver;

import net.fabricmc.fabric.impl.client.rendering.ColorResolverRegistryImpl;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    // Mapa otimizado para acesso rápido de ColorResolver para BiomeColorCache
    @Unique
    private final Reference2ReferenceMap<ColorResolver, BiomeColorCache> customColorCache = ColorResolverRegistryImpl.createCustomCacheMap(resolver -> {
        // Função lambda para inicializar o cache com base no resolvedor
        return new BiomeColorCache(pos -> calculateColor(pos, resolver));
    });

    @Shadow
    public abstract int calculateColor(BlockPos pos, ColorResolver colorResolver);

    @Inject(method = "resetChunkColor(Lnet/minecraft/util/math/ChunkPos;)V", at = @At("RETURN"))
    private void onResetChunkColor(ChunkPos chunkPos, CallbackInfo ci) {
        // Reinicializar caches de cores personalizadas para o chunk
        for (BiomeColorCache cache : customColorCache.values()) {
            cache.reset(chunkPos.x, chunkPos.z);
        }
    }

    @Inject(method = "reloadColor()V", at = @At("RETURN"))
    private void onReloadColor(CallbackInfo ci) {
        // Reinicializar todos os caches de cores personalizadas
        for (BiomeColorCache cache : customColorCache.values()) {
            cache.reset();
        }
    }

    @ModifyExpressionValue(method = "getColor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/ColorResolver;)I", at = @At(value = "INVOKE", target = "it/unimi/dsi/fastutil/objects/Object2ObjectArrayMap.get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object modifyNullCache(/* BiomeColorCache */ Object cache, BlockPos pos, ColorResolver resolver) {
        if (cache == null) {
            // Obter cache do mapa otimizado
            cache = customColorCache.get(resolver);

            if (cache == null) {
                throw new UnsupportedOperationException("ClientWorld.getColor called with unregistered ColorResolver " + resolver);
            }
        }

        return cache;
    }
}
