package pedrixzz.barium.client;

import net.fabricmc.fabric.api.event.player.UseItemOnArmorEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ModLifecycle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPacketSender;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.effect.InstantEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.StatusEffect;

public class BotaLendariaMod {

    private static final Identifier SPEED_EFFECT_ID = new Identifier("bota_lendaria", "speed_effect");

    public static void register() {
        ModLifecycle.EVENT.onShutDown(BotaLendariaMod::unregister);
        UseItemOnArmorEvent.HOOK.register(BotaLendariaMod::onUseItemOnArmor);
    }

    private static void unregister() {
        UseItemOnArmorEvent.HOOK.unregister(BotaLendariaMod::onUseItemOnArmor);
    }

    private static void onUseItemOnArmor(UseItemOnArmorEvent event) {
        ItemStack stack = event.getStack();
        if (stack.hasCustomName() && stack.getCustomName().equals("Bota Lendária")) {
            PlayerEntity player = event.getPlayer();
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                ServerPacketSender playerConnection = serverPlayer.getServer().getPlayerConnectionHandler(serverPlayer);
                PacketByteBuf packetByteBuf = new PacketByteBuf(null);
                packetByteBuf.writeBoolean(true);
                playerConnection.sendPacket(BotaLendariaMod.SPEED_EFFECT_PACKET, packetByteBuf);
            }
        }
    }

    public static final PacketByteBuf SPEED_EFFECT_PACKET = new PacketByteBuf(null);

    static {
        SPEED_EFFECT_PACKET.writeBoolean(true);
    }

    public static void applySpeedEffect(PlayerEntity player) {
        StatusEffect speedEffect = Registry.STATUS_EFFECT.get(SPEED_EFFECT_ID);
        MobEffectInstance speedEffectInstance = new MobEffectInstance(speedEffect, 20 * 3, 4, false, false);
        player.addEffect(speedEffectInstance);
    }
                                      }
