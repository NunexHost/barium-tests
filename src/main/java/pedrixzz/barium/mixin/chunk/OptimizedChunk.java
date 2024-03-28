package pedrixzz.barium.mixin.chunk;

import java.util.Arrays;
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World;

class OptimizedChunk {

    private final int x;
    private final int z;
    private int[][][] sections = new int[16][][];
    private int[] biomes = new int[16 * 16];
    private final boolean generate;
    private final World world;
    private String status = "unloaded";
    private long lastUpdate = 0;

    public OptimizedChunk(World world, int x, int z, boolean generate) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.sections = Arrays.stream(sections)
            .map(row -> Arrays.stream(row)
             //   .map(col -> Arrays.fill(new int[256], 0))
                .toArray(int[][][]::new)
            )
            .toArray(int[][][]::new);
        this.generate = generate;
    }

    public void setBiome(int x, int z, int biome) {
        biomes[x + 16 * z] = biome;
    }

    public void setBlockState(int x, int y, int z, int blockState) {
        int sectionIndex = y / 16;
        int blockIndex = (y % 16) * 16 * 16 + (z * 16) + x;
        sections[sectionIndex][x][z] = blockState;
    }

    public int getBlockState(int x, int y, int z) {
        int sectionIndex = y / 16;
        int blockIndex = (y % 16) * 16 * 16 + (z * 16) + x;
        return sections[sectionIndex][x][z];
    }

    public void load() {
        if (!isUnloaded() || generate) {
            return;
        }
        this.status = "loaded";
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.setBiome(x, z, world.getBiome(x + this.x * 16, z + this.z * 16));
            }
        }
    }

    public void unload() {
        if (this.status.equals("unloaded")) {
            return;
        }
        this.status = "unloaded";
        this.sections = new int[16][][];
        this.biomes = new int[16 * 16];
    }

    public boolean isLoaded() {
        return this.status.equals("loaded");
    }

    public boolean isUnloaded() {
        return this.status.equals("unloaded");
    }
}
