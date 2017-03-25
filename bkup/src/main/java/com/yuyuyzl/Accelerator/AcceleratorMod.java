package com.yuyuyzl.Accelerator;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AcceleratorMod.MODID, version = AcceleratorMod.VERSION,name = AcceleratorMod.NAME)
public class AcceleratorMod
{
    public static final String MODID = "acceleratormod";
    public static final String VERSION = "0.01";
    public static final String NAME= "Project Accelerator";

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="com.yuyuyzl.Accelerator.ClientProxy",serverSide = "com.yuyuyzl.Accelerator.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
    @Mod.Instance(AcceleratorMod.MODID)
    public static AcceleratorMod instance;
}
