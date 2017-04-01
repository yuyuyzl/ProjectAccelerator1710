package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.tile.TileAccCoolant;
import com.yuyuyzl.Accelerator.tile.TileAccFluid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/3/27.
 */
public class AccCoolantBlock extends AccAdvMachineBlock implements ITileEntityProvider {

    public IIcon CoolantTop;
    public IIcon CoolantSide;

    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);
        CoolantSide = icon.registerIcon(MODID + ":HullSideAdv");
        CoolantTop = icon.registerIcon(MODID + ":CoolantTop");
    }

    @Override
    public IIcon getIcon(int facing, int meta) {
        if(facing==0)return square;
        if(facing==1)return CoolantTop;
        return CoolantSide;

    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAccCoolant();
    }
}