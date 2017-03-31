package com.yuyuyzl.Accelerator.tile;

import net.minecraft.block.BlockStone;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * Created by yuyuyzl on 2017/3/29.
 */
public class TileBuildGuide extends TileEntity{

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    public int radius=0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(worldObj.isRemote){
            radius=0;
            boolean yy=true;
            int i=0;
            while (yy){
                yy=false;
                i++;
                if(worldObj.getBlock(xCoord,yCoord+i,zCoord).getUnlocalizedName().endsWith("stonebrick")) {
                    yy = true;
                    radius += 2;
                    continue;
                }
                if(worldObj.getBlock(xCoord,yCoord+i,zCoord).getUnlocalizedName().endsWith("wood")) {
                    yy = true;
                    radius += 1;
                    continue;
                }
                if(worldObj.getBlock(xCoord,yCoord+i,zCoord).getUnlocalizedName().endsWith("blockIron")) {
                    yy = true;
                    radius += 5;
                    continue;
                }
                if(worldObj.getBlock(xCoord,yCoord+i,zCoord).getUnlocalizedName().endsWith("blockGold")) {
                    yy = true;
                    radius += 10;
                    continue;
                }

                //System.out.println(worldObj.getBlock(xCoord,yCoord+i,zCoord).getUnlocalizedName());
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
    }
}
