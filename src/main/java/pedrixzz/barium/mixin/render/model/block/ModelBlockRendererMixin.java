package pedrixzz.barium.mixin.render;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {

  @Shadow
  private List<Box> boxes;

  /**
   * Otimiza o método `renderModel` para evitar a recálculo do tamanho da lista `boxes` a cada iteração do loop.
   */
  public void renderModel(Model model, float partialTicks) {

    int size = boxes.size();

    for (int i = 0; i < size; i++) {
      renderBox(boxes.get(i), partialTicks);
    }

  }

}
