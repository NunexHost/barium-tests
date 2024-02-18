package pedrixzz.barium.mixin.client.renderer;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

@Mixin(BakedModel.class)
public interface BakedModelMixin extends FabricBakedModel {

    /**
     * Override the fallback path to shade vanilla quads differently. Optimized version.
     */
    @Override
    default void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        VanillaModelEncoder.emitBlockQuadsOptimized((BakedModel) this, state, randomSupplier, context, ((AbstractBlockRenderContext) context).getVanillaModelEmitter());
    }
}
