package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileAccFluid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/25.
 */
public class AccFluidBlock extends AccMachineBlock implements ITileEntityProvider{

    @Override
    public IIcon getIcon(int facing, int meta) {
        if (facing==0 || facing==1){
            return empty;
        }else {
            return square;
        }
    }
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAccFluid();
    }
}
