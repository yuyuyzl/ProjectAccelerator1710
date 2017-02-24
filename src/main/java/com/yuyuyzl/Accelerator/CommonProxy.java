package com.yuyuyzl.Accelerator;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by user on 2016/5/28.
 */
public class CommonProxy {

    public Block blockAccHull;
    public void preInit(){




        Config.preInit();
    }
    public void init(){

    }
    public void postInit(){

    }
    public boolean playerIsInCreativeMode(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
            return entityPlayerMP.theItemInWorldManager.isCreative();
        }
        return false;
    }
}
