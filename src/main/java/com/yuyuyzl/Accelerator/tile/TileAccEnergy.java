package com.yuyuyzl.Accelerator.tile;

import ic2.api.energy.prefab.BasicSink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class TileAccEnergy extends TileEntity {

    private BasicSink ic2EnergySink = new BasicSink(this, Integer.MAX_VALUE, 5);

    @Override
    public void invalidate() {
        ic2EnergySink.invalidate(); // notify the energy sink
        super.invalidate(); // this is important for mc!
    }

    @Override
    public void onChunkUnload() {
        ic2EnergySink.onChunkUnload(); // notify the energy sink

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        ic2EnergySink.readFromNBT(tag);

    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        ic2EnergySink.writeToNBT(tag);

    }

    @Override
    public void updateEntity() {
        ic2EnergySink.updateEntity(); // notify the energy sink


    }

    public double getAllEnergy(){
        double t=ic2EnergySink.getEnergyStored();
        ic2EnergySink.setEnergyStored(0);
        return t;
    }
}

