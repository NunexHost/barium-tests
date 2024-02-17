package pedrixzz.barium.mixin.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import java.util.Random;

@Mixin(Chunk.class)
public abstract class ChunkMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BlockState state, World world, BlockPos pos, Random rand) {
        // Otimização 1: Reduzir a frequência de atualizações de blocos
        if (rand.nextInt(10) != 0) {
            return;
        }
        
        // Otimização 2: Evitar recálculo de luz desnecessário
        if (!world.isAreaLoaded(pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8, pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8)) {
            return;
        }

        // Código original do método tick
    }

    @ModifyVariable(method = "getLightSubtracted", at = @At("HEAD"), ordinal = 0)
    private int modifyLightSubtracted(int lightSubtracted, BlockState state, BlockPos pos) {
        // Otimização 3: Armazenar o valor de luz subtraída para evitar recálculo
        if (lightSubtracted == -1) {
            lightSubtracted = state.getLightSubtracted(world, pos);
        }
        return lightSubtracted;
    }

}
