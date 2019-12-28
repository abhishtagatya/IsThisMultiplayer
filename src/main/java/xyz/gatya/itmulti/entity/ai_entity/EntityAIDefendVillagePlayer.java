package xyz.gatya.itmulti.entity.ai_entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;
import xyz.gatya.itmulti.entity.EntityPlayerNPC;

public class EntityAIDefendVillagePlayer extends EntityAITarget
{
    EntityPlayerNPC playerNPC;
    /** The aggressor of the iron golem's village which is now the golem's attack target. */
    EntityLivingBase villageAgressorTarget;

    public EntityAIDefendVillagePlayer(EntityPlayerNPC player)
    {
        super(player, false, true);
        this.playerNPC = player;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        Village village = this.playerNPC.getVillage();

        if (village == null)
        {
            return false;
        }
        else
        {
            this.villageAgressorTarget = village.findNearestVillageAggressor(this.playerNPC);

            if (this.villageAgressorTarget instanceof EntityCreeper)
            {
                return false;
            }
            else if (this.isSuitableTarget(this.villageAgressorTarget, false))
            {
                return true;
            }
            else if (this.taskOwner.getRNG().nextInt(20) == 0)
            {
                this.villageAgressorTarget = village.getNearestTargetPlayer(this.playerNPC);
                return this.isSuitableTarget(this.villageAgressorTarget, false);
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.playerNPC.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }
}