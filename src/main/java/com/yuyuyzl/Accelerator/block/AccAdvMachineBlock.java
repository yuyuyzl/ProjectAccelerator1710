package com.yuyuyzl.Accelerator.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/27.
 */
public class AccAdvMachineBlock extends AccMachineBlock {
    public void registerBlockIcons(IIconRegister icon){
        empty= icon.registerIcon(MODID + ":machine1_empty");
        square = icon.registerIcon(MODID + ":machine1_square");
    }
    @Override
    public IIcon getIcon(int facing, int meta) {
        if (facing==0 || facing==1){
            return empty;
        }else {
            return square;
        }
    }
}
