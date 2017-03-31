package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileBuildGuide;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

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

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return world.getBlockMetadata(x, y, z) == 1?false:side == ForgeDirection.DOWN;
    }




}
