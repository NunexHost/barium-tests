package pedrixzz.barium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.registry;

public class BotaLendaria implements ModInitializer {

    private static final Identifier LEGENDARY_BOOT_ID = new Identifier("Barium", "BotaLendaria"); // Replace "examplemod" with your mod id

    @Override
    public void onInitialize() {
        // Register the Legendary Boot item
        Registry.ITEM.register(LEGENDARY_BOOT_ID, new BotaLendaria(ArmorMaterial.DIAMOND, new PlayerEntityEquipmentSlot[]{EntityEquipmentSlot.FEET}, Settings.group(ItemGroup.COMBAT)));

        // Register server tick event listener for speed effect
        MinecraftForge.EVENT_BUS.addListener(ServerTickEvent.class, event -> {
            for (PlayerEntity player : event.getServerWorld().getPlayers()) {
                ItemStack boots = player.getInventory().getArmorStack(EntityEquipmentSlot.FEET);

                if (boots.getItem().equals(BotaLendaria.LEGENDARY_BOOTS) && boots.getName().getString().equals("Bota Lendária")) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
                } else {
                    player.removeStatusEffect(StatusEffects.SPEED);
                }
            }
        });
    }
    }
