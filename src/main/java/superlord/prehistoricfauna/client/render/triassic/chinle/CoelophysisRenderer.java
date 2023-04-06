package superlord.prehistoricfauna.client.render.triassic.chinle;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import superlord.prehistoricfauna.PrehistoricFauna;
import superlord.prehistoricfauna.client.model.triassic.chinle.CoelophysisModel;
import superlord.prehistoricfauna.client.render.layer.CoelophysisEyeLayer;
import superlord.prehistoricfauna.common.entities.triassic.chinle.CoelophysisEntity;
import superlord.prehistoricfauna.config.PrehistoricFaunaConfig;

public class CoelophysisRenderer extends MobRenderer<CoelophysisEntity, EntityModel<CoelophysisEntity>> {

	private static final ResourceLocation COELOPHYSIS = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/coelophysis.png");
	private static final ResourceLocation ALBINO = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/albino.png");
	private static final ResourceLocation MELANISTIC = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/melanistic.png");
	private static final ResourceLocation JUVENILE = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/juvenile.png");
	private static final ResourceLocation COELOPHYSIS_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/coelophysis_sleeping.png");
	private static final ResourceLocation ALBINO_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/albino_sleeping.png");
	private static final ResourceLocation MELANISTIC_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/melanistic_sleeping.png");
	private static final ResourceLocation JUVENILE_SLEEPING = new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/coelophysis/juvenile_sleeping.png");
	private static final CoelophysisModel COELOPHYSIS_MODEL = new CoelophysisModel();

	public CoelophysisRenderer() {
		super(Minecraft.getInstance().getRenderManager(), COELOPHYSIS_MODEL, 0.75F);
		if (PrehistoricFaunaConfig.eyeShine) {
			this.addLayer(new CoelophysisEyeLayer(this));
		}
	}

	protected void preRenderCallback(CoelophysisEntity coelophysis, MatrixStack matrixStackIn, float partialTickTime) {
		if (coelophysis.isChild()) {
			matrixStackIn.scale(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(CoelophysisEntity entity) {
		if (entity.isAlbino()) {
			if (entity.isAsleep() || entity.ticksExisted % 50 >= 0 && entity.ticksExisted % 50 <= 5) {
				return ALBINO_SLEEPING;
			} else return ALBINO;
		} else if (entity.isMelanistic()) {
			if (entity.isAsleep() || entity.ticksExisted % 50 >= 0 && entity.ticksExisted % 50 <= 5) {
				return MELANISTIC_SLEEPING;
			}else return MELANISTIC;
		} else {
			if (entity.isChild()) {
				if (entity.isAsleep() || entity.ticksExisted % 50 >= 0 && entity.ticksExisted % 50 <= 5) {
					return JUVENILE_SLEEPING;
				} else return JUVENILE;
			} else {
				if (entity.isAsleep() || entity.ticksExisted % 50 >= 0 && entity.ticksExisted % 50 <= 5) {
					return COELOPHYSIS_SLEEPING;
				} else return COELOPHYSIS;
			}
		}
	}

}
