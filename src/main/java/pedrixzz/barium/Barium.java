package pedrixzz.barium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.ArmorEquipEvent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BotaLendaria implements ModInitializer {

    private static final Identifier LEGENDARY_BOOT_ID = new Identifier("Barium", "Bota_Lendaria"); // Replace "examplemod" with your mod id

    @Override
    public void onInitialize() {
        // Register the Legendary Boot item
        Registry.ITEM.register(LEGENDARY_BOOT_ID, new BotaLendaria(ArmorMaterial.DIAMOND, new PlayerEntityEquipmentSlot[]{EntityEquipmentSlot.FEET}, Settings.group(ItemGroup.COMBAT)));
    }
}
