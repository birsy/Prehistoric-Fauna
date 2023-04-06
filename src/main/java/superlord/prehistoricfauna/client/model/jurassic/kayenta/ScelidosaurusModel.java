package superlord.prehistoricfauna.client.model.jurassic.kayenta;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.ScelidosaurusEntity;

/**
 * ScelidosaurusModel - Either Mojang or a mod author (Taken From Memory)
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ScelidosaurusModel extends EntityModel<ScelidosaurusEntity> {
	public ModelRenderer LegRight;
	public ModelRenderer LegLeft;
	public ModelRenderer Body;
	public ModelRenderer Leg2Right;
	public ModelRenderer FootRight;
	public ModelRenderer Leg2Left;
	public ModelRenderer FootLeft;
	public ModelRenderer ArmRight;
	public ModelRenderer BodyOsteoderms;
	public ModelRenderer Tail;
	public ModelRenderer Neck;
	public ModelRenderer ArmLeft;
	public ModelRenderer BodyOsteoderms_1;
	public ModelRenderer TailOsteoderms;
	public ModelRenderer Tail2;
	public ModelRenderer BodyOsteoderms_1_1;
	public ModelRenderer Tail2Osteoderms;
	public ModelRenderer BodyOsteoderms_1_2;
	public ModelRenderer Head;
	public ModelRenderer Snout;
	public ModelRenderer part21;

	public ScelidosaurusModel() {
		this.textureWidth = 160;
		this.textureHeight = 75;
		this.LegRight = new ModelRenderer(this, 44, 33);
		this.LegRight.setRotationPoint(1.5F, 11.0F, 5.9F);
		this.LegRight.addBox(0.0F, 0.0F, -3.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 64, 4);
		this.Tail2.setRotationPoint(0.0F, 0.5F, 10.0F);
		this.Tail2.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 3.0F, 22.0F, 0.0F, 0.0F, 0.0F);
		this.Leg2Right = new ModelRenderer(this, 44, 47);
		this.Leg2Right.setRotationPoint(2.3F, 6.5F, 1.9F);
		this.Leg2Right.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.ArmLeft = new ModelRenderer(this, 28, 34);
		this.ArmLeft.mirror = true;
		this.ArmLeft.setRotationPoint(-4.5F, 2.0F, -16.0F);
		this.ArmLeft.addBox(-1.0F, 0.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.TailOsteoderms = new ModelRenderer(this, 104, 24);
		this.TailOsteoderms.setRotationPoint(0.0F, 1.5F, 8.0F);
		this.TailOsteoderms.addBox(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.Head = new ModelRenderer(this, 0, 44);
		this.Head.setRotationPoint(0.0F, -0.5F, -5.0F);
		this.Head.addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(Head, 0.2500000029182012F, 0.0F, 0.0F);
		this.LegLeft = new ModelRenderer(this, 44, 33);
		this.LegLeft.mirror = true;
		this.LegLeft.setRotationPoint(-1.5F, 11.0F, 6.0F);
		this.LegLeft.addBox(-4.0F, 0.0F, -3.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.BodyOsteoderms = new ModelRenderer(this, 71, 0);
		this.BodyOsteoderms.setRotationPoint(0.0F, -0.5F, 0.0F);
		this.BodyOsteoderms.addBox(-8.0F, 0.0F, -19.0F, 16.0F, 0.0F, 22.0F, 0.0F, 0.0F, 0.0F);
		this.ArmRight = new ModelRenderer(this, 28, 34);
		this.ArmRight.setRotationPoint(2.5F, 2.0F, -16.0F);
		this.ArmRight.addBox(0.0F, 0.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.FootLeft = new ModelRenderer(this, 43, 57);
		this.FootLeft.mirror = true;
		this.FootLeft.setRotationPoint(0.0F, 5.5F, 0.5F);
		this.FootLeft.addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.Body = new ModelRenderer(this, 0, 0);
		this.Body.setRotationPoint(0.0F, 12.0F, 8.0F);
		this.Body.addBox(-4.5F, -4.0F, -19.0F, 9.0F, 10.0F, 22.0F, 0.0F, 0.0F, 0.0F);
		this.Leg2Left = new ModelRenderer(this, 44, 47);
		this.Leg2Left.mirror = true;
		this.Leg2Left.setRotationPoint(-2.3F, 6.5F, 1.9F);
		this.Leg2Left.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.part21 = new ModelRenderer(this, 15, 49);
		this.part21.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.part21.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 3.0F, 0.00999999F, 0.0F, 0.0F);
		this.Neck = new ModelRenderer(this, 0, 32);
		this.Neck.setRotationPoint(0.0F, 0.0F, -19.0F);
		this.Neck.addBox(-1.5F, -2.0F, -6.0F, 3.0F, 5.0F, 7.0F, 0.0F, 0.0F, 0.0F);
		this.Snout = new ModelRenderer(this, 0, 52);
		this.Snout.setRotationPoint(0.0F, 0.0F, -4.0F);
		this.Snout.addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.BodyOsteoderms_1_1 = new ModelRenderer(this, 127, 6);
		this.BodyOsteoderms_1_1.setRotationPoint(0.0F, -1.5F, 2.0F);
		this.BodyOsteoderms_1_1.addBox(-1.0F, -0.5F, -10.0F, 2.0F, 1.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(BodyOsteoderms_1_1, 0.0F, 0.0F, 0.001745329278001762F);
		this.FootRight = new ModelRenderer(this, 43, 57);
		this.FootRight.setRotationPoint(0.0F, 5.5F, 0.5F);
		this.FootRight.addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.Tail = new ModelRenderer(this, 41, 2);
		this.Tail.setRotationPoint(0.0F, -3.5F, 3.0F);
		this.Tail.addBox(-2.5F, 0.0F, -1.0F, 5.0F, 5.0F, 12.0F, 0.0F, 0.0F, 0.0F);
		this.BodyOsteoderms_1_2 = new ModelRenderer(this, 122, 20);
		this.BodyOsteoderms_1_2.setRotationPoint(0.0F, -2.0F, 13.0F);
		this.BodyOsteoderms_1_2.addBox(-1.0F, 0.0F, -18.0F, 2.0F, 1.0F, 18.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(BodyOsteoderms_1_2, 0.0F, 0.0F, 0.001745329278001762F);
		this.Tail2Osteoderms = new ModelRenderer(this, 67, 35);
		this.Tail2Osteoderms.setRotationPoint(0.0F, 1.5F, 6.0F);
		this.Tail2Osteoderms.addBox(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 20.0F, 0.0F, 0.0F, 0.0F);
		this.BodyOsteoderms_1 = new ModelRenderer(this, 111, 40);
		this.BodyOsteoderms_1.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.BodyOsteoderms_1.addBox(-1.0F, 0.0F, -18.0F, 2.0F, 1.0F, 20.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(BodyOsteoderms_1, 0.0F, 0.0F, 0.001745329278001762F);
		this.Tail.addChild(this.Tail2);
		this.LegRight.addChild(this.Leg2Right);
		this.Body.addChild(this.ArmLeft);
		this.Tail.addChild(this.TailOsteoderms);
		this.Neck.addChild(this.Head);
		this.Body.addChild(this.BodyOsteoderms);
		this.Body.addChild(this.ArmRight);
		this.Leg2Left.addChild(this.FootLeft);
		this.LegLeft.addChild(this.Leg2Left);
		this.Head.addChild(this.part21);
		this.Body.addChild(this.Neck);
		this.Head.addChild(this.Snout);
		this.TailOsteoderms.addChild(this.BodyOsteoderms_1_1);
		this.Leg2Right.addChild(this.FootRight);
		this.Body.addChild(this.Tail);
		this.Tail2Osteoderms.addChild(this.BodyOsteoderms_1_2);
		this.Tail2.addChild(this.Tail2Osteoderms);
		this.BodyOsteoderms.addChild(this.BodyOsteoderms_1);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
		ImmutableList.of(this.LegRight, this.LegLeft, this.Body).forEach((modelRenderer) -> { 
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(ScelidosaurusEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialTick = ageInTicks - entityIn.ticksExisted;
		float attackProgress = entityIn.getMeleeProgress(partialTick);
		float speed = 1.0f;
		float degree = 1.0f;
		resetModel();
		if (entityIn.isAsleep()) {
			this.LegRight.rotationPointX = 1.0F;
			this.LegRight.rotationPointY = 15.5F;
			this.LegRight.rotationPointZ = 7.0F;
			this.LegRight.rotateAngleY = 0.03909537541112055F;
			this.LegRight.rotateAngleZ = -0.27366763203903305F;
			this.Tail2.rotateAngleX = 0.1563815016444822F;
			this.Tail2.rotateAngleY = 0.35185837453889574F;
			this.Leg2Right.rotationPointY = 7;
			this.Leg2Right.rotateAngleX = -0.9782570324270162F;
			this.ArmLeft.rotationPointX = -6.0F;
			this.ArmLeft.rotationPointY = 3.0F;
			this.ArmLeft.rotateAngleX = -1.407433498155583F;
			this.ArmLeft.rotateAngleY = -0.5082398928281348F;
			this.ArmLeft.rotateAngleZ = 0.1563815016444822F;
			this.Head.rotateAngleX = 0.35185837453889574F;
			this.LegLeft.rotationPointX = -1.0F;
			this.LegLeft.rotationPointY = 15.5F;
			this.LegLeft.rotationPointZ = 7.0F;
			this.LegLeft.rotateAngleY = -0.03909537541112055F;
			this.LegLeft.rotateAngleZ = 0.27366763203903305F;
			this.ArmRight.rotationPointX = 2.7F;
			this.ArmRight.rotationPointY = 3.0F;
			this.ArmRight.rotateAngleX = -1.2906709285865847F;
			this.ArmRight.rotateAngleY = 0.19547687289441354F;
			this.FootLeft.rotationPointX = -0.5F;
			this.FootLeft.rotationPointY = 3.5F;
			this.FootLeft.rotationPointZ = -0.3F;
			this.FootLeft.rotateAngleX = 1.0555751236166873F;
			this.FootLeft.rotateAngleY = 0.3909537457888271F;
			this.FootLeft.rotateAngleZ = 0.1558579075294158F;
			this.Body.rotationPointY = 17.3F;
			this.Leg2Left.rotationPointY = 7F;
			this.Leg2Left.rotateAngleX = -0.9782570324270162F;
			this.Neck.rotateAngleX = 0.35185837453889574F;
			this.Neck.rotateAngleY = -0.19547687289441354F;
			this.Neck.rotateAngleZ = 0.3127630032889644F;
			this.FootRight.rotationPointX = 0.5F;
			this.FootRight.rotationPointY = 3.5F;
			this.FootRight.rotationPointZ = -0.3F;
			this.FootRight.rotateAngleX = 1.0555751236166873F;
			this.FootRight.rotateAngleY = -0.3909537457888271F;
			this.FootRight.rotateAngleZ = -0.1558579075294158F;
			this.Tail.rotateAngleX = -0.3909537457888271F;
			this.Tail.rotateAngleY = 0.19547687289441354F;
			this.Tail.rotateAngleZ = 0.03909537541112055F;
		} else {
			if (entityIn.getRevengeTarget() != null || entityIn.isBipedal()) {
				this.Body.rotateAngleX = -0.3F;
				this.ArmLeft.rotateAngleX = 0.3F;
				this.ArmRight.rotateAngleX = 0.3F;
				this.Head.rotateAngleX = 0.3F;
				this.Tail.rotateAngleX = (-Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5))) + 0.15F;
				this.Tail2.rotateAngleX = (-Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5))) + 0.15F;
				this.ArmLeft.rotateAngleZ = Math.abs(-0.05F * MathHelper.sin(0.15F * ageInTicks / 3));
				this.ArmRight.rotateAngleZ = -Math.abs(-0.05F * MathHelper.sin(0.15F * ageInTicks / 3));
			} else {
				this.Body.rotateAngleX = 0;
				this.ArmLeft.rotateAngleX = -0F;
				this.ArmRight.rotateAngleX = -0F;
				this.Tail.rotateAngleX = -Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5));
				this.Tail2.rotateAngleX = -Math.abs(-0.05F * MathHelper.sin(0.1F * ageInTicks / 5));

				this.ArmLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
				this.ArmRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			}
			this.Head.rotateAngleX = (-Math.abs(-0.025F * MathHelper.sin(0.1F * ageInTicks / 3))) + (MathHelper.cos(-1.0F + limbSwing * speed * 0.3F) * degree * 0.05F * limbSwingAmount + 0.25F) + attackProgress * (float) Math.toRadians(25F);
			this.Body.rotationPointZ = 8F + attackProgress * -10F;
			this.LegRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.LegLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.Tail.rotateAngleY = -0.12F * MathHelper.sin(0.2F * ageInTicks / 5);
			this.Tail2.rotateAngleY = -0.12F * MathHelper.sin(0.2F * ageInTicks / 5);
			this.Neck.rotateAngleX = (headPitch * ((float)Math.PI / 180F)) + (Math.abs(-0.025F * MathHelper.sin(0.1F * ageInTicks / 3)));
			this.Neck.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
			if (entityIn.isEating()) {
				this.Neck.rotateAngleX = Math.abs(MathHelper.sin(0.05F * ageInTicks) * 0.75F) + 0.5F;
			}
			if (entityIn.isInWater()) {
				this.Body.rotationPointY = 18;
				this.Body.rotateAngleX = -0.25F;
				this.Tail.rotateAngleX = 0.125F;
				this.Tail2.rotateAngleX = 0.125F;
				this.LegLeft.rotationPointY = 17;
				this.LegRight.rotationPointY = 17;
				this.LegLeft.rotateAngleX = -0.25F * MathHelper.sin(0.15F * ageInTicks / 1.5F);
				this.LegRight.rotateAngleX = 0.25F * MathHelper.sin(0.15F * ageInTicks / 1.5F);
				this.ArmRight.rotateAngleX = -0.25F * MathHelper.sin(0.15F * ageInTicks / 1.5F);
				this.ArmLeft.rotateAngleX = 0.25F * MathHelper.sin(0.15F * ageInTicks / 1.5F);
				this.Neck.rotateAngleX = 0.0625F;
				this.Tail.rotateAngleY = (MathHelper.cos(limbSwing * 2.6662F) * 1.4F * limbSwingAmount) + (0.0625F * MathHelper.sin(0.15F * ageInTicks / 1.5F));
				this.Tail2.rotateAngleY = (MathHelper.cos(limbSwing * 2.6662F) * 1.4F * limbSwingAmount) + (0.0625F * MathHelper.sin(0.15F * ageInTicks / 1.5F));
			}
		}
	}

	public void resetModel() {
		this.LegRight.rotationPointX = 1.5F;
		this.LegRight.rotationPointY = 11.0F;
		this.LegRight.rotationPointZ = 5.9F;
		this.LegRight.rotateAngleX = 0;
		this.LegRight.rotateAngleY = 0;
		this.LegRight.rotateAngleZ = 0;
		this.Tail2.rotateAngleX = 0;
		this.Tail2.rotateAngleY = 0;
		this.Leg2Right.rotationPointY = 6.5F;
		this.Leg2Right.rotateAngleX = 0;
		this.ArmLeft.rotationPointX = -4.5F;
		this.ArmLeft.rotationPointY = 2.0F;
		this.ArmLeft.rotateAngleX = 0;
		this.ArmLeft.rotateAngleY = 0;
		this.ArmLeft.rotateAngleZ = 0;
		this.Head.rotateAngleX = 0.2500000029182012F;
		this.LegLeft.rotationPointX = -1.5F;
		this.LegLeft.rotationPointY = 11.0F;
		this.LegLeft.rotationPointZ = 6.0F;
		this.LegLeft.rotateAngleX = 0;
		this.LegLeft.rotateAngleY = 0;
		this.LegLeft.rotateAngleZ = 0;
		this.ArmRight.rotationPointX = 2.5F;
		this.ArmRight.rotationPointY = 2.0F;
		this.ArmRight.rotateAngleX = 0;
		this.ArmRight.rotateAngleY = 0;
		this.FootLeft.rotationPointX = 0.0F;
		this.FootLeft.rotationPointY = 5.5F;
		this.FootLeft.rotationPointZ = 0.5F;
		this.FootLeft.rotateAngleX = 0;
		this.FootLeft.rotateAngleY = 0;
		this.FootLeft.rotateAngleZ = 0;
		this.Body.rotationPointY = 12;
		this.Leg2Left.rotationPointY = 6.5F;
		this.Leg2Left.rotateAngleX = 0;
		this.Neck.rotateAngleX = 0;
		this.Neck.rotateAngleY = 0;
		this.Neck.rotateAngleZ = 0;
		this.FootRight.rotationPointX = 0.0F;
		this.FootRight.rotationPointY = 5.5F;
		this.FootRight.rotationPointZ = 0.5F;
		this.FootRight.rotateAngleX = 0;
		this.FootRight.rotateAngleY = 0;
		this.FootRight.rotateAngleZ = 0;
		this.Tail.rotateAngleX = 0;
		this.Tail.rotateAngleY = 0;
		this.Tail.rotateAngleZ = 0;
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
