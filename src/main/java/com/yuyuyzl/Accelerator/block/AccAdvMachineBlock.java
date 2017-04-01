package com.yuyuyzl.Accelerator.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/27.
 */
public class AccAdvMachineBlock extends AccMachineBlock {
    public void registerBlockIcons(IIconRegister icon){
        square = icon.registerIcon(MODID + ":BlockAdv");
    }
    @Override
    public IIcon getIcon(int facing, int meta) {

            return square;

    }
}
