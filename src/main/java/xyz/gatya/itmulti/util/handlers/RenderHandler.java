package xyz.gatya.itmulti.util.handlers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.gatya.itmulti.entity.EntityPlayerNPC;
import xyz.gatya.itmulti.render.RenderPlayerNPC;


public class RenderHandler {
	
	public static void registerEntityRenders() {
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayerNPC.class, new IRenderFactory<EntityPlayerNPC>() {

			@Override
			public Render<? super EntityPlayerNPC> createRenderFor(RenderManager manager) {
				return new RenderPlayerNPC(manager);
			}		
		
		});
	}
}
