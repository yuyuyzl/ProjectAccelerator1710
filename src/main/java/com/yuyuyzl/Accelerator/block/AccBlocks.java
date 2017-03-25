package com.yuyuyzl.Accelerator.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class AccBlocks {


    public static Block accMainBlock;
    public static Block accCoreBlock;
    public static Block accHullBlock;
    public static Block accTunnelBlock;
    public static Block accEnergyBlock;
    public static Block accFluidBlock;

    public static void init(){
        accMainBlock=new AccMachineBlock().setBlockName("AccMachineBlock");
        accCoreBlock=new AccCoreBlock().setBlockName("AccCoreBlock");
        accHullBlock=new AccMachineHull().setBlockName("AccHullBlock");
        accTunnelBlock=new AccTunnelBlock().setBlockName("AccTunnelBlock");
        accEnergyBlock=new AccEnergyBlock().setBlockName("AccEnergyBlock");
        accFluidBlock=new AccFluidBlock().setBlockName("AccFluidBlock");
        GameRegistry.registerBlock(accMainBlock,accMainBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accCoreBlock,accCoreBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accHullBlock,accHullBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accTunnelBlock,accTunnelBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accEnergyBlock,accEnergyBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accFluidBlock,accFluidBlock.getUnlocalizedName());
    }

}
