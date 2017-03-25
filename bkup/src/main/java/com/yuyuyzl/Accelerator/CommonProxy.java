package com.yuyuyzl.Accelerator;


import com.yuyuyzl.Accelerator.block.AccBlocks;
import com.yuyuyzl.Accelerator.creativetabs.AccCreativeTab;
import com.yuyuyzl.Accelerator.gui.GuiHandler;
import com.yuyuyzl.Accelerator.tile.AccTiles;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;

/**
 * Created by user on 2016/5/28.
 */
public class CommonProxy {

    public void preInit(){

        AccCreativeTab.init();
        AccBlocks.init();
        AccTiles.init();
        Config.preInit();
    }
    public void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(AcceleratorMod.instance, new GuiHandler());
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
