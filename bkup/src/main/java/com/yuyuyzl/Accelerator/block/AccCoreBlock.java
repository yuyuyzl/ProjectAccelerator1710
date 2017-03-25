package com.yuyuyzl.Accelerator.block;

import com.yuyuyzl.Accelerator.AcceleratorMod;
import com.yuyuyzl.Accelerator.tile.TileAccCore;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.yuyuyzl.Accelerator.AcceleratorMod.MODID;

/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class AccCoreBlock extends AccMachineBlock implements ITileEntityProvider{
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileAccCore te=(TileAccCore) world.getTileEntity(x,y,z);
        te.dir=direction;

        //System.out.println(String.valueOf(direction));

    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAccCore();
    }

    public IIcon screen;
    public IIcon hole;
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);
        screen=icon.registerIcon(MODID+":machine_screen");
        hole=icon.registerIcon(MODID+":machine_hole");
    }

    public static final int[] dirtofac={2,5,3,4};
    public static final int[] dirtoopp={3,4,2,5};
    //0=z+ , 2=z- , 3=x+ , 1=x-

    @Override
    public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int facing) {
        TileAccCore te=(TileAccCore)iBlockAccess.getTileEntity(x,y,z);
        //System.out.println("te.dir="+String.valueOf(te.dir));
        if (facing==dirtofac[te.dir])
            return screen;
        if (facing==dirtoopp[te.dir])
            return hole;
        return getIcon(facing,0);

    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        player.openGui(AcceleratorMod.instance,0,world,x,y,z);
        return true;
    }
}
