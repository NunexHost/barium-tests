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

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(ClientPlayerEntity player, boolean tickEnd) {
        if (tickEnd) {
            for (Chunk chunk : this.getLoadedChunks()) {
                chunk.unload();
            }
        }
    }
}
