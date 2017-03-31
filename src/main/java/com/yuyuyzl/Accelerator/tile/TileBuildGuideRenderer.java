package com.yuyuyzl.Accelerator.tile;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by yuyuyzl on 2017/3/29.
 */
public class TileBuildGuideRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation laserTexture= new ResourceLocation("acceleratormod","textures/blocks/laser.png");

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double d, double d1, double d2, float f) {


        TileBuildGuide tile=(TileBuildGuide)p_147500_1_;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_BLEND);
        //bindTexture(laserTexture);
        int x=tile.xCoord;
        int y=tile.yCoord;
        int z=tile.zCoord;


        //// TODO: 2017/3/29  draw lines
        GL11.glPushMatrix();
        GL11.glTranslated(d+0.5, d1+0.1, d2+0.5);
        GL11.glColor4d(0.2,0.7,1,1);
        int radius=tile.radius;
        //GL11.glRotatef((float) (Math.atan2((cx+7.5-x), (cz+7.5-z))*180/3.1415)+90, 0, 1, 0);
        //GL11.glRotatef((float) (-Math.asin(0.8/toCenter)*180/3.1415), 0, 0, 1);

        for (double i=0;i<360;i+=1){
            GL11.glRotated(1,0,1,0);
            GL11.glTranslated(radius, 0, 0);
            GL11.glRotated(91,0,1,0);
            Render.renderAABB(AxisAlignedBB.getBoundingBox(3.14/180*radius, -0.03, -0.03, 0, 0.03, 0.03));

            GL11.glTranslated(0, 0.8, 0);

            Render.renderAABB(AxisAlignedBB.getBoundingBox(3.14/180*radius, -0.03, -0.03, 0, 0.03, 0.03));

            GL11.glTranslated(0, -0.8, 0);
            GL11.glRotated(-91,0,1,0);
            GL11.glTranslated(-radius, 0, 0);

        }


        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_FOG);

    }
}
