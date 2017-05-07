package com.yuyuyzl.Accelerator.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class AccBlocks {


    public static Block accMachineBlock;
    public static Block accCoreBlock;
    public static Block accHullBlock;
    public static Block accTunnelBlock;
    public static Block accEnergyBlock;
    public static Block accFluidBlock;
    public static Block accAdvMachineBlock;
    public static Block accCoolantBlock;
    public static Block accAdvHullBlock;
    public static Block accAdvTunnelBlock;
    public static Block accBuildGuide;
    public static Block accTimeBlock;
    public static Block accFakeBlockN;
    public static Block accFakeBlockA;

    public static void init(){
        accMachineBlock=new AccMachineBlock().setBlockName("AccMachineBlock");
        accCoreBlock=new AccCoreBlock().setBlockName("AccCoreBlock");
        accHullBlock=new AccMachineHull().setBlockName("AccHullBlock");
        accTunnelBlock=new AccTunnelBlock().setBlockName("AccTunnelBlock");
        accEnergyBlock=new AccEnergyBlock().setBlockName("AccEnergyBlock");
        accFluidBlock=new AccFluidBlock().setBlockName("AccFluidBlock");
        accAdvMachineBlock=new AccAdvMachineBlock().setBlockName("AccAdvMachineBlock");
        accCoolantBlock=new AccCoolantBlock().setBlockName("AccCoolantBlock");
        accAdvHullBlock=new AccAdvMachineHull().setBlockName("AccAdvMachineHull");
        accAdvTunnelBlock=new AccAdvTunnel().setBlockName("AccAdvTunnel");
        accBuildGuide=new AccBuildGuide().setBlockName("AccBuildGuide");
        accTimeBlock=new AccTimeBlock().setBlockName("AccTimeBlock");
        accFakeBlockN=new AccFakeBlock(0).setBlockName("AccFakeHullBlock");
        accFakeBlockA=new AccFakeBlock(1).setBlockName("AccFakeAdvHullBlock");
        GameRegistry.registerBlock(accMachineBlock,accMachineBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accCoreBlock,accCoreBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accHullBlock,accHullBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accTunnelBlock,accTunnelBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accEnergyBlock,accEnergyBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accFluidBlock,accFluidBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accAdvMachineBlock,accAdvMachineBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accCoolantBlock,accCoolantBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accAdvHullBlock,accAdvHullBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accAdvTunnelBlock,accAdvTunnelBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accBuildGuide,accBuildGuide.getUnlocalizedName());
        GameRegistry.registerBlock(accTimeBlock,accTimeBlock.getUnlocalizedName());
        GameRegistry.registerBlock(accFakeBlockN,accFakeBlockN.getUnlocalizedName());
        GameRegistry.registerBlock(accFakeBlockA,accFakeBlockA.getUnlocalizedName());


    }

}
