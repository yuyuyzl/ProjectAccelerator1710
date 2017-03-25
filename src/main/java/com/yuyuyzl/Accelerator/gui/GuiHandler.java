package com.yuyuyzl.Accelerator.gui;

import com.yuyuyzl.Accelerator.container.ContainerAccCore;
import com.yuyuyzl.Accelerator.tile.TileAccCore;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by yuyuyzl on 2017/3/3.
 */
public class GuiHandler implements IGuiHandler {

    public GuiHandler(){

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 0:
                return new ContainerAccCore(player.inventory,(TileAccCore) world.getTileEntity(x,y,z));


        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 0:
                return new GuiAccCore(player.inventory,(TileAccCore) world.getTileEntity(x,y,z));


        }
        return null;
    }
}
