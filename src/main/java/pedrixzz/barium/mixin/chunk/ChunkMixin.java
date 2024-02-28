package pedrixzz.barium.mixin.chunk;

import net.minecraft.block.Block;
import net.minecraft.chunk.Chunk;
import net.minecraft.network.packet.s2c.play.ChunkData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Modify;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.annotation.Nonnull;

@Mixin(Chunk.class)
public class ChunkMixin {

    // Cache de dados de chunk
    private ChunkData chunkData;

    // Pool de objetos para chunks
    private static ObjectPool<Chunk> chunkPool = new ObjectPool<>();

    @Inject(at = "ctor")
    public void init(World world, int chunkX, int chunkZ, boolean shouldCreate) {
        this.chunkData = new ChunkData(world, chunkX, chunkZ);
        this.chunkPool.put(this);
    }

    // Método otimizado para obter um bloco
    @Getter
    public Block getBlock(int x, int y, int z) {
        return this.chunkData.getBlock(x, y, z);
    }

    // Método otimizado para definir um bloco
    public void setBlock(int x, int y, int z, Block block) {
        this.chunkData.setBlock(x, y, z, block);
    }

}
