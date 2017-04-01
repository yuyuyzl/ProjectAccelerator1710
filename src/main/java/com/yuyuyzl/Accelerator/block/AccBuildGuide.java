package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileBuildGuide;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/29.
 */
public class AccBuildGuide extends AccMachineBlock implements ITileEntityProvider {
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBuildGuide();
    }

    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public IIcon GuideTop,GuideSide;

    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);

        GuideTop = icon.registerIcon(MODID + ":GuideTop");
        GuideSide = icon.registerIcon(MODID + ":GuideSide");
    }

    @Override
    public IIcon getIcon(int facing, int meta) {
        if(facing==0)return square;
        if(facing==1)return GuideTop;
        return GuideSide;
    }
}
