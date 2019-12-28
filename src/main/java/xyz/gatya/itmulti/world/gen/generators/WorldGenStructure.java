package xyz.gatya.itmulti.world.gen.generators;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import xyz.gatya.itmulti.util.IStructure;
import xyz.gatya.itmulti.util.ModInformation;

public class WorldGenStructure extends WorldGenerator implements IStructure {
	
	public static String structureName;
	
	public WorldGenStructure(String name) {
		this.structureName = name;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		this.generateStructure(worldIn, position);
		return true;
	}

	public static void generateStructure(World worldIn, BlockPos position) {
		MinecraftServer mcServer = worldIn.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = new ResourceLocation(ModInformation.MOD_ID, structureName);
		Template template = manager.get(mcServer, location);
		
		if (template != null) {
			IBlockState state = worldIn.getBlockState(position);
			worldIn.notifyBlockUpdate(position, state, state, 3);
			template.addBlocksToWorldChunk(worldIn, position, settings);
		}
	}

}
