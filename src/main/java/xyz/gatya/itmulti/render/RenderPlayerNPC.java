package xyz.gatya.itmulti.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import xyz.gatya.itmulti.entity.EntityPlayerNPC;
import xyz.gatya.itmulti.util.ModInformation;

public class RenderPlayerNPC extends RenderLiving<EntityPlayerNPC> {

	protected ResourceLocation TEXTURES;
	
	public RenderPlayerNPC(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelPlayer(0.0F, false), 0.3F);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerArrow(this));
		this.addLayer(new LayerBipedArmor(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPlayerNPC entity) {
		return new ResourceLocation(
				ModInformation.MOD_ID + 
				":textures/entity/" + 
				entity.getTexture() + ".png"
			);
	}

}
