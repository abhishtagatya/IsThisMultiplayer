package xyz.gatya.itmulti.entity;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import xyz.gatya.itmulti.entity.ai_entity.EntityAIDefendVillagePlayer;
import xyz.gatya.itmulti.util.handlers.LootTableHandler;

public class EntityPlayerNPC extends EntityVillager {
	
	public static final int ID = 14042;
	
	public String playerName;
	public String playerTexture;
	public int playerLevel;
	
	@Nullable
    Village village;
	private int attackTimer;
	
	private String[] NAME_AVAILABLE = {
		"sub2poods", "t.tv/gurlgamer", "sub4sub", "lamegamer101", "xXMoGaminXx",
		"iamnoob", "new_comer4", "carpediem", "MCProMiner", "pvpMaster", "theYTGuy",
		"slayin145", "jamesCharlesFAN", "saladass", "skydustminecraft", "captainEnder",
		"enderslayed", "yt.chillguy"
	};
	
	private String[] TEXTURE_AVAILABLE = {
		"steve", "alex", "block_noob", "elmo", "elmo_steve",
		"girl_pink", "tv_head"
	};
	
	private Random rand = new Random();

	public EntityPlayerNPC(World worldIn) {
		super(worldIn);
		
		generateName();
		generateTexture();
		generateLevel();
		setCustomNameTag(this.playerName);
	}
	
	protected void generateName() {
		this.playerName = NAME_AVAILABLE[rand.nextInt(NAME_AVAILABLE.length)];
	}
	
	protected void generateTexture() {
		this.playerTexture = TEXTURE_AVAILABLE[rand.nextInt(TEXTURE_AVAILABLE.length)];
	}
	
	protected void generateLevel() {
		
		/* Level 0 - 4 : Noob
		 * Level 4 - 7 : Normal
		 * Level 7 - 8 : Advance
		 * Level 9	   : Pro
		 * */
		
		this.playerLevel = rand.nextInt(10);
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public String getTexture() {
		return this.playerTexture;
	}
	
	public int getLevel() {
		return this.playerLevel;
	}
	
	public Village getVillage()
    {
        return this.village;
    }
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, true));
		
		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntitySkeleton.class, 8.0F, 0.6D, 0.6D));
	    this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityCreeper.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
        
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));

        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayerNPC.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        
        this.targetTasks.addTask(1, new EntityAIDefendVillagePlayer(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 100, false, true, null));
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		
		int random_spawn_item;
		
		switch (this.playerLevel) {
		case 0:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.DIRT));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.CRAFTING_TABLE));
				break;
			default:
				break;
			}
			break;
		
		case 1:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_AXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_PICKAXE));
			default:
				break;
			}
			break;
			
		case 2:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.FURNACE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.COAL));
				break;
			default:
				break;
			}
			break;
		
		case 3:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.IRON_ORE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.COAL));
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				break;
			}
			break;
			
		case 4:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_INGOT));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				break;
			}
			break;
			
		case 5:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.COOKED_CHICKEN));
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				break;
			}
			break;
			
		case 6:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.COOKED_BEEF));
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				break;
			}
			break;
			
		case 7:
			random_spawn_item = rand.nextInt(10);
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.COOKED_BEEF));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.BAKED_POTATO));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
				break;
			case 3:
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			}
			break;
			
		case 8:
			random_spawn_item = rand.nextInt(10);
			
			// SET ENCHANTMENTS
			
			switch (random_spawn_item) {
			case 0:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.COOKED_BEEF));
				break;
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.BAKED_POTATO));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
				break;
			case 3:
				break;
			default:
				break;
			}
			
			random_spawn_item = rand.nextInt(10);
			
			switch(random_spawn_item) {
			case 1:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			case 2:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			case 3:
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			case 4:
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				break;
			}
			break;
			
		case 9:
			random_spawn_item = rand.nextInt(10);
			
			// SET ENCHANTMENTS
			
			if (random_spawn_item > 2) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			}
			
			
			random_spawn_item = rand.nextInt(10);
			
			if (random_spawn_item > 3) {
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
			}
			break;
		}
		
		return livingdata;
	}
	
	public boolean attackEntityAsMob(Entity entityIn) {
		
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(1 + this.getHeldItemMainhand().getMaxDamage() / 100));
//		System.out.println(this.getHeldItemMainhand().getItemDamage() / 100);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }
        
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 1.0F);
        return flag;
    }
	
	protected void collideWithEntity(Entity entityIn)
    {
        if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0)
        {
            this.setAttackTarget((EntityLivingBase)entityIn);
        }

        super.collideWithEntity(entityIn);
    }
	
	@Override
	protected boolean canEquipItem(ItemStack stack) {
		return true;
	}
	
	@Override
	protected boolean canDespawn() {
		return true;
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		
		if (this.playerLevel >= 0 && this.playerLevel <= 4) {
			return LootTableHandler.NOOB_PLAYER_LOOT;
		}
		
		if (this.playerLevel > 4 && this.playerLevel <= 7) {
			return LootTableHandler.NORMAL_PLAYER_LOOT;
		}
		
		return null;
		
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_PLAYER_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_PLAYER_DEATH;
	}
}
