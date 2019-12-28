package xyz.gatya.itmulti;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.gatya.itmulti.init.ModEntities;
import xyz.gatya.itmulti.proxy.CommonProxy;
import xyz.gatya.itmulti.util.ModInformation;
import xyz.gatya.itmulti.util.handlers.RenderHandler;
import xyz.gatya.itmulti.world.gen.WorldGenCustomStructure;

@Mod(
	modid=ModInformation.MOD_ID,
	name=ModInformation.NAME,
	version=ModInformation.VERSION
)
public class Main {
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide=ModInformation.CLIENT_PROXY_CLASS, serverSide=ModInformation.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		ModEntities.registerEntities();
		RenderHandler.registerEntityRenders();
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructure(), 0);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}

}
