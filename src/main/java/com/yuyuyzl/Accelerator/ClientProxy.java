package com.yuyuyzl.Accelerator;


import com.yuyuyzl.Accelerator.tile.TileBuildGuide;
import com.yuyuyzl.Accelerator.tile.TileBuildGuideRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by user on 2016/5/28.
 */
public class ClientProxy extends CommonProxy{
    public void preInit(){
        super.preInit();
        Config.clientPreInit();
}
    public void init(){
        super.init();
        ClientRegistry.bindTileEntitySpecialRenderer(TileBuildGuide.class, new TileBuildGuideRenderer());
    }
    public void postInit(){
        super.postInit();
    }
    @Override
    public boolean playerIsInCreativeMode(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
            return entityPlayerMP.theItemInWorldManager.isCreative();
        } else if (player instanceof EntityPlayerSP) {
            return Minecraft.getMinecraft().playerController.isInCreativeMode();
        }
        return false;
    }
}
