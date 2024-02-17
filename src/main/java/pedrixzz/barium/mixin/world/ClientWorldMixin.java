package pedrixzz.barium.mixin.world;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.chunk.Chunk;
import java.util.List;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(ClientPlayerEntity player, boolean tickEnd) {
        if (tickEnd) {
            for (Chunk chunk : this.getLoadedChunks()) {
                chunk.unload();
            }
        }
    }
}
