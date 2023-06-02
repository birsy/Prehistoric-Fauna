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
import superlord.prehistoricfauna.client.model.triassic.ischigualasto.ExaeretodonModel;
import superlord.prehistoricfauna.common.entity.triassic.ischigualasto.Exaeretodon;

public class ExaeretodonEyeLayer extends RenderLayer<Exaeretodon, ExaeretodonModel> {
	
	private static final RenderType TEXTURE = RenderType.eyes(new ResourceLocation(PrehistoricFauna.MOD_ID, "textures/entities/exaeretodon/exaeretodon_eyeglow.png"));
	private final RenderLayerParent<Exaeretodon, ExaeretodonModel> exaeretodonRenderer;
	
	public ExaeretodonEyeLayer(RenderLayerParent<Exaeretodon, ExaeretodonModel> rendererIn) {
		super(rendererIn);
		this.exaeretodonRenderer = rendererIn;
	}
	
	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Exaeretodon exaeretodon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!(exaeretodonRenderer.getModel() instanceof ExaeretodonModel)) {
			return;
		}
		long roundTime = exaeretodon.level.getDayTime() % 24000;
		boolean night = roundTime >= 13000 && roundTime <= 22000;
		BlockPos exaeretodonPos = exaeretodon.blockPosition();
		int i = exaeretodon.level.getBrightness(LightLayer.SKY, exaeretodonPos);
		int j = exaeretodon.level.getBrightness(LightLayer.BLOCK, exaeretodonPos);
		int brightness;
		if (night) {
			brightness = j;
		} else {
			brightness = Math.max(i, j);
		}
		if (brightness < 7) {
			RenderType tex = null;
			if (exaeretodon.isAsleep() || exaeretodon.tickCount % 50 >= 0 && exaeretodon.tickCount % 50 <= 5) {
				tex = null;
			} else tex = TEXTURE;
	        if(tex != null){
	        	VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	        }
		}
	}
	
}
