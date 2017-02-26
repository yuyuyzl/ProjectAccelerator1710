package com.yuyuyzl.Accelerator.Block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by yuyuyzl on 2017/2/26.
 */
public abstract class AccMachineBlock extends Block {
    public AccMachineBlock() {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);

    }
}
