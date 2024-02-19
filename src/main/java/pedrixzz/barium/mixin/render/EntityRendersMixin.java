package pedrixzz.barium.mixin.render;

import java.util.Map;
import java.util.WeakHashMap;

import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.fabricmc.fabric.impl.client.rendering.RegistrationHelperImpl;

@Mixin(EntityRenderers.class)
public abstract class EntityRenderersMixin {
	@Shadow()
    	@Final
        	private static Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES;

            	private static final Map<EntityType<?>, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper> RENDERER_HELPERS = new WeakHashMap<>();

                	@SuppressWarnings({"unchecked", "rawtypes"})
                    	@Inject(method = "<clinit>*", at = @At(value = "RETURN"))
                        	private static void onRegisterRenderers(CallbackInfo info) {
                            		EntityRendererRegistryImpl.setup(((t, factory) -> RENDERER_FACTORIES.put(t, factory)));
                                    	}

                                        	// synthetic lambda in reloadEntityRenderers
                                            	@SuppressWarnings({"unchecked", "rawtypes"})
                                                	@Redirect(method = "method_32174", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRendererFactory;create(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)Lnet/minecraft/client/render/entity/EntityRenderer;"))
                                                    	private static EntityRenderer<?> createEntityRenderer(EntityRendererFactory<?> entityRendererFactory, EntityRendererFactory.Context context, ImmutableMap.Builder builder, EntityRendererFactory.Context context2, EntityType<?> entityType) {
                                                        		EntityRenderer<?> entityRenderer = entityRendererFactory.create(context);

                                                                		if (entityRenderer instanceof LivingEntityRenderer) {
                                                                        			LivingEntityRendererAccessor accessor = (LivingEntityRendererAccessor) entityRenderer;
                                                                                    			LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper helper = RENDERER_HELPERS.computeIfAbsent(entityType, (type) -> new RegistrationHelperImpl(accessor::callAddFeature));
                                                                                                			LivingEntityFeatureRendererRegistrationCallback.EVENT.invoker().registerRenderers((EntityType<? extends LivingEntity>) entityType, (LivingEntityRenderer) entityRenderer, helper, context);
                                                                                                            		}

                                                                                                                    		return entityRenderer;
                                                                                                                            	}

                                                                                                                                	// private static synthetic method_32175(Lcom/google/common/collect/ImmutableMap$Builder;Lnet/minecraft/class_5617$class_5618;Ljava/lang/String;Lnet/minecraft/class_5617;)V
                                                                                                                                    	@SuppressWarnings({"unchecked", "rawtypes"})
                                                                                                                                        	@Redirect(method = "method_32175", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRendererFactory;create(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)Lnet/minecraft/client/render/entity/EntityRenderer;"))
                                                                                                                                            	private static EntityRenderer<? extends PlayerEntity> createPlayerEntityRenderer(EntityRendererFactory playerEntityRendererFactory, EntityRendererFactory.Context context) {
                                                                                                                                                		EntityRenderer<? extends PlayerEntity> entityRenderer = playerEntityRendererFactory.create(context);

                                                                                                                                                        		LivingEntityRendererAccessor accessor = (LivingEntityRendererAccessor) entityRenderer;
                                                                                                                                                                		LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper helper = RENDERER_HELPERS.get(EntityType.PLAYER);
                                                                                                                                                                        		if (helper == null) {
                                                                                                                                                                                    			helper = new RegistrationHelperImpl(accessor::callAddFeature);
                                                                                                                                                                                                		RENDERER_HELPERS.put(EntityType.PLAYER, helper);
                                                                                                                                                                                                        		}
                                                                                                                                                                                                                		LivingEntityFeatureRendererRegistrationCallback.EVENT.invoker().registerRenderers(EntityType.PLAYER, (LivingEntityRenderer) entityRenderer, helper, context);

                                                                                                                                                                                                                        		return entityRenderer;
                                                                                                                                                                                                                                	}
                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                    // Interface auxiliar para evitar reflexão (acessar métodos privados)
                                                                                                                                                                                                                                    interface LivingEntityRendererAccessor {
                                                                                                                                                                                                                                        void callAddFeature(LivingEntityFeatureRenderer.FeatureRenderer<? extends LivingEntity> feature);
                                                                                                                                                                                                                                        }
                                                                                                                                                 
