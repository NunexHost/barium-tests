package pedrixzz.barium.mixin.chunk;

import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(WorldChunk.class)
public abstract class ChunkMixin {

    @Inject
    private static void modifyTick(World world, Chunk chunk, BlockPos pos) {
        // Cache de variáveis usadas com frequência
      //  int[] states = chunk.getStates();
        int minX = pos.getX() - 8;
        int minY = pos.getY() - 8;
        int minZ = pos.getZ() - 8;
        int maxX = pos.getX() + 8;
        int maxY = pos.getY() + 8;
        int maxZ = pos.getZ() + 8;

        // Otimização do loop de tique
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    int index = (x << 11) | (y << 7) | z;
                   // BlockState state = states[index];
            }
        }
    }

}
