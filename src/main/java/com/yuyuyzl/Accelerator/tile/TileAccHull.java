package com.yuyuyzl.Accelerator.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by yuyuyzl on 2017/3/21.
 */
public class TileAccHull extends TileEntity {

    private int CorePosX,CorePosY,CorePosZ;
    private boolean hasCoreConn;

    public boolean setCorePos(int x,int y,int z){
        if (hasCoreConn)return false;
        CorePosX=x;
        CorePosY=y;
        CorePosZ=z;
        hasCoreConn=true;
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        hasCoreConn=compound.getBoolean("CC");
        CorePosX=compound.getInteger("CPX");
        CorePosY=compound.getInteger("CPY");
        CorePosZ=compound.getInteger("CPZ");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("CC",hasCoreConn);
        compound.setInteger("CPX",CorePosX);
        compound.setInteger("CPY",CorePosY);
        compound.setInteger("CPZ",CorePosZ);


    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }
}
