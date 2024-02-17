package pedrixzz.barium.mixin.chunk;

import net.minecraft.core.Direction;
import net.minecraft.util.BitStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Direction.class)
public class DirectionMixin {

    @Shadow @Final private static Direction[] BY_3D_DATA;
    @Shadow @Final private int oppositeIndex;

    // Armazena os índices das direções opostas em um BitStorage para acesso rápido.
    private static final BitStorage OPPOSITE_DIRECTIONS = new BitStorage(26);

    static {
        for (Direction direction : BY_3D_DATA) {
            OPPOSITE_DIRECTIONS.set(direction.ordinal(), direction.getOpposite().ordinal());
        }
    }

    /**
     * Obtém a direção oposta usando um BitStorage para acesso rápido.
     * @author vulkanmod
     * @reason Substitui o acesso direto ao array BY_3D_DATA por um BitStorage,
     * que é mais eficiente para consultas frequentes.
     */
    @Overwrite
    public Direction getOpposite() {
        return BY_3D_DATA[OPPOSITE_DIRECTIONS.get(this.ordinal())];
    }
}

