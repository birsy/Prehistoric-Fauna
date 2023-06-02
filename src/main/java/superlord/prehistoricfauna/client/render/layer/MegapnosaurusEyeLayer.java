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
import superlord.prehistoricfauna.client.model.jurassic.kayenta.MegapnosaurusModel;
import superlord.prehistoricfauna.common.entity.jurassic.kayenta.Megapnosaurus;

public class MegapnosaurusEyeLayer extends RenderLayer<Megapnosaurus, MegapnosaurusModel> {
	
	private static final RenderType TEXTURE = RenderType.eyes(new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/megapnosaurus/megapnosaurus_eyeglow.png"));
	private final RenderLayerParent<Megapnosaurus, MegapnosaurusModel> megapnosaurusRenderer;
	
	public MegapnosaurusEyeLayer(RenderLayerParent<Megapnosaurus, MegapnosaurusModel> rendererIn) {
		super(rendererIn);
		this.megapnosaurusRenderer = rendererIn;
	}
	
	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Megapnosaurus megapnosaurus, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!(megapnosaurusRenderer.getModel() instanceof MegapnosaurusModel)) {
			return;
		}
		long roundTime = megapnosaurus.level.getDayTime() % 24000;
		boolean night = roundTime >= 13000 && roundTime <= 22000;
		BlockPos megapnosaurusPos = megapnosaurus.blockPosition();
		int i = megapnosaurus.level.getBrightness(LightLayer.SKY, megapnosaurusPos);
		int j = megapnosaurus.level.getBrightness(LightLayer.BLOCK, megapnosaurusPos);
		int brightness;
		if (night) {
			brightness = j;
		} else {
			brightness = Math.max(i, j);
		}
		if (brightness < 7) {
			RenderType tex = null;
			if (megapnosaurus.isAsleep() || megapnosaurus.tickCount % 50 >= 0 && megapnosaurus.tickCount % 50 <= 5) {
				tex = null;
			} else tex = TEXTURE;
	        if(tex != null){
	        	VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	        }
		}
	}
	
}
