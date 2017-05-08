package com.yuyuyzl.Accelerator;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/7.
 */
public class planner {
    //final static double r=20.5;
    public static class BlockPos{
        int x,y,z;
        public BlockPos(int x,int y,int z){
            this.x=x;
            this.y=y;
            this.z=z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public double distanceSq(double px,double py,double pz){
            return (px-x)*(px-x)+(py-y)*(py-y)+(pz-z)*(pz-z);
        }
    }

    public static class Config{
        public static double kAcceleration=0.0025;
        public static double kOverall=6;
        public static double kFail=0.004;
        public static double kStabilizer=0.5;
        public static double kTime=10;
        public static double kDrag=0.01;

    }

    static int[][] a=new int[100][100];
    static List<BlockPos> posList=new ArrayList<BlockPos>();
    public static void add(BlockPos p){
        if (!posList.contains(p)){
            posList.add(p);
        }
    }
    public static void main(String[] args) {
        for(double r=2;r<=32;r+=1) {
            posList.clear();
            a=new int[100][100];
            for (double i = 0.00005; i <= Math.PI * 2; i += 0.001) {
                a[(int) Math.round(Math.cos(i) * r+49)][(int) Math.round(Math.sin(i) * r+49)] = 1;
            }
            for (int i = (int)(49-r); i <= (int)(50+r); i++) {
                int s = 0;
                for (int j = (int)(49-r); j <= (int)(50+r); j++) {
                    //System.out.print(a[i][j]==0?"  ":"[]");
                    //s += a[i][j];
                    if (a[i][j] == 1) {
                        add(new BlockPos(i, 0, j));
                    }

                }
                //System.out.println();
            }
            AccProperty property=calculateDrag(posList);
            double min=1e15,minE=0;double min2=1e15,minE2=0;int n=0;
            for (double i=32;i<=32768;i+=32){
                double a=calculateAcceleration(property.drag,i, Config.kAcceleration,Config.kOverall,0,property.failrate,0);
                double a2=calculateAcceleration(property.drag,i/Config.kTime, Config.kAcceleration,Config.kOverall*Config.kTime,0,0,0);
                double t=100/a;
                double totEU=t*i;
                double t2=100/a2;
                double totEU2=t2*i;
                if(totEU>0 && totEU<min){
                    min=totEU;
                    minE=i;
                }
                if(totEU2>0 && totEU2<min2){
                    min2=totEU2;
                    minE2=i;
                }


            }
            double rt=property.failrate;
            while(rt>0){
                rt*=Config.kStabilizer;
                rt-=0.01;
                n++;
            }
            System.out.println(String.format("R = %d , Drag = %.5f , Fail = %.3f , Best = %.0f at %.0f EU/t , Best(NEED %d) = %.0f at %.0f EU/t",(int)r,property.drag,property.failrate*100,min ,minE,n,min2,minE2));

            //System.out.println(posList.size());
        }
    }

    private static double calculateAcceleration(double drag,double eu,double kAcceleration,double kOverall,int numStabilizer,double failrate,double kStabilizer){
        double r=failrate;

        //if(worldObj.getWorldTime()%20==0)System.out.println(String.valueOf(r));
        return kOverall*(kAcceleration*Math.sqrt(eu)*(1-(r>0?r:0))-drag);
    }

    public static class AccProperty{
        public double drag;
        public double failrate;
        public AccProperty(double drag,double failrate){
            this.drag=drag;
            this.failrate=failrate;
        }
    }
    private static AccProperty calculateDrag(List<BlockPos> posList){
        double avgX=0,avgY=0,avgZ=0,avgDis=0,deltaDis=0;
        for(BlockPos p:posList){
            avgX+=p.getX();
            avgY+=p.getY();
            avgZ+=p.getZ();
        }
        avgX/=posList.size();
        avgY/=posList.size();
        avgZ/=posList.size();
        for(BlockPos p:posList)avgDis+=Math.sqrt(p.distanceSq(avgX,avgY,avgZ));
        avgDis/=posList.size();
        //for(BlockPos p:posList)deltaDis+=Math.pow((p.distanceSq(avgX,avgY,avgZ)-avgDis-1)<0?0:(p.distanceSq(avgX,avgY,avgZ)-avgDis-1),2);
        for(BlockPos p:posList){
            double d=Math.pow((Math.sqrt(p.distanceSq(avgX,avgY,avgZ))-avgDis),2);
            //System.out.println(String.valueOf(d));
            //d=(d-0.5<0)?0:d-0.5;
            deltaDis+=d;
        }
        deltaDis=Math.sqrt(deltaDis);
        double failrate=avgDis*avgDis*Config.kFail;
        double drag=deltaDis*1000/avgDis/posList.size()/posList.size();
        //System.out.println(String.valueOf(deltaDis*1000/avgDis/posList.size()/posList.size()));
        return new AccProperty(drag+Config.kDrag,failrate);
    }
}
