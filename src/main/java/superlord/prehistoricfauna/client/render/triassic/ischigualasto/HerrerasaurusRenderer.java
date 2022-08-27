package superlord.prehistoricfauna.client.render.triassic.ischigualasto;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import superlord.prehistoricfauna.PrehistoricFauna;
import superlord.prehistoricfauna.client.model.triassic.ischigualasto.HerrerasaurusModel;
import superlord.prehistoricfauna.client.model.triassic.ischigualasto.HerrerasaurusSleepingModel;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.HerrerasaurusEntity;

public class HerrerasaurusRenderer extends MobRenderer<HerrerasaurusEntity, EntityModel<HerrerasaurusEntity>> {

	private static final ResourceLocation HERRERASAURUS = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/herrerasaurus.png");
	private static final ResourceLocation ALBINO = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/albino.png");
	private static final ResourceLocation MELANISTIC = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/melanistic.png");
	private static final ResourceLocation HERRERASAURUS_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/herrerasaurus_sleeping.png");
	private static final ResourceLocation ALBINO_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/albino_sleeping.png");
	private static final ResourceLocation MELANISTIC_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/herrerasaurus/melanistic_sleeping.png");
	private static final HerrerasaurusModel HERRERASAURUS_MODEL = new HerrerasaurusModel();
	private static final HerrerasaurusSleepingModel HERRERASAURUS_SLEEPING_MODEL = new HerrerasaurusSleepingModel();

	public HerrerasaurusRenderer() {
		super(Minecraft.getInstance().getRenderManager(), HERRERASAURUS_MODEL, 0.625F);
	}

	public void render(HerrerasaurusEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		if (entityIn.isAsleep()) {
			entityModel = HERRERASAURUS_SLEEPING_MODEL;
		} else {
			entityModel = HERRERASAURUS_MODEL;
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	protected void preRenderCallback(HerrerasaurusEntity herrerasaurus, MatrixStack matrixStackIn, float partialTickTime) {
		if (herrerasaurus.isChild()) {
			matrixStackIn.scale(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(HerrerasaurusEntity entity) {
		if (entity.isAlbino()) {
			if (entity.isAsleep()) {
				return ALBINO_SLEEPING;
			} else return ALBINO;
		} else if (entity.isMelanistic()) {
			if (entity.isAsleep()) {
				return MELANISTIC_SLEEPING;
			}else return MELANISTIC;
		} else {
			if (entity.isAsleep()) {
				return HERRERASAURUS_SLEEPING;
			} else return HERRERASAURUS;
		}
	}

}