package superlord.prehistoricfauna.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import superlord.prehistoricfauna.PrehistoricFauna;
import superlord.prehistoricfauna.client.model.jurassic.morrison.AllosaurusModel;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Allosaurus;

public class AllosaurusEyeLayer extends RenderLayer<Allosaurus, AllosaurusModel> {
	
	private static final RenderType TEXTURE = RenderType.eyes(new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/allosaurus/allosaurus_eyeglow.png"));
	private final RenderLayerParent<Allosaurus, AllosaurusModel> allosaurusRenderer;
	
	public AllosaurusEyeLayer(RenderLayerParent<Allosaurus, AllosaurusModel> rendererIn) {
		super(rendererIn);
		this.allosaurusRenderer = rendererIn;
	}
	
	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Allosaurus allosaurus, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!(allosaurusRenderer.getModel() instanceof AllosaurusModel)) {
			return;
		}
		long roundTime = allosaurus.level.getDayTime() % 24000;
		boolean night = roundTime >= 13000 && roundTime <= 22000;
		BlockPos allosaurusPos = allosaurus.blockPosition();
		int i = allosaurus.level.getBrightness(LightLayer.SKY, allosaurusPos);
		int j = allosaurus.level.getBrightness(LightLayer.BLOCK, allosaurusPos);
		int brightness;
		if (night) {
			brightness = j;
		} else {
			brightness = Math.max(i, j);
		}
		if (brightness < 7) {
			RenderType tex = null;
			if (allosaurus.isAsleep() || allosaurus.tickCount % 50 >= 0 && allosaurus.tickCount % 50 <= 5) {
				tex = null;
			} else tex = TEXTURE;
	        if(tex != null){
	        	VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	        }
		}
	}
	
}
