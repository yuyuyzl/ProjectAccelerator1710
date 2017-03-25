package com.yuyuyzl.Accelerator.tile;

import com.yuyuyzl.Accelerator.Config;
import com.yuyuyzl.Accelerator.block.AccEnergyBlock;
import com.yuyuyzl.Accelerator.block.AccFluidBlock;
import com.yuyuyzl.Accelerator.block.AccMachineHull;
import com.yuyuyzl.Accelerator.block.AccTunnelBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import scala.Int;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class TileAccCore extends TileEntity{

    public int dir;

    private List<Integer> HullPosX=new ArrayList<Integer>(),HullPosY=new ArrayList<Integer>(),HullPosZ=new ArrayList<Integer>();
    private List<Integer> EnergyPosX=new ArrayList<Integer>(),EnergyPosY=new ArrayList<Integer>(),EnergyPosZ=new ArrayList<Integer>();
    private List<Integer> FluidPosX=new ArrayList<Integer>(),FluidPosY=new ArrayList<Integer>(),FluidPosZ=new ArrayList<Integer>();
    private List<Integer> TunnelPosX=new ArrayList<Integer>(),TunnelPosY=new ArrayList<Integer>(),TunnelPosZ=new ArrayList<Integer>();

    private int searchX,searchY,searchZ;
    private int psearchX,psearchY,psearchZ;
    public int stat=0;

    public double storedEnergy=0;
    public int storedEnergyInt=0;
    public int lastConsumedEnergy=0;
    public int uuStored=0;
    public double drag=0;
    public double accProgress=0;
    public int accProgressInt=0;
    public double EUperUU=1000000;

    //0=z+ , 2=z- , 3=x+ , 1=x-
    private final int dirDeltaX[]={0,-1,0,1};
    private final int dirDeltaZ[]={1,0,-1,0};
    public int posReset=0;
    public int waitT=0;
    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote) {
            if(waitT>0&&stat!=3){
                waitT--;
                return;
            }
            if (posReset > 0) {
                posReset--;
                return;
            }
            if (posReset == 0) {
                stat = 0;
                HullPosX.clear();
                HullPosY.clear();
                HullPosZ.clear();
                EnergyPosX.clear();
                EnergyPosY.clear();
                EnergyPosZ.clear();
                TunnelPosX.clear();
                TunnelPosY.clear();
                TunnelPosZ.clear();
                FluidPosX.clear();
                FluidPosY.clear();
                FluidPosZ.clear();
                storedEnergy=0;
                storedEnergyInt=0;
                lastConsumedEnergy=0;
                uuStored=0;
                searchX = xCoord + dirDeltaX[dir];
                searchY = yCoord;
                searchZ = zCoord + dirDeltaZ[dir];
                psearchX = searchX;
                psearchY = searchY;
                psearchZ = searchZ;
                posReset = -1;
                return;
            }

            switch (stat) {
                case 0:
                    if (worldObj.getBlock(searchX, searchY, searchZ) instanceof AccTunnelBlock) {
                        int count = 0, px = 0, pz = 0;
                        for (int i = 0; i <= 3; i++) {
                            if (worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccTunnelBlock) {
                                if (count == 0) {
                                    px = searchX + dirDeltaX[i];
                                    pz = searchZ + dirDeltaZ[i];
                                }
                                count++;
                            }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccMachineHull){
                                HullPosX.add(searchX+dirDeltaX[i]);
                                HullPosY.add(searchY);
                                HullPosZ.add(searchZ+dirDeltaZ[i]);
                            }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccEnergyBlock){
                                EnergyPosX.add(searchX+dirDeltaX[i]);
                                EnergyPosY.add(searchY);
                                EnergyPosZ.add(searchZ+dirDeltaZ[i]);
                            }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccFluidBlock){
                                FluidPosX.add(searchX+dirDeltaX[i]);
                                FluidPosY.add(searchY);
                                FluidPosZ.add(searchZ+dirDeltaZ[i]);
                            }else if(searchX + dirDeltaX[i]!=xCoord ||searchZ+dirDeltaZ[i]!=zCoord) {
                                posReset = 40;
                                return;
                            }
                        }
                        if(worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccMachineHull){
                            HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);
                        }else{
                            posReset = 40;
                            return;
                        }
                        if(worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccMachineHull){
                            HullPosX.add(searchX);
                            HullPosY.add(searchY+1);
                            HullPosZ.add(searchZ);
                        }else{
                            posReset = 40;
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
                            posReset = 40;
                            return;
                        }
                    } else {
                        posReset = 40;
                        return;
                    }
                    break;
                case 1:
                    int count = 0, px = 0, pz = 0;
                    for (int i = 0; i <= 3; i++) {
                        if (worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccTunnelBlock) {
                            if ((searchX + dirDeltaX[i]!=psearchX)||(searchZ + dirDeltaZ[i]!=psearchZ)) {
                                px = searchX + dirDeltaX[i];
                                pz = searchZ + dirDeltaZ[i];
                            }
                            count++;
                        }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccMachineHull){
                            HullPosX.add(searchX+dirDeltaX[i]);
                            HullPosY.add(searchY);
                            HullPosZ.add(searchZ+dirDeltaZ[i]);
                        }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccEnergyBlock){
                            EnergyPosX.add(searchX+dirDeltaX[i]);
                            EnergyPosY.add(searchY);
                            EnergyPosZ.add(searchZ+dirDeltaZ[i]);
                        }else if(worldObj.getBlock(searchX + dirDeltaX[i], searchY, searchZ + dirDeltaZ[i]) instanceof AccFluidBlock){
                            FluidPosX.add(searchX+dirDeltaX[i]);
                            FluidPosY.add(searchY);
                            FluidPosZ.add(searchZ+dirDeltaZ[i]);
                        }else if(searchX + dirDeltaX[i]!=xCoord ||searchZ+dirDeltaZ[i]!=zCoord){
                            posReset = 40;
                            return;
                        }

                    }
                    if(worldObj.getBlock(searchX, searchY+1, searchZ) instanceof AccMachineHull){
                        HullPosX.add(searchX);
                        HullPosY.add(searchY+1);
                        HullPosZ.add(searchZ);
                    }else{
                        posReset = 40;
                        return;
                    }
                    if(worldObj.getBlock(searchX, searchY-1, searchZ) instanceof AccMachineHull){
                        HullPosX.add(searchX);
                        HullPosY.add(searchY+1);
                        HullPosZ.add(searchZ);
                    }else{
                        posReset = 40;
                        return;
                    }
                    if (count!=2){
                        //System.out.println("Failed @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        posReset=40;
                        return;
                    }else {
                        TunnelPosX.add(searchX);
                        TunnelPosY.add(searchY);
                        TunnelPosZ.add(searchZ);
                        psearchX = searchX;
                        psearchY = searchY;
                        psearchZ = searchZ;
                        searchX = px;
                        searchZ = pz;
                        waitT=5;
                        //System.out.println("Running @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                    }
                    if(searchX==xCoord+dirDeltaX[dir] && searchZ==zCoord+dirDeltaZ[dir]){
                        stat=3;
                        searchX=0;
                        searchY=0;
                        searchZ=0;
                        psearchX=0;
                        psearchY=0;
                        psearchZ=0;
                        drag=calculateDrag(TunnelPosX,TunnelPosY,TunnelPosZ);
                        //System.out.println("Success");

                    }

                    break;
                case 3:
                    if(waitT>0){
                        waitT--;
                    }else {
                        waitT=5;
                        searchX=TunnelPosX.get(psearchX%TunnelPosX.size());
                        searchY=TunnelPosY.get(psearchX%TunnelPosY.size());
                        searchZ=TunnelPosZ.get(psearchX%TunnelPosZ.size());
                        if(!(worldObj.getBlock(searchX,searchY,searchZ)instanceof AccTunnelBlock)){
                            posReset=40;

                            //System.out.println("Failed1 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        searchX=HullPosX.get(psearchX%HullPosX.size());
                        searchY=HullPosY.get(psearchX%HullPosY.size());
                        searchZ=HullPosZ.get(psearchX%HullPosZ.size());
                        if(!(worldObj.getBlock(searchX,searchY,searchZ)instanceof AccMachineHull)){
                            posReset=40;
                            //System.out.println("Failed2 @"+String.valueOf(searchX)+","+String.valueOf(searchZ));
                        }
                        psearchX++;
                    }

                    //process acceleration here
                    double energyIn=0;
                    for (int i=0;i<EnergyPosX.size();i++){
                        if(worldObj.getTileEntity(EnergyPosX.get(i),EnergyPosY.get(i),EnergyPosZ.get(i)) instanceof TileAccEnergy) {
                            TileAccEnergy te = (TileAccEnergy) worldObj.getTileEntity(EnergyPosX.get(i), EnergyPosY.get(i), EnergyPosZ.get(i));
                            if (te != null) {
                                energyIn += te.getAllEnergy();
                            }
                        }else {
                            posReset=40;
                        }
                    }
                    //if(worldObj.getWorldTime()%10==0)System.out.println(String.valueOf(storedEnergy));
                    storedEnergy+=energyIn;
                    //if(worldObj.getWorldTime()%10==0)System.out.println(String.valueOf(calculateAcceleration(drag,energyIn, Config.kAcceleration,Config.kOverall)));
                    accProgress+=calculateAcceleration(drag,energyIn, Config.kAcceleration,Config.kOverall);
                    if (accProgress<0)accProgress=0;
                    if (accProgress>=100){
                        accProgress-=100;
                        lastConsumedEnergy=(int)storedEnergy;
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
                            posReset=40;
                        }
                    }

                    break;
            }
        }
    }
    private double calculateDrag(List<Integer> posListX,List<Integer> posListY,List<Integer> posListZ){
        double avgX=0,avgY=0,avgZ=0,avgDis=0,deltaDis=0;
        for (int i=0;i<posListX.size();i++){
            avgX+=posListX.get(i);
            avgY+=posListY.get(i);
            avgZ+=posListZ.get(i);
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
        return deltaDis*1000/avgDis/posListX.size()/posListX.size();
    }
    private double calculateAcceleration(double drag,double eu,double kAcceleration,double kOverall){
        return kOverall*(kAcceleration*Math.sqrt(eu)-drag);
    }
    public int[] tointArray(List<Integer> l){
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
        HullPosX=toArrayListInt(compound.getIntArray("hullx"));
        HullPosY=toArrayListInt(compound.getIntArray("hully"));
        HullPosZ=toArrayListInt(compound.getIntArray("hullz"));
        TunnelPosX=toArrayListInt(compound.getIntArray("tunnelx"));
        TunnelPosY=toArrayListInt(compound.getIntArray("tunnely"));
        TunnelPosZ=toArrayListInt(compound.getIntArray("tunnelz"));
        FluidPosX=toArrayListInt(compound.getIntArray("fluidx"));
        FluidPosY=toArrayListInt(compound.getIntArray("fluidy"));
        FluidPosZ=toArrayListInt(compound.getIntArray("fluidz"));
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
        lastConsumedEnergy=compound.getInteger("lastconsumedenergy");
        uuStored=compound.getInteger("uustored");
        drag=compound.getDouble("drag");
        accProgress=compound.getDouble("accprogress");

    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("dirMachine",dir);
        compound.setIntArray("hullx",tointArray(HullPosX));
        compound.setIntArray("hully",tointArray(HullPosY));
        compound.setIntArray("hullz",tointArray(HullPosZ));
        compound.setIntArray("energyx",tointArray(EnergyPosX));
        compound.setIntArray("energyy",tointArray(EnergyPosY));
        compound.setIntArray("energyz",tointArray(EnergyPosZ));
        compound.setIntArray("tunnelx",tointArray(TunnelPosX));
        compound.setIntArray("tunnely",tointArray(TunnelPosY));
        compound.setIntArray("tunnelz",tointArray(TunnelPosZ));
        compound.setIntArray("fluidx",tointArray(FluidPosX));
        compound.setIntArray("fluidy",tointArray(FluidPosY));
        compound.setIntArray("fluidz",tointArray(FluidPosZ));
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
        compound.setInteger("lastconsumedenergy",lastConsumedEnergy);
        compound.setDouble("drag",drag);
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
