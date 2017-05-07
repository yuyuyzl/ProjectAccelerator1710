package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.creativetabs.AccCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 * Created by yuyuyzl on 2017/5/7.
 */
public class AccFakeBlock extends Block {

    private int type;

    public AccFakeBlock(int type) {
        super(Material.iron);
        setHardness(0.6F);
        setCreativeTab(AccCreativeTab.accCreativeTab);
        this.type=type;
    }




    public void registerBlockIcons(IIconRegister icon){
    }
    @Override
    public IIcon getIcon(int facing, int meta) {
        switch (type){
            case 0:
                return AccBlocks.accHullBlock.getIcon(facing,meta);
            case 1:
                return AccBlocks.accAdvHullBlock.getIcon(facing, meta);
        }
        return null;
    }
}
