package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.creativetabs.AccCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/2/26.
 */
public class AccMachineBlock extends Block{

    public IIcon square;

    public AccMachineBlock() {
        super(Material.iron);
        setHardness(0.6F);
        setCreativeTab(AccCreativeTab.accCreativeTab);
    }


    public void registerBlockIcons(IIconRegister icon){
        square = icon.registerIcon(MODID + ":BlockNormal");
    }
    @Override
    public IIcon getIcon(int facing, int meta) {
            return square;
    }
}
