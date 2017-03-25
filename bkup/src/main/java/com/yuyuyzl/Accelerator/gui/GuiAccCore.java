package com.yuyuyzl.Accelerator.gui;

import com.yuyuyzl.Accelerator.container.ContainerAccCore;
import com.yuyuyzl.Accelerator.tile.TileAccCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * Created by yuyuyzl on 2017/2/28.
 */
public class GuiAccCore extends GuiContainer{

    public static final ResourceLocation guiTexture= new ResourceLocation("acceleratormod","/textures/blocks/GUIAccMainbg1.png");
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
                this.fontRendererObj.drawString("Tunnel not found.",27,25,0x00ffffff);
                break;
            case 1:
                this.fontRendererObj.drawString("Checking tunnel link.",27,25,0x00ffffff);
                break;
            case 3:
                this.fontRendererObj.drawString("Running.",27,25,0x00ffffff);
                this.fontRendererObj.drawString("Process : "+String.format("% 6.2f",((double)tileAccCore.accProgressInt)/100)+"%",27,35,0x00ffffff);
                this.fontRendererObj.drawString("Stored Energy : ",27,45,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 11d",tileAccCore.storedEnergyInt)+" EU",27,55,0x00ffffff);
                this.fontRendererObj.drawString("Stored UU Matter : ",27,65,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 11d",tileAccCore.uuStored)+" mB",27,75,0x00ffffff);
                this.fontRendererObj.drawString("Last Energy per UUM : ",27,85,0x00ffffff);
                this.fontRendererObj.drawString(String.format("% 11d",tileAccCore.lastConsumedEnergy)+" EU",27,95,0x00ffffff);

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
