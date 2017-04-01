package com.yuyuyzl.Accelerator.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/22.
 */
public class AccTunnelBlock extends AccMachineBlock{

    public IIcon stripe;

    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);
        stripe=icon.registerIcon(MODID + ":TunnelSideNormal");
    }

    @Override
    public IIcon getIcon(int facing, int meta) {
        if (facing==0 || facing==1){
            return square;
        }else {
            return stripe;
        }
    }
}
