package com.yuyuyzl.Accelerator.gui;

import com.yuyuyzl.Accelerator.container.ContainerAccCore;
import com.yuyuyzl.Accelerator.tile.TileAccCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by yuyuyzl on 2017/2/28.
 */
public class GuiAccCore extends GuiContainer{

    public static final ResourceLocation guiTexture= new ResourceLocation("acceleratormod","textures/blocks/GUIAccMainbg.png");
    private TileAccCore tileAccCore;


    public GuiAccCore(InventoryPlayer invPlayer, TileAccCore tile) {
        super(new ContainerAccCore(invPlayer, tile));
        this.tileAccCore = tile;

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
        switch (tileAccCore.stat){
            case 0:
                this.fontRendererObj.drawString("P.A.O.S. V1.0.",27,25,0x00ffffff);
                this.fontRendererObj.drawString("Now Loading...",27,45,0x00ffffff);
                break;
            case 1:
                this.fontRendererObj.drawString("Checking tunnel link.",27,25,0x00ffffff);
                break;
            case 3:
                this.fontRendererObj.drawString("Running.",27,25,0x00ffffff);
                this.fontRendererObj.drawString("Process : "+String.format("% 6.2f",((double)tileAccCore.accProgressInt)/100)+"%",27,35,0x00ffffff);
                this.fontRendererObj.drawString("Stored UU Matter : ",27,45,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 16d",tileAccCore.uuStored)+" mB",27,55,0x00ffffff);
                this.fontRendererObj.drawString("Used Energy : ",27,65,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 16d",tileAccCore.guiField1 +tileAccCore.guiField2 *32768)+" EU",27,75,0x00ffffff);
                this.fontRendererObj.drawString("Last Energy per UUM : ",27,85,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 16d",tileAccCore.guiField3 +tileAccCore.guiField4 *32768)+" EU",27,95,0x00ffffff);
                this.fontRendererObj.drawString("System Info : ",27,105,0x00ffffff);
                this.fontRendererObj.drawString(String.format("   Drag = % 4.4f",tileAccCore.dragUI),27,115,0x00ffffff);
                this.fontRendererObj.drawString(String.format("   Fail = % 4.1f",tileAccCore.failrateUI*100)+"%",27,125,0x00ffffff);
                break;
            case 4:
                this.fontRendererObj.drawString("Failed.",27,25,0x00ffffff);
                this.fontRendererObj.drawString("Check the block below",27,45,0x00ffffff);
                this.fontRendererObj.drawString("and it's surroundings,",27,55,0x00ffffff);
                this.fontRendererObj.drawString("Then replace the core.",27,65,0x00ffffff);
                this.fontRendererObj.drawString("X = "+String.format("%d",tileAccCore.guiField1),27,85,0x00ffffff);
                this.fontRendererObj.drawString("Y = "+String.format("%d",tileAccCore.guiField2),27,95,0x00ffffff);
                this.fontRendererObj.drawString("Z = "+String.format("%d",tileAccCore.guiField3),27,105,0x00ffffff);
                break;
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
