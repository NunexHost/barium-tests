package pedrixzz.barium.mixin.features.chunk;

import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReferenceArray;

@Mixin(targets = "net/minecraft/client/world/ClientChunkManager$ClientChunkMap")
public class MixinClientChunkMap {
    @Mutable
    @Shadow
    @Final
    private WorldChunk[] chunks;

    @Mutable
    @Shadow
    @Final
    private int diameter;

    @Mutable
    @Shadow
    @Final
    private int radius;

    private int factor;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(ClientChunkManager outer, int loadDistance, CallbackInfo ci) {
        // Essa re-inicialização é um pouco cara em memória, mas só acontece quando o mundo é
        // trocado ou a distância de renderização é alterada;
        this.radius = loadDistance;

        // Faça o diâmetro uma potência de dois para que possamos explorar matemática bit-a-bit ao calcular índices
        this.diameter = MathHelper.smallestEncompassingPowerOfTwo(loadDistance * 2 + 1);

        // O factor é usado como uma máscara de bits para substituir o modulo em getIndex
        this.factor = this.diameter - 1;

        this.chunks = new WorldChunk[this.diameter * this.diameter];
    }

    /**
     * @reason Evita modulo caro
     * @author JellySquid
     */
    @Overwrite
    public int getIndex(int chunkX, int chunkZ) {
        return (chunkZ & this.factor) * this.diameter + (chunkX & this.factor);
    }
}
