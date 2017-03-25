package com.yuyuyzl.Accelerator.container;

import com.yuyuyzl.Accelerator.tile.TileAccCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import java.util.List;

/**
 * Created by yuyuyzl on 2017/2/27.
 */
public class ContainerAccCore extends Container{

    private TileAccCore tile;

    public ContainerAccCore(InventoryPlayer player,TileAccCore tile){
        this.tile=tile;



    }

    @Override
    public void addCraftingToCrafters(ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(ICrafting iCrafting:(List<ICrafting>)this.crafters){
            iCrafting.sendProgressBarUpdate(this,0,tile.stat);
            iCrafting.sendProgressBarUpdate(this,1,(int)tile.storedEnergy);
            iCrafting.sendProgressBarUpdate(this,2,(int)(tile.accProgress*100));
            iCrafting.sendProgressBarUpdate(this,3,tile.uuStored);
            iCrafting.sendProgressBarUpdate(this,4,tile.lastConsumedEnergy);
        }
    }


    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id){
            case 0:tile.stat=data;
                break;
            case 1:tile.storedEnergyInt=data;
                break;
            case 2:tile.accProgressInt=data;
                break;
            case 3:tile.uuStored=data;
                break;
            case 4:tile.lastConsumedEnergy=data;
                break;
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }
}
