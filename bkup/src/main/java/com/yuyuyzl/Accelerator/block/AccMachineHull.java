package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileAccHull;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by yuyuyzl on 2017/3/21.
 */
public class AccMachineHull extends AccMachineBlock{

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {

    }

    //@Override
    //public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        //return new TileAccHull();
    //}

    @Override
    public IIcon getIcon(int facing, int meta) {
        return empty;
    }
}
