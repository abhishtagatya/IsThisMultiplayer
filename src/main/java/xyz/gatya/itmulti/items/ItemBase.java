package xyz.gatya.itmulti.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.gatya.itmulti.Main;
import xyz.gatya.itmulti.init.ModItems;
import xyz.gatya.itmulti.util.IHasModel;

public class ItemBase extends Item implements IHasModel{
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
