package pedrixzz.barium.client;

import fabricmc.api.event.EventPriority;
import fabricmc.api.event.lifecycle.EquipEvent;
import fabricmc.api.item.Wearable;
import fabricmc.api.player.v1.Player;
import fabricmc.fabric.api.event.player.PlayerEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.effect.InstantEffectTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("Barium")
public class BotaLendaria {

    private static final Logger LOGGER = LogManager.getLogger(AetherArmorCondition.class);

    @Mod.EventBus
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onArmorEquip(EquipEvent.Feet event) {
        Player player = event.getEntity();
        ItemStack newStack = event.getNewStack();

        if (newStack.getItem() instanceof Wearable wearable && isLegendaryDiamondBoots(newStack)) {
            applySpeedEffect(player);
        }
    }

    private static boolean isLegendaryDiamondBoots(ItemStack stack) {
        // Case-insensitive name check for "Bota Lendária"
        String itemName = stack.getName().getString().toLowerCase();
        return itemName.contains("Bota Lendária") && stack.getItem().getRegistryHolder().getId().getPath().startsWith("minecraft:diamond_boots");
    }

    private static void applySpeedEffect(Player player) {
        player.addEffect(new MobEffectInstance(InstantEffectTypes.SPEED, 20 * 3, 3)); // Speed 4 for 3 seconds
    }
}
