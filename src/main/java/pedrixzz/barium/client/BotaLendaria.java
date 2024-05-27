package pedrixzz.barium.client;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BotaLendaria extends ArmorItem {

    public BotaLendaria(ArmorMaterial material, EntityEquipmentSlot[] slot, Settings settings) {
        super(material, slot[0], settings);
    }

    @Override
    public void onEquipEvent(ArmorEquipEvent event) {
        PlayerEntity player = event.getEntity();
        ItemStack stack = event.getItemStack();

        // Check if the equipped item is the Legendary Boot
        if (stack.getItem().equals(this) && stack.getName().getString().equals("Legendary Boot")) {
            // Apply speed effect when equipped
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1)); // Adjust duration and amplifier as needed
        } else {
            // Remove speed effect if another boot is equipped
            player.removeStatusEffect(StatusEffects.SPEED);
        }
    }
}
