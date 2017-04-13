package com.yuyuyzl.Accelerator.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/4/10.
 */
public class AccTimeBlock extends AccMachineBlock{
    public IIcon HullTop;
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);

        HullTop = icon.registerIcon(MODID + ":BlockTime");

    }

    public IIcon getIcon(int facing, int meta) {
        return HullTop;
    }
}
