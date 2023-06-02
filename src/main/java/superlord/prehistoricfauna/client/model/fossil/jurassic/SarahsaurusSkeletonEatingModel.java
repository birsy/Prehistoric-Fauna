package superlord.prehistoricfauna.client.model.fossil.jurassic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import superlord.prehistoricfauna.common.entity.fossil.jurassic.SarahsaurusSkeleton;

public class SarahsaurusSkeletonEatingModel extends EntityModel<SarahsaurusSkeleton> {
	private final ModelPart Ribs;

	public SarahsaurusSkeletonEatingModel(ModelPart root) {
		this.Ribs = root.getChild("Ribs");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Ribs = partdefinition.addOrReplaceChild("Ribs", CubeListBuilder.create().texOffs(4, 0).addBox(-3.5F, 1.0F, -12.0F, 7.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.7F, 2.0F, -0.4691F, 0.0F, 0.0F));

		PartDefinition LLeg1 = Ribs.addOrReplaceChild("LLeg1", CubeListBuilder.create().texOffs(41, 28).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, 1.5F, 0.4691F, 0.0F, 0.0F));

		PartDefinition LLeg2 = LLeg1.addOrReplaceChild("LLeg2", CubeListBuilder.create().texOffs(27, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 8.0F, 4.0F));

		PartDefinition LFoot = LLeg2.addOrReplaceChild("LFoot", CubeListBuilder.create().texOffs(19, 27).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition Vertabrae = Ribs.addOrReplaceChild("Vertabrae", CubeListBuilder.create().texOffs(7, 67).mirror().addBox(0.0F, 0.0F, -12.0F, 0.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Tail1Vertabrae = Ribs.addOrReplaceChild("Tail1Vertabrae", CubeListBuilder.create().texOffs(7, 29).addBox(0.0F, -1.0F, 0.0F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 6.0F, 0.0782F, 0.0F, 0.0F));

		PartDefinition Tail1CaudalRibs = Tail1Vertabrae.addOrReplaceChild("Tail1CaudalRibs", CubeListBuilder.create().texOffs(1, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Tail2Vertabrae = Tail1Vertabrae.addOrReplaceChild("Tail2Vertabrae", CubeListBuilder.create().texOffs(21, 31).addBox(0.0F, -1.0F, 0.0F, 0.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, 0.2346F, 0.0F, 0.0F));

		PartDefinition Tail2CaudalRibs = Tail2Vertabrae.addOrReplaceChild("Tail2CaudalRibs", CubeListBuilder.create().texOffs(18, 28).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Shoulders = Ribs.addOrReplaceChild("Shoulders", CubeListBuilder.create().texOffs(40, 64).addBox(-4.0F, 0.0F, -7.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -5.0F));

		PartDefinition RArm = Ribs.addOrReplaceChild("RArm", CubeListBuilder.create().texOffs(22, 52).mirror().addBox(-0.9F, -1.0F, -1.5F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 6.0F, -8.5F, 0.4691F, 0.0F, -0.6646F));

		PartDefinition Neck1CervicalRibs = Ribs.addOrReplaceChild("Neck1CervicalRibs", CubeListBuilder.create().texOffs(46, 3).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -11.0F, 0.2262F, 0.0F, 0.0F));

		PartDefinition Neck = Neck1CervicalRibs.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(38, 78).addBox(0.0F, -8.0F, -9.0F, 0.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition Neck1Vertabrae = Neck1CervicalRibs.addOrReplaceChild("Neck1Vertabrae", CubeListBuilder.create().texOffs(2, 27).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.0F));

		PartDefinition Skull = Neck1Vertabrae.addOrReplaceChild("Skull", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 1.5F, -0.6644F, 0.0F, 0.0F));

		PartDefinition RLeg1 = Ribs.addOrReplaceChild("RLeg1", CubeListBuilder.create().texOffs(41, 28).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 4.0F, 1.5F, 0.4691F, 0.0F, 0.0F));

		PartDefinition RLeg2 = RLeg1.addOrReplaceChild("RLeg2", CubeListBuilder.create().texOffs(27, 31).mirror().addBox(-0.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 8.0F, 4.0F));

		PartDefinition RFoot = RLeg2.addOrReplaceChild("RFoot", CubeListBuilder.create().texOffs(19, 27).addBox(-0.5F, 0.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition LArm = Ribs.addOrReplaceChild("LArm", CubeListBuilder.create().texOffs(22, 52).addBox(-1.1F, -1.0F, -1.5F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, -8.5F, 0.4691F, 0.0F, 0.6646F));

		return LayerDefinition.create(meshdefinition, 64, 100);
	}

	@Override
	public void setupAnim(SarahsaurusSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Ribs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
