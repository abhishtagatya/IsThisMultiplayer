package xyz.gatya.itmulti.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import xyz.gatya.itmulti.util.ModInformation;

public class LootTableHandler {
	public static final ResourceLocation NOOB_PLAYER_LOOT = LootTableList.register(new ResourceLocation(ModInformation.MOD_ID, "noob_player"));
	public static final ResourceLocation NORMAL_PLAYER_LOOT = LootTableList.register(new ResourceLocation(ModInformation.MOD_ID, "normal_player")); 
}
