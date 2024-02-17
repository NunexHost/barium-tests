package pedrixzz.barium.mixin.world;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Shadow @Final private List<Entity> entities;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(ClientPlayerEntity player) {
        // **Otimização 1: Remoção de entidades inativas**
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof LivingEntity && !((LivingEntity) entity).isAlive()) {
                entities.remove(i);
                i--; // Decrementar para evitar pular entidades
            }
        }

        // **Otimização 2: Redução de rastreamento de entidades distantes**
        for (Entity entity : entities) {
            if (entity.squaredDistanceTo(player) > 256 * 256) {
                entity.setFlag(6, false); // Desativar rastreamento de movimentos
            } else {
                entity.setFlag(6, true); // Reativar rastreamento de movimentos
            }
        }

        // **Otimização 3: Limpeza de chunks não utilizados**
        for (Chunk chunk : world.getLoadedChunks()) {
            if (chunk.isEmpty() && !chunk.isPlayerLoaded(player)) {
                world.unloadChunk(chunk);
            }
        }
    }
}
