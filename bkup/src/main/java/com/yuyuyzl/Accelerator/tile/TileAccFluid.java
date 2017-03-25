package com.yuyuyzl.Accelerator.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by yuyuyzl on 2017/3/25.
 */
public class TileAccFluid extends TileEntity implements IFluidHandler{

    protected FluidTank fluidTank=new FluidTank(1000);

    @Override
    public void updateEntity() {
        super.updateEntity();
        //if(worldObj.getWorldTime()%10==0)System.out.println(String.valueOf(fluidTank.getFluidAmount()));
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.canFill(from,resource.getFluid())?fluidTank.fill(resource,doFill):0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return resource != null && resource.isFluidEqual(fluidTank.getFluid())?(!this.canDrain(from, resource.getFluid())?null:fluidTank.drain(resource.amount, doDrain)):null;

    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return !this.canDrain(from, null)?null:fluidTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return from==null;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{fluidTank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fluidTank.readFromNBT(compound.getCompoundTag("fluidtag"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound fluidTag=new NBTTagCompound();
        fluidTank.writeToNBT(fluidTag);
        compound.setTag("fluidtag",fluidTag);


    }
}
