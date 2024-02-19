package pedrixzz.barium.mixin.util;

import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Overwrite;

@Mixin(BlockBox.class)
public abstract class BlockBoxMixin {

    @Shadow private int minX;
    @Shadow private int minY;
    @Shadow private int minZ;
    @Shadow private int maxX;
    @Shadow private int maxY;
    @Shadow private int maxZ;

    private final int volume;
    private final int area;

    @Inject
    public BlockBoxMixin(BlockBox instance) {
        this.volume = calculateVolume(instance);
        this.area = calculateArea(instance);
    }

    @Overwrite
    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    @Overwrite
    public int getVolume() {
        return volume;
    }

    @Overwrite
    public int getArea() {
        return area;
    }

    private int calculateVolume(BlockBox instance) {
        return (instance.maxX - instance.minX + 1) * (instance.maxY - instance.minY + 1) * (instance.maxZ - instance.minZ + 1);
    }

    private int calculateArea(BlockBox instance) {
        return 2 * ((instance.maxX - instance.minX + 1) * (instance.maxY - instance.minY + 1) + (instance.maxX - instance.minX + 1) * (instance.maxZ - instance.minZ + 1) + (instance.maxY - instance.minY + 1) * (instance.maxZ - instance.minZ + 1));
    }
}
