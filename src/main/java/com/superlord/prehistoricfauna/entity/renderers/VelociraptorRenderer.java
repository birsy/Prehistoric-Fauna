package com.superlord.prehistoricfauna.entity.renderers;





import com.superlord.prehistoricfauna.entity.EntityVelociraptor;
import com.superlord.prehistoricfauna.entity.models.Triceratops;
import com.superlord.prehistoricfauna.entity.models.Velociraptor;
import com.superlord.prehistoricfauna.util.Reference;

import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.entity.Render;

import net.minecraft.client.renderer.entity.RenderLiving;

import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.client.registry.IRenderFactory;



public class VelociraptorRenderer extends RenderLiving<EntityVelociraptor> {



	public static final Factory FACTORY = new Factory();

	

	public VelociraptorRenderer(RenderManager manager) {

		super(manager, new Velociraptor(), 0.2F);

	}



	@Override

	protected ResourceLocation getEntityTexture(EntityVelociraptor entity) {
		if(entity.isChild()) {
			return new ResourceLocation(Reference.MOD_ID, "textures/entities/velociraptorchild.png");
		} else {
		return new ResourceLocation(Reference.MOD_ID, "textures/entities/velociraptormale.png");
		}
	}

	

	

	public static class Factory implements IRenderFactory<EntityVelociraptor> {



        @Override

        public Render<? super EntityVelociraptor> createRenderFor(RenderManager manager) {

            return new VelociraptorRenderer(manager);

        }



    }

}