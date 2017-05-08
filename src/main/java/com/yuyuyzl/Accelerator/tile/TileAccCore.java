package com.yuyuyzl.Accelerator.tile;

import com.yuyuyzl.Accelerator.Config;
import com.yuyuyzl.Accelerator.block.*;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class TileAccCore extends TileEntity{

    public int dir;

    //private List<Integer> HullPosX=new ArrayList<Integer>(),HullPosY=new ArrayList<Integer>(),HullPosZ=new ArrayList<Integer>();
    private List<Integer> EnergyPosX=new ArrayList<Integer>(),EnergyPosY=new ArrayList<Integer>(),EnergyPosZ=new ArrayList<Integer>();
    private List<Integer> FluidPosX=new ArrayList<Integer>(),FluidPosY=new ArrayList<Integer>(),FluidPosZ=new ArrayList<Integer>();
    private List<Integer> TunnelPosX=new ArrayList<Integer>(),TunnelPosY=new ArrayList<Integer>(),TunnelPosZ=new ArrayList<Integer>();
    //private List<Integer> AdvTunnelPosX=new ArrayList<Integer>(),AdvTunnelPosY=new ArrayList<Integer>(),AdvTunnelPosZ=new ArrayList<Integer>();
    private List<Integer> CoolantPosX=new ArrayList<Integer>(),CoolantPosY=new ArrayList<Integer>(),CoolantPosZ=new ArrayList<Integer>();
    private List<Integer> TimePosX=new ArrayList<Integer>(),TimePosY=new ArrayList<Integer>(),TimePosZ=new ArrayList<Integer>();

    private int searchX,searchY,searchZ;
    private int psearchX,psearchY,psearchZ;

    public int stat=0;

    public double storedEnergy=0;
    public int guiField1 =0;//storedInt, FailX
    public int guiField3 =0;//lastConsumed, FailZ
    public int uuStored=0;
    public int guiField2 =0;//storedIntH, FailY
    public int guiField4 =0;//lastConsumedH
    public double drag=0;
    public double failrate=1;
    public double dragUI=0;
    public double failrateUI=0;
    public double accProgress=0;
    public int accProgressInt=0;
    public double EUperUU=1000000;

    //0=z+ , 2=z- , 3=x+ , 1=x-
    private final int dirDeltaX[]={0,-1,0,1};
    private final int dirDeltaZ[]={1,0,-1,0};
    public int posReset=0;
    public int waitT=0;

    private void doFail(int px,int py,int pz){
        posReset=10000;
        guiField1=px;
        guiField2=py;
        guiField3=pz;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        double energyIn=0;
        if(!worldObj.isRemote) {
            if(waitT>0&&stat!=3){
                waitT--;
                return;
            }
            if (posReset > 0) {
                //posReset--;
                stat=4;
                return;
            }
            if (posReset == 0) {
                stat = 0;
                /*
                HullPosX.clear();
                HullPosY.clear();
                HullPosZ.clear();*/
                EnergyPosX.clear();
                EnergyPosY.clear();
                EnergyPosZ.clear();
                TunnelPosX.clear();
                TunnelPosY.clear();
                TunnelPosZ.clear();
                FluidPosX.clear();
                FluidPosY.clear();
                FluidPosZ.clear();
                CoolantPosX.clear();
                CoolantPosY.clear();
                CoolantPosZ.clear();
                TimePosX.clear();
                TimePosY.clear();
                TimePosZ.clear();
                /*
                AdvTunnelPosX.clear();
                AdvTunnelPosY.clear();
                AdvTunnelPosZ.clear();*/
                storedEnergy=0;
                guiField1 =0;
                guiField3 =0;
                uuStored=0;
                searchX = xCoord + dirDeltaX[dir];
                searchY = yCoord;
                searchZ = zCoord + dirDeltaZ[dir];
                psearchX = searchX;
                psearchY = searchY;
                psearchZ = searchZ;
                waitT=0;
                posReset = -1;
                return;
            }

            switch (stat) {
                case 0:
                    if (worldObj.getBlock(searchX, searchY, searchZ) instanceof AccTunnelBlock) {
                        int count = 0, px = 0, pz = 0;
                        for (int i = 0; i <= 3; i++) {
                            Block bls=worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]);
                            if (bls instanceof AccTunnelBlock||
                                    bls instanceof AccAdvTunnel) {
                                if (count == 0) {
                                    px = searchX + dirDeltaX[i];
                                    pz = searchZ + dirDeltaZ[i];
                                }
                                count++;
                            }else if(bls instanceof AccMachineHull ||
                                    bls instanceof AccAdvMachineHull){
                                /*HullPosX.add(searchX+dirDeltaX[i]);
                                HullPosY.add(searchY);
                                HullPosZ.add(searchZ+dirDeltaZ[i]);*/
                            }else if(bls instanceof AccEnergyBlock){
                                EnergyPosX.add(searchX+dirDeltaX[i]);
                                EnergyPosY.add(searchY);
                                EnergyPosZ.add(searchZ+dirDeltaZ[i]);
                            }else if(bls instanceof AccFluidBlock){
                                FluidPosX.add(searchX+dirDeltaX[i]);
                                FluidPosY.add(searchY);
                                FluidPosZ.add(searchZ+dirDeltaZ[i]);
                            }else if(searchX + dirDeltaX[i]!=xCoord ||searchZ+dirDeltaZ[i]!=zCoord) {
                                doFail(searchX + dirDeltaX[i],searchY,searchZ+dirDeltaZ[i]);
                                return;
                            }
                        }
                        if(worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccMachineHull||
                                worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);*/
                        }else{
                            doFail(searchX,searchY+1,searchZ);
                            return;
                        }
                        if(worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccMachineHull||
                                worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY-1);
                            HullPosZ.add(searchZ);*/
                        }else{
                            doFail(searchX,searchY-1,searchZ);
                            return;
                        }
                        if (count == 2) {
                            TunnelPosX.add(searchX);
                            TunnelPosY.add(searchY);
                            TunnelPosZ.add(searchZ);
                            psearchX = searchX;
                            psearchY = searchY;
                            psearchZ = searchZ;
                            searchX = px;
                            searchZ = pz;
                            stat = 1;
                            waitT=5;


                        } else {
                            doFail(searchX,searchY,searchZ);
                            return;
                        }
                    }else {
                        doFail(searchX,searchY,searchZ);
                        return;
                    }
                    break;
                case 1:
                    if (worldObj.getBlock(searchX, searchY, searchZ) instanceof AccTunnelBlock) {

                        int count = 0, px = 0, pz = 0;
                        for (int i = 0; i <= 3; i++) {
                            Block bls = worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]);
                            if (bls instanceof AccTunnelBlock||
                                    bls instanceof AccAdvTunnel) {
                                if ((searchX + dirDeltaX[i] != psearchX) || (searchZ + dirDeltaZ[i] != psearchZ)) {
                                    px = searchX + dirDeltaX[i];
                                    pz = searchZ + dirDeltaZ[i];
                                }
                                count++;
                            } else if (bls instanceof AccMachineHull ||
                                    bls instanceof AccAdvMachineHull) {
                                /*HullPosX.add(searchX + dirDeltaX[i]);
                                HullPosY.add(searchY);
                                HullPosZ.add(searchZ + dirDeltaZ[i]);*/


                            } else if (bls instanceof AccEnergyBlock) {
                                EnergyPosX.add(searchX + dirDeltaX[i]);
                                EnergyPosY.add(searchY);
                                EnergyPosZ.add(searchZ + dirDeltaZ[i]);
                            } else if (bls instanceof AccFluidBlock) {
                                FluidPosX.add(searchX + dirDeltaX[i]);
                                FluidPosY.add(searchY);
                                FluidPosZ.add(searchZ + dirDeltaZ[i]);
                            } else if (searchX + dirDeltaX[i] != xCoord || searchZ + dirDeltaZ[i] != zCoord) {
                                //System.out.println("Failed1 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                                doFail(searchX + dirDeltaX[i],searchY,searchZ+dirDeltaZ[i]);
                                return;
                            }

                        }
                        if (worldObj.getBlock(searchX, searchY + 1, searchZ) instanceof AccMachineHull ||
                                worldObj.getBlock(searchX, searchY + 1, searchZ) instanceof AccAdvMachineHull) {
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY + 1);
                            HullPosZ.add(searchZ);*/
                        } else {
                            //System.out.println("Failed2 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                            doFail(searchX,searchY+1,searchZ);
                            return;
                        }
                        if (worldObj.getBlock(searchX, searchY - 1, searchZ) instanceof AccMachineHull ||
                                worldObj.getBlock(searchX, searchY - 1, searchZ) instanceof AccAdvMachineHull) {
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY - 1);
                            HullPosZ.add(searchZ);*/
                        } else {
                            //System.out.println("Failed3 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                            doFail(searchX,searchY-1,searchZ);
                            return;
                        }
                        if(px==searchX&&searchX==psearchX) {
                            if (worldObj.getBlock(searchX, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+1, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-1, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+1, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-1, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+2, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-2, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+1, searchY - 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-1, searchY - 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+1, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-1, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+2, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-2, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX+2, searchY, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX-2, searchY, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 2, searchZ) instanceof AccTimeBlock) {
                                TimePosX.add(searchX);
                                TimePosY.add(searchY);
                                TimePosZ.add(searchZ);
                            }
                        }
                        if(pz==searchZ&&searchZ==psearchZ) {
                            if (worldObj.getBlock(searchX, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 2, searchZ+1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 2, searchZ-1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 1, searchZ+1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 1, searchZ-1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 1, searchZ+1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY + 1, searchZ-2) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 2, searchZ+1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 2, searchZ-1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 1, searchZ+1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 1, searchZ-1) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 1, searchZ+2) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 1, searchZ-2) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY, searchZ+2) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY, searchZ-2) instanceof AccTimeBlock &&
                                    worldObj.getBlock(searchX, searchY - 2, searchZ) instanceof AccTimeBlock) {
                                TimePosX.add(searchX);
                                TimePosY.add(searchY);
                                TimePosZ.add(searchZ);
                            }
                        }
                        if (count != 2) {
                            //System.out.println("Failed @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                            doFail(searchX,searchY,searchZ);
                            return;
                        } else {
                            TunnelPosX.add(searchX);
                            TunnelPosY.add(searchY);
                            TunnelPosZ.add(searchZ);
                            psearchX = searchX;
                            psearchY = searchY;
                            psearchZ = searchZ;
                            searchX = px;
                            searchZ = pz;
                            waitT = 5;
                            //System.out.println("Running NT@"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        if (searchX == xCoord + dirDeltaX[dir] && searchZ == zCoord + dirDeltaZ[dir]) {
                            stat = 3;
                            searchX = 0;
                            searchY = 0;
                            searchZ = 0;
                            psearchX = 0;
                            psearchY = 0;
                            psearchZ = 0;
                            AccProperty property = calculateProperty(TunnelPosX, TunnelPosY, TunnelPosZ);
                            drag=property.drag;
                            failrate=property.failrate;

                            //System.out.println("Success with "+String.valueOf(failrate));
                            //System.out.println("Success");

                        }
                    } else if(worldObj.getBlock(searchX, searchY, searchZ) instanceof AccAdvTunnel){
                        int count = 0, px = 0, pz = 0, advcount=0;
                        for (int i = 0; i <= 3; i++) {
                            Block bls=worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]);
                            if (bls instanceof AccTunnelBlock||
                                    bls instanceof AccAdvTunnel) {
                                if ((searchX + dirDeltaX[i] != psearchX) || (searchZ + dirDeltaZ[i] != psearchZ)) {
                                    px = searchX + dirDeltaX[i];
                                    pz = searchZ + dirDeltaZ[i];
                                }
                                count++;
                                if(bls instanceof AccAdvTunnel)advcount++;
                            }else if(bls instanceof AccAdvMachineHull){
                                if(!(worldObj.getBlock(searchX + dirDeltaX[i], searchY+1, searchZ + dirDeltaZ[i]) instanceof AccAdvMachineHull)){
                                    doFail(searchX + dirDeltaX[i],searchY+1,searchZ+dirDeltaZ[i]);
                                    return;
                                }if(!(worldObj.getBlock(searchX + dirDeltaX[i], searchY-1, searchZ + dirDeltaZ[i]) instanceof AccAdvMachineHull)){
                                    doFail(searchX + dirDeltaX[i],searchY-1,searchZ+dirDeltaZ[i]);
                                    return;
                                }

                            }else {
                                doFail(searchX + dirDeltaX[i],searchY,searchZ+dirDeltaZ[i]);
                                return;
                            }
                        }
                        if(worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);*/
                        }else{
                            doFail(searchX,searchY+1,searchZ);
                            return;
                        }
                        if(worldObj.getBlock(searchX, searchY+2, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);*/
                        }else if(advcount==2 &&
                                (worldObj.getBlock(searchX, searchY+2, searchZ) instanceof AccCoolantBlock) &&
                                TunnelPosX.size()>=2 &&
                                worldObj.getBlock(TunnelPosX.get(TunnelPosX.size()-2),TunnelPosY.get(TunnelPosY.size()-2),TunnelPosZ.get(TunnelPosZ.size()-2)) instanceof AccTunnelBlock){
                            CoolantPosX.add(searchX);
                            CoolantPosY.add(searchY+2);
                            CoolantPosZ.add(searchZ);
                        }else {
                            doFail(searchX,searchY+2,searchZ);
                            return;
                        }

                        if(worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY-1);
                            HullPosZ.add(searchZ);*/
                        }else{
                            doFail(searchX,searchY-1,searchZ);
                            return;
                        }
                        if(worldObj.getBlock(searchX, searchY-2, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY-1);
                            HullPosZ.add(searchZ);*/
                        }else{
                            doFail(searchX,searchY-2,searchZ);
                            return;
                        }
                        if (count != 2) {
                            //System.out.println("Failed @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                            doFail(searchX,searchY,searchZ);
                            return;
                        } else {
                            TunnelPosX.add(searchX);
                            TunnelPosY.add(searchY);
                            TunnelPosZ.add(searchZ);
                            psearchX = searchX;
                            psearchY = searchY;
                            psearchZ = searchZ;
                            searchX = px;
                            searchZ = pz;
                            waitT = 5;
                            //System.out.println("Running AT@"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        if (searchX == xCoord + dirDeltaX[dir] && searchZ == zCoord + dirDeltaZ[dir]) {
                            stat = 3;
                            searchX = 0;
                            searchY = 0;
                            searchZ = 0;
                            psearchX = 0;
                            psearchY = 0;
                            psearchZ = 0;
                            AccProperty property = calculateProperty(TunnelPosX, TunnelPosY, TunnelPosZ);
                            drag=property.drag;
                            failrate=property.failrate;
                            //System.out.println("Success with "+String.valueOf(failrate));

                        }
                    }else{
                        doFail(searchX,searchY,searchZ);
                        return;
                    }
                    //clear all Energy inputs


                    for (int i=0;i<EnergyPosX.size();i++){
                        if(worldObj.getTileEntity(EnergyPosX.get(i),EnergyPosY.get(i),EnergyPosZ.get(i)) instanceof TileAccEnergy) {
                            TileAccEnergy te = (TileAccEnergy) worldObj.getTileEntity(EnergyPosX.get(i), EnergyPosY.get(i), EnergyPosZ.get(i));
                            if (te != null) {
                                energyIn += te.getAllEnergy();
                            }
                        }else {
                            doFail(EnergyPosX.get(i),EnergyPosY.get(i),EnergyPosZ.get(i));
                            return;
                        }
                    }


                    break;
                case 3:
                    if(waitT>0){
                        waitT--;
                    }else {
                        waitT=0;
                        searchX=TunnelPosX.get(psearchX%TunnelPosX.size());
                        searchY=TunnelPosY.get(psearchX%TunnelPosY.size());
                        searchZ=TunnelPosZ.get(psearchX%TunnelPosZ.size());
                        for (int i = 0; i < TimePosX.size(); i++) {
                            if(searchX==TimePosX.get(i)&&searchY==TimePosY.get(i)&&searchZ==TimePosZ.get(i)){
                                if (worldObj.getBlock(searchX, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+1, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-1, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+1, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-1, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+2, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-2, searchY + 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+1, searchY - 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-1, searchY - 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+1, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-1, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+2, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-2, searchY - 1, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX+2, searchY, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX-2, searchY, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 2, searchZ) instanceof AccTimeBlock) {

                                }else if (worldObj.getBlock(searchX, searchY + 2, searchZ) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 2, searchZ+1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 2, searchZ-1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 1, searchZ+1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 1, searchZ-1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 1, searchZ+1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY + 1, searchZ-2) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 2, searchZ+1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 2, searchZ-1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 1, searchZ+1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 1, searchZ-1) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 1, searchZ+2) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 1, searchZ-2) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY, searchZ+2) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY, searchZ-2) instanceof AccTimeBlock &&
                                        worldObj.getBlock(searchX, searchY - 2, searchZ) instanceof AccTimeBlock) {

                                }else {
                                    doFail(searchX,searchY,searchZ);
                                    return;
                                }
                            }
                        }
                        if(worldObj.getBlock(searchX,searchY,searchZ)instanceof AccTunnelBlock){
                            {
                                int count = 0, px = 0, pz = 0;
                                for (int i = 0; i <= 3; i++) {
                                    Block bls = worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]);
                                    if (bls instanceof AccTunnelBlock||
                                            bls instanceof AccAdvTunnel) {
                                        if ((searchX + dirDeltaX[i] != psearchX) || (searchZ + dirDeltaZ[i] != psearchZ)) {
                                            px = searchX + dirDeltaX[i];
                                            pz = searchZ + dirDeltaZ[i];
                                        }
                                        count++;
                                    } else if (bls instanceof AccMachineHull ||
                                            bls instanceof AccAdvMachineHull) {
                                    } else if (bls instanceof AccEnergyBlock) {

                                    } else if (bls instanceof AccFluidBlock) {
                                    } else if (searchX + dirDeltaX[i] != xCoord || searchZ + dirDeltaZ[i] != zCoord) {
                                        doFail(searchX + dirDeltaX[i],searchY,searchZ+dirDeltaZ[i]);
                                        return;
                                    }

                                }
                                if (worldObj.getBlock(searchX, searchY + 1, searchZ) instanceof AccMachineHull ||
                                        worldObj.getBlock(searchX, searchY + 1, searchZ) instanceof AccAdvMachineHull) {

                                } else {
                                    doFail(searchX,searchY+1,searchZ);
                                    return;
                                }
                                if (worldObj.getBlock(searchX, searchY - 1, searchZ) instanceof AccMachineHull ||
                                        worldObj.getBlock(searchX, searchY - 1, searchZ) instanceof AccAdvMachineHull) {

                                } else {
                                    doFail(searchX,searchY-1,searchZ);
                                    return;
                                }
                                if (count != 2) {
                                    //System.out.println("Failed @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                                    doFail(searchX,searchY,searchZ);
                                    return;
                                }

                            }
                        } else if (worldObj.getBlock(searchX,searchY,searchZ)instanceof AccAdvTunnel){

                            int count = 0, px = 0, pz = 0;
                            for (int i = 0; i <= 3; i++) {
                                Block bls=worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]);
                                if (bls instanceof AccTunnelBlock||
                                        bls instanceof AccAdvTunnel) {
                                    if ((searchX + dirDeltaX[i] != psearchX) || (searchZ + dirDeltaZ[i] != psearchZ)) {
                                        px = searchX + dirDeltaX[i];
                                        pz = searchZ + dirDeltaZ[i];
                                    }
                                    count++;
                                }else if(bls instanceof AccAdvMachineHull){
                                    if(!(worldObj.getBlock(searchX + dirDeltaX[i], searchY+1, searchZ + dirDeltaZ[i]) instanceof AccAdvMachineHull)){
                                        doFail(searchX + dirDeltaX[i],searchY+1,searchZ+dirDeltaZ[i]);
                                        return;
                                    }if(!(worldObj.getBlock(searchX + dirDeltaX[i], searchY-1, searchZ + dirDeltaZ[i]) instanceof AccAdvMachineHull)){
                                        doFail(searchX + dirDeltaX[i],searchY-1,searchZ+dirDeltaZ[i]);
                                        return;
                                    }

                                }else {
                                    doFail(searchX + dirDeltaX[i],searchY,searchZ+dirDeltaZ[i]);
                                    return;
                                }
                            }
                            if(worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);*/
                            }else{
                                doFail(searchX,searchY+1,searchZ);
                                return;
                            }
                            if(worldObj.getBlock(searchX, searchY+2, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);*/
                            }else if((worldObj.getBlock(searchX, searchY+2, searchZ) instanceof AccCoolantBlock)){

                            }else {
                                doFail(searchX,searchY+2,searchZ);
                                return;
                            }

                            if(worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY-1);
                            HullPosZ.add(searchZ);*/
                            }else{
                                doFail(searchX,searchY-1,searchZ);
                                return;
                            }
                            if(worldObj.getBlock(searchX, searchY-2, searchZ) instanceof AccAdvMachineHull){
                            /*HullPosX.add(searchX);
                            HullPosY.add(searchY-1);
                            HullPosZ.add(searchZ);*/
                            }else{
                                doFail(searchX,searchY-2,searchZ);
                                return;
                            }
                            if (count != 2) {
                                //System.out.println("Failed @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                                doFail(searchX,searchY,searchZ);
                                return;
                            }


                        } else {
                            doFail(searchX,searchY,searchZ);
                            return;
                            //System.out.println("Failed1 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        /*
                        searchX=HullPosX.get(psearchX%HullPosX.size());
                        searchY=HullPosY.get(psearchX%HullPosY.size());
                        searchZ=HullPosZ.get(psearchX%HullPosZ.size());
                        if(!(worldObj.getBlock(searchX,searchY,searchZ)instanceof AccMachineHull||
                                worldObj.getBlock(searchX,searchY,searchZ)instanceof AccAdvMachineHull)){
                            posReset=40;
                            //System.out.println("Failed2 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        */
                        psearchX++;
                    }

                    //process acceleration here

                    for (int i=0;i<EnergyPosX.size();i++){
                        if(worldObj.getTileEntity(EnergyPosX.get(i),EnergyPosY.get(i),EnergyPosZ.get(i)) instanceof TileAccEnergy) {
                            TileAccEnergy te = (TileAccEnergy) worldObj.getTileEntity(EnergyPosX.get(i), EnergyPosY.get(i), EnergyPosZ.get(i));
                            if (te != null) {
                                energyIn += te.getAllEnergy();
                            }
                        }else {
                            doFail(EnergyPosX.get(i),EnergyPosY.get(i),EnergyPosZ.get(i));
                            return;
                        }
                    }
                    //if(worldObj.getWorldTime()%10==0)System.out.println(String.valueOf(storedEnergy));
                    storedEnergy+=energyIn;
                    //if(worldObj.getWorldTime()%10==0)System.out.println(String.valueOf(calculateAcceleration(drag,energyIn, Config.kAcceleration,Config.kOverall)));
                    int numStablizer=0;
                    for (int i=0;i<CoolantPosX.size();i++){
                        if(worldObj.getTileEntity(CoolantPosX.get(i),CoolantPosY.get(i),CoolantPosZ.get(i))instanceof TileAccCoolant){
                            TileAccCoolant tile =(TileAccCoolant) worldObj.getTileEntity(CoolantPosX.get(i),CoolantPosY.get(i),CoolantPosZ.get(i));
                            FluidStack f= tile.drain(null,5,true);
                            if(f!=null && f.amount==5){
                                numStablizer++;
                            }
                        }else{
                            doFail(CoolantPosX.get(i),CoolantPosY.get(i),CoolantPosZ.get(i));
                            return;
                        }
                    }
                    double timeMultiplier=1+Config.kTime*TimePosX.size();
                    accProgress+=calculateAcceleration(drag,energyIn/timeMultiplier, Config.kAcceleration,Config.kOverall*timeMultiplier,numStablizer,failrate,Config.kStabilizer);
                    if (accProgress<0)accProgress=0;
                    if (accProgress>=100){
                        accProgress-=100;
                        guiField3 =(int)storedEnergy;
                        storedEnergy=0;
                        uuStored++;
                    }
                    for(int i=0;i<FluidPosX.size();i++){
                        if(worldObj.getTileEntity(FluidPosX.get(i),FluidPosY.get(i),FluidPosZ.get(i)) instanceof TileAccFluid){
                            TileAccFluid tile=(TileAccFluid) worldObj.getTileEntity(FluidPosX.get(i),FluidPosY.get(i),FluidPosZ.get(i));
                            if(tile.fluidTank.getFluidAmount()==0 && uuStored>0) {
                                tile.fluidTank.setFluid(FluidRegistry.getFluidStack("ic2uumatter", 1));
                                uuStored--;
                            }
                        }else {
                            doFail(FluidPosX.get(i),FluidPosY.get(i),FluidPosZ.get(i));
                            return;
                        }
                    }

                    break;
            }
        }
    }

    public class AccProperty{
        public double drag;
        public double failrate;
        public AccProperty(double drag,double failrate){
            this.drag=drag;
            this.failrate=failrate;
        }
    }

    private AccProperty calculateProperty(List<Integer> posListX, List<Integer> posListY, List<Integer> posListZ){
        double avgX=0,avgY=0,avgZ=0,avgDis=0,deltaDis=0;
        for (int i=0;i<posListX.size();i++){
            avgX+=posListX.get(i);
            avgY+=posListY.get(i);
            avgZ+=posListZ.get(i);
            //System.out.println(String.format("x%d,y%d,z%d",posListX.get(i),posListY.get(i),posListZ.get(i)));
        }
        avgX/=posListX.size();
        avgY/=posListX.size();
        avgZ/=posListX.size();
        //for(BlockPos p:posList)avgDis+=Math.sqrt(p.distanceSq(avgX,avgY,avgZ));
        for (int i=0;i<posListX.size();i++){
            double dis=Math.sqrt(Math.pow(posListX.get(i)-avgX,2)+
                    Math.pow(posListY.get(i)-avgY,2)+
                    Math.pow(posListZ.get(i)-avgZ,2));
            avgDis+=dis;
        }

        avgDis/=posListX.size();
        for (int i=0;i<posListX.size();i++){
            double dis=Math.sqrt(Math.pow(posListX.get(i)-avgX,2)+
                    Math.pow(posListY.get(i)-avgY,2)+
                    Math.pow(posListZ.get(i)-avgZ,2));
            deltaDis+=Math.pow(dis-avgDis,2);
        }

        deltaDis=Math.sqrt(deltaDis);
        //System.out.println(String.valueOf(deltaDis*1000/avgDis/posListX.size()/posListX.size()));
        //System.out.println(Config.kOverall);
        drag=deltaDis*1000/avgDis/posListX.size()/posListX.size()+Config.kDrag;
        failrate=avgDis*avgDis*Config.kFail;
        return new AccProperty(drag,failrate);
    }
    private double calculateAcceleration(double drag,double eu,double kAcceleration,double kOverall,int numStabilizer,double failrate,double kStabilizer){
        double r=failrate;
        for (int i=0;i<numStabilizer;i++){
            r*=kStabilizer;
            r-=0.01;
        }
        //if(worldObj.getWorldTime()%20==0)System.out.println(String.valueOf(r));
        return kOverall*(kAcceleration*Math.sqrt(eu)*(Math.random()>r?1:0)-drag);
    }
    public int[] toIntArray(List<Integer> l){
        int[] a=new int[l.size()];
        for (int i=0;i<l.size();i++) {
            a[i]=l.get(i);
        }
        return a;
    }
    public List<Integer> toArrayListInt(int[] a){
        List<Integer> l=new ArrayList<Integer>();
        for(int i:a){
            l.add(i);
        }
        return l;
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        dir=compound.getInteger("dirMachine");
        EnergyPosX=toArrayListInt(compound.getIntArray("energyx"));
        EnergyPosY=toArrayListInt(compound.getIntArray("energyy"));
        EnergyPosZ=toArrayListInt(compound.getIntArray("energyz"));
        /*HullPosX=toArrayListInt(compound.getIntArray("hullx"));
        HullPosY=toArrayListInt(compound.getIntArray("hully"));
        HullPosZ=toArrayListInt(compound.getIntArray("hullz"));*/
        TunnelPosX=toArrayListInt(compound.getIntArray("tunnelx"));
        TunnelPosY=toArrayListInt(compound.getIntArray("tunnely"));
        TunnelPosZ=toArrayListInt(compound.getIntArray("tunnelz"));
        FluidPosX=toArrayListInt(compound.getIntArray("fluidx"));
        FluidPosY=toArrayListInt(compound.getIntArray("fluidy"));
        FluidPosZ=toArrayListInt(compound.getIntArray("fluidz"));
        CoolantPosX=toArrayListInt(compound.getIntArray("coolantx"));
        CoolantPosY=toArrayListInt(compound.getIntArray("coolanty"));
        CoolantPosZ=toArrayListInt(compound.getIntArray("coolantz"));
        TimePosX=toArrayListInt(compound.getIntArray("timeposx"));
        TimePosY=toArrayListInt(compound.getIntArray("timeposy"));
        TimePosZ=toArrayListInt(compound.getIntArray("timeposz"));
        /*
        AdvTunnelPosX=toArrayListInt(compound.getIntArray("advtunnelx"));
        AdvTunnelPosY=toArrayListInt(compound.getIntArray("advtunnely"));
        AdvTunnelPosZ=toArrayListInt(compound.getIntArray("advtunnelz"));*/
        stat=compound.getInteger("stat");
        posReset=compound.getInteger("posreset");
        searchX=compound.getInteger("searchx");
        searchY=compound.getInteger("searchy");
        searchZ=compound.getInteger("searchz");
        psearchX=compound.getInteger("psearchx");
        psearchY=compound.getInteger("psearchy");
        psearchZ=compound.getInteger("psearchz");
        storedEnergy=compound.getDouble("storedenergy");
        EUperUU=compound.getDouble("euperuu");
        guiField1=compound.getInteger("gui1");
        guiField2=compound.getInteger("gui2");

        guiField3 =compound.getInteger("lastconsumedenergy");
        uuStored=compound.getInteger("uustored");
        drag=compound.getDouble("drag");
        failrate=compound.getDouble("failrate");
        accProgress=compound.getDouble("accprogress");

    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("dirMachine",dir);
        /*compound.setIntArray("hullx",toIntArray(HullPosX));
        compound.setIntArray("hully",toIntArray(HullPosY));
        compound.setIntArray("hullz",toIntArray(HullPosZ));*/
        compound.setIntArray("energyx", toIntArray(EnergyPosX));
        compound.setIntArray("energyy", toIntArray(EnergyPosY));
        compound.setIntArray("energyz", toIntArray(EnergyPosZ));
        compound.setIntArray("tunnelx", toIntArray(TunnelPosX));
        compound.setIntArray("tunnely", toIntArray(TunnelPosY));
        compound.setIntArray("tunnelz", toIntArray(TunnelPosZ));
        compound.setIntArray("fluidx", toIntArray(FluidPosX));
        compound.setIntArray("fluidy", toIntArray(FluidPosY));
        compound.setIntArray("fluidz", toIntArray(FluidPosZ));
        compound.setIntArray("coolantx", toIntArray(CoolantPosX));
        compound.setIntArray("coolanty", toIntArray(CoolantPosY));
        compound.setIntArray("coolantz", toIntArray(CoolantPosZ));
        compound.setIntArray("timeposx", toIntArray(TimePosX));
        compound.setIntArray("timeposy", toIntArray(TimePosY));
        compound.setIntArray("timeposz", toIntArray(TimePosZ));
        /*
        compound.setIntArray("advtunnelx",toIntArray(AdvTunnelPosX));
        compound.setIntArray("advtunnely",toIntArray(AdvTunnelPosY));
        compound.setIntArray("advtunnelz",toIntArray(AdvTunnelPosZ));*/
        compound.setInteger("stat",stat);
        compound.setInteger("posreset",posReset);
        compound.setInteger("searchx",searchX);
        compound.setInteger("searchy",searchY);
        compound.setInteger("searchz",searchZ);
        compound.setInteger("psearchx",psearchX);
        compound.setInteger("psearchy",psearchY);
        compound.setInteger("psearchz",psearchZ);
        compound.setDouble("storedenergy",storedEnergy);
        compound.setDouble("euperuu",EUperUU);
        compound.setInteger("uustored",uuStored);
        compound.setInteger("lastconsumedenergy", guiField3);
        compound.setInteger("gui1", guiField1);
        compound.setInteger("gui2", guiField2);
        compound.setDouble("drag",drag);
        compound.setDouble("failrate",failrate);
        compound.setDouble("accprogress",accProgress);

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
