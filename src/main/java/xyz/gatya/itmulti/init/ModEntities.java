package xyz.gatya.itmulti.init;

import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.gatya.itmulti.Main;
import xyz.gatya.itmulti.entity.EntityPlayerNPC;
import xyz.gatya.itmulti.util.ModInformation;

public class ModEntities {
	
	public static void registerEntities() {
		
		// TODO: Change the weight of spawn
		
//		registerEntity("noob_player", EntityPlayerNoob.class, EntityPlayerNoob.ID, 50, MapColor.GREEN.colorValue, MapColor.WATER.colorValue);
//		registerSpawn(EntityPlayerNoob.class, 20, 1, 3, EnumCreatureType.AMBIENT,
//				new Biome[] {Biomes.BEACH, Biomes.FOREST, Biomes.TAIGA});
//		
//		registerEntity("normal_player", EntityPlayerNormal.class, EntityPlayerNormal.ID, 50, MapColor.GREEN.colorValue, MapColor.WATER.colorValue);
//		registerSpawn(EntityPlayerNormal.class, 10, 1, 3, EnumCreatureType.AMBIENT,
//				new Biome[] {Biomes.BEACH, Biomes.FOREST, Biomes.TAIGA, Biomes.DESERT, Biomes.RIVER});
		
		registerEntity("npc_player", EntityPlayerNPC.class, EntityPlayerNPC.ID, 50, MapColor.GREEN.colorValue, MapColor.WATER.colorValue);
		registerSpawn(EntityPlayerNPC.class, 10, 1, 3, EnumCreatureType.AMBIENT,
				new Biome[] {Biomes.BEACH, Biomes.FOREST, Biomes.TAIGA, Biomes.DESERT, Biomes.RIVER});
	}
	
	private static void registerEntity(
			String name, Class<? extends Entity> entity, int id,
			int range, int color_primary, int color_secondary) {
		
		EntityRegistry.registerModEntity(
				new ResourceLocation(ModInformation.MOD_ID + ":" + name), entity, name, id,
				Main.instance, range, 1,
				true, MapColor.BLUE.colorValue, MapColor.GREEN.colorValue);
	}
	
	private static void registerSpawn(
			Class <? extends EntityLiving > entityClass, int weightedProb,
			int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
	}
	
}
