package com.yuyuyzl.Accelerator.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/4/10.
 */
public class AccTimeBlock extends AccMachineBlock{
    public IIcon HullTop,HullSide;
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);

        HullTop = icon.registerIcon(MODID + ":HullTopAdv");
        HullSide = icon.registerIcon(MODID + ":HullSideAdv");

    }

    public IIcon getIcon(int facing, int meta) {
        if(facing==1)return HullTop;
        if(facing==0)return square;
        return HullSide;
    }
}
