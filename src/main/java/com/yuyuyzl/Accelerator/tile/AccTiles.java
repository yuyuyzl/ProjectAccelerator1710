package com.yuyuyzl.Accelerator.tile;

import cpw.mods.fml.common.registry.GameRegistry;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class AccTiles {
    public static void init(){
        GameRegistry.registerTileEntity(TileAccCore.class,MODID+":TileAccCore");
        GameRegistry.registerTileEntity(TileAccHull.class,MODID+":TileAccHull");
        GameRegistry.registerTileEntity(TileAccEnergy.class,MODID+":TileAccEnergy");
        GameRegistry.registerTileEntity(TileAccFluid.class,MODID+":TileAccFluid");
        GameRegistry.registerTileEntity(TileAccCoolant.class,MODID+":TileAccCoolant");

    }
}
