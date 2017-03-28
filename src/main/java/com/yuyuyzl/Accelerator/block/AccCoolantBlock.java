package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileAccCoolant;
import com.yuyuyzl.Accelerator.tile.TileAccFluid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by yuyuyzl on 2017/3/27.
 */
public class AccCoolantBlock extends AccAdvMachineBlock implements ITileEntityProvider {

    @Override
    public IIcon getIcon(int facing, int meta) {
        if (facing == 1) {
            return square;
        } else {
            return empty;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAccCoolant();
    }
}