package pedrixzz.barium;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Barium implements ModInitializer {
    public static final String MOD_ID = "barium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
	}
}
public class LegendaryBoots implements ModInitializer {

    private static final Identifier Bota_Lendaria_ID = new Identifier("Barium", "Bota_Lendária"); // Replace "examplemod" with your mod id

    @Override
    public void onInitialize() {
        // Register the Legendary Boot item
        Registry.ITEM.register(Bota_Lendaria_ID, new LegendaryBootItem(ArmorMaterial.DIAMOND, new PlayerEntityEquipmentSlot[]{EntityEquipmentSlot.FEET}, Settings.group(ItemGroup.COMBAT)));
    }

    public static class LegendaryBootItem extends ArmorItem {

        public LegendaryBootItem(ArmorMaterial material, EntityEquipmentSlot[] slot, Settings settings) {
            super(material, slot[0], settings);
        }

        @Override
        public void onEquipEvent(ArmorEquipEvent event) {
            PlayerEntity player = event.getEntity();
            ItemStack stack = event.getItemStack();

            // Check if the equipped item is the Legendary Boot
            if (stack.getItem().equals(this) && stack.getName().getString().equals("Bota Lendária")) {
                // Apply speed effect when equipped
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1)); // Adjust duration and amplifier as needed
            } else {
                // Remove speed effect if another boot is equipped
                player.removeStatusEffect(StatusEffects.SPEED);
            }
        }
    }
}
