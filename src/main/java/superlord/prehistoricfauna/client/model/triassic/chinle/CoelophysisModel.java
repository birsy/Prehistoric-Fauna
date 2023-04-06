package superlord.prehistoricfauna.client.model.triassic.chinle;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.prehistoricfauna.common.entities.triassic.chinle.CoelophysisEntity;

/**
 * Coelophysis - Obsolerus
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CoelophysisModel extends EntityModel<CoelophysisEntity> {
	public ModelRenderer Body;
	public ModelRenderer Tail1;
	public ModelRenderer LArm;
	public ModelRenderer RArm;
	public ModelRenderer Neck;
	public ModelRenderer RThigh;
	public ModelRenderer LThigh;
	public ModelRenderer Tail2;
	public ModelRenderer Head;
	public ModelRenderer RLeg;
	public ModelRenderer RFoot;
	public ModelRenderer LLeg;
	public ModelRenderer LFoot;

	public CoelophysisModel() {
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.LArm = new ModelRenderer(this, 82, 0);
		this.LArm.mirror = true;
		this.LArm.setRotationPoint(-1.0F, 4.0F, 3.0F);
		this.LArm.addBox(-1.0F, -1.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Body = new ModelRenderer(this, 0, 0);
		this.Body.setRotationPoint(0.0F, 11.0F, -8.0F);
		this.Body.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 14.0F, 0.0F, 0.0F, 0.0F);
		this.RFoot = new ModelRenderer(this, 0, 0);
		this.RFoot.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.RFoot.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 0, 31);
		this.Tail2.setRotationPoint(0.0F, -0.4F, 6.4F);
		this.Tail2.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 24.0F, 0.0F, 0.0F, 0.0F);
		this.RArm = new ModelRenderer(this, 82, 0);
		this.RArm.setRotationPoint(1.0F, 4.0F, 3.0F);
		this.RArm.addBox(0.0F, -1.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.RThigh = new ModelRenderer(this, 100, 0);
		this.RThigh.setRotationPoint(1.0F, 2.5F, 11.0F);
		this.RThigh.addBox(-0.5F, -1.5F, -2.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.LThigh = new ModelRenderer(this, 100, 0);
		this.LThigh.mirror = true;
		this.LThigh.setRotationPoint(-1.0F, 2.5F, 11.0F);
		this.LThigh.addBox(-1.5F, -1.5F, -2.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.Tail1 = new ModelRenderer(this, 55, 0);
		this.Tail1.setRotationPoint(0.0F, 1.6F, 14.0F);
		this.Tail1.addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.LLeg = new ModelRenderer(this, 6, 3);
		this.LLeg.mirror = true;
		this.LLeg.setRotationPoint(-0.5F, 4.5F, 2.0F);
		this.LLeg.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.Neck = new ModelRenderer(this, 34, 0);
		this.Neck.setRotationPoint(0.0F, 2.0F, 0.5F);
		this.Neck.addBox(-1.0F, -8.0F, -2.0F, 2.0F, 9.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.Head = new ModelRenderer(this, 41, 13);
		this.Head.setRotationPoint(0.0F, -6.0F, -2.0F);
		this.Head.addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.RLeg = new ModelRenderer(this, 6, 3);
		this.RLeg.setRotationPoint(0.5F, 4.5F, 2.0F);
		this.RLeg.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.LFoot = new ModelRenderer(this, 0, 0);
		this.LFoot.mirror = true;
		this.LFoot.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.LFoot.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.Body.addChild(this.LArm);
		this.RLeg.addChild(this.RFoot);
		this.Tail1.addChild(this.Tail2);
		this.Body.addChild(this.RArm);
		this.Body.addChild(this.RThigh);
		this.Body.addChild(this.LThigh);
		this.Body.addChild(this.Tail1);
		this.LThigh.addChild(this.LLeg);
		this.Body.addChild(this.Neck);
		this.Neck.addChild(this.Head);
		this.RThigh.addChild(this.RLeg);
		this.LLeg.addChild(this.LFoot);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
		ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(CoelophysisEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialTick = ageInTicks - entityIn.ticksExisted;
		float attackProgress =  entityIn.getMeleeProgress(partialTick);
		resetModel();
		if (entityIn.isAsleep()) {
			this.Neck.rotateAngleX = 0.6646214111173737F;
			this.Neck.rotateAngleY = -2.3848178792724077F;
			this.LLeg.rotationPointY = 4.2F;
			this.LLeg.rotationPointZ = 0.9F;
			this.LLeg.rotateAngleX = -0.3979350561389017F;
			this.LLeg.rotateAngleY = -0.0781907508222411F;
			this.LLeg.rotateAngleZ = -0.27366763203903305F;
			this.RThigh.rotateAngleX = -1.1728612040769677F;
			this.RThigh.rotateAngleY = -0.46914448828868976F;
			this.Tail1.rotateAngleX = -0.23457224414434488F;
			this.Tail1.rotateAngleY = 0.5864306020384839F;
			this.LArm.rotateAngleZ = 1.3292428222347474F;
			this.Tail2.rotateAngleX = 0.11728612207217244F;
			this.Tail2.rotateAngleY = 0.7037167490777915F;
			this.LFoot.rotateAngleX = 1.5707963267948966F;
			this.RArm.rotateAngleZ = -1.3264502315156905F;
			this.Body.rotationPointY = 19;
			this.RLeg.rotationPointY = 4.2F;
			this.RLeg.rotationPointZ = 0.9F;
			this.RLeg.rotateAngleX = -0.3979350561389017F;
			this.RLeg.rotateAngleY = 0.0781907508222411F;
			this.RLeg.rotateAngleZ = 0.27366763203903305F;
			this.LThigh.rotateAngleX = -1.1728612040769677F;
			this.LThigh.rotateAngleY = 0.46914448828868976F;
			this.RFoot.rotateAngleX = 1.5707963267948966F;
		} else {
			this.RThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.LThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.Tail1.rotateAngleY = -0.12F * MathHelper.sin(0.2F * ageInTicks / 5);
			this.Tail2.rotateAngleY = -0.12F * MathHelper.sin(0.2F * ageInTicks / 5);
			this.Tail1.rotateAngleX = -Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5));
			this.Tail2.rotateAngleX = -Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5));
			this.Neck.rotateAngleX = (headPitch * ((float)Math.PI / 180F)) + (Math.abs(-0.025F * MathHelper.sin(0.1F * ageInTicks / 3))) + attackProgress * (float) Math.toRadians(40F);
			this.LArm.rotateAngleZ = Math.abs(-0.05F * MathHelper.sin(0.15F * ageInTicks / 3));
			this.RArm.rotateAngleZ = -Math.abs(-0.05F * MathHelper.sin(0.15F * ageInTicks / 3));
			this.Neck.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
			this.Head.rotateAngleX = attackProgress * (float) Math.toRadians(-15F);
			if (entityIn.isInWater()) {
				this.Body.rotationPointY = 14;
				this.Body.rotateAngleX = -0.5F;
				this.Tail1.rotateAngleX = 0.25F;
				this.Tail2.rotateAngleX = 0.25F;
				this.Neck.rotateAngleX = 0.5F;
				this.LThigh.rotateAngleX = -0.5F * MathHelper.sin(0.2F * ageInTicks / 1.5F);
				this.RThigh.rotateAngleX = 0.5F * MathHelper.sin(0.2F * ageInTicks / 1.5F);
				this.LLeg.rotateAngleX = -0.3F * MathHelper.sin(0.2F * ageInTicks / 1.5F);
				this.RLeg.rotateAngleX = 0.3F * MathHelper.sin(0.2F * ageInTicks / 1.5F);
				this.RArm.rotateAngleX = 0.5F;
				this.LArm.rotateAngleX = 0.5F;
				this.Tail1.rotateAngleY = (MathHelper.cos(limbSwing * 2.6662F) * 1.4F * limbSwingAmount) + (0.0625F * MathHelper.sin(0.15F * ageInTicks / 1.5F));
				this.Tail2.rotateAngleY = (MathHelper.cos(limbSwing * 2.6662F) * 1.4F * limbSwingAmount) + (0.0625F * MathHelper.sin(0.15F * ageInTicks / 1.5F));
			}
		}
	}

	public void resetModel() {
		this.Neck.rotateAngleX = 0;
		this.Neck.rotateAngleY = -2;
		this.LLeg.rotationPointY = 4.5F;
		this.LLeg.rotationPointZ = 2;
		this.LLeg.rotateAngleX = 0;
		this.LLeg.rotateAngleY = 0;
		this.LLeg.rotateAngleZ = 0;
		this.RThigh.rotateAngleX = 0;
		this.RThigh.rotateAngleY = 0;
		this.Tail1.rotateAngleX = 0;
		this.Tail1.rotateAngleY = 0;
		this.LArm.rotateAngleZ = 0;
		this.Tail2.rotateAngleX = 0;
		this.Tail2.rotateAngleY = 0;
		this.LFoot.rotateAngleX = 0;
		this.RArm.rotateAngleZ = 0;
		this.Body.rotationPointY = 11.0F;
		this.RLeg.rotationPointY = 4.5F;
		this.RLeg.rotationPointZ = 2;
		this.RLeg.rotateAngleX = 0;
		this.RLeg.rotateAngleY = 0;
		this.RLeg.rotateAngleZ = 0;
		this.LThigh.rotateAngleX = 0;
		this.LThigh.rotateAngleY = 0;
		this.RFoot.rotateAngleX = 0;
		this.Body.rotateAngleX = 0;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
