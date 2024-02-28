package pedrixzz.barium.mixin.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Modify;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkCache;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(Chunk.class)
public class ChunkMixin {

    @Inject
    private ChunkCache chunkCache;

    @Modify
    private void modifyTick(int tick) {
        // Armazenar em cache dados frequentemente acessados.
        chunkCache.cacheChunkData(chunk);
    }

    @Modify
    private List<Entity> modifyEntities(List<Entity> entities) {
        // Otimizar a iteração sobre entidades.
        return entities.stream().filter(entity -> entity.isAlive()).collect(Collectors.toList());
    }

}
