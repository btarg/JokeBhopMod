package com.icrazyblaze.boppinhops;

import com.icrazyblaze.boppinhops.audio.MovingSoundBhops;
import com.icrazyblaze.boppinhops.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import squeek.quakemovement.ModConfig;
import squeek.quakemovement.QuakeClientPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SideOnly(Side.CLIENT)
public class DetectBhops {

    public static final SoundEvent bhopSoundEvent = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "bhopsong"));
    public static boolean isYeeting = false;
    public static boolean isPlayingSound = false;
    private static final QuakeClientPlayer client = new QuakeClientPlayer();
    private static final ModConfig config = new ModConfig();

    public final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void doEveryTick(TickEvent.ServerTickEvent event) {

        if (event.phase == TickEvent.Phase.END && getEnabled()) {

            if (player() == null || player().isElytraFlying() || player().isSpectator() || player().isInWater()) {
                return;
            }

            double yeetThreshold = 0.35f;

            if (isJumping() && getSpeed() > yeetThreshold && !isYeeting) {

                Main.logger.info("Player has started bunnyhopping");
                isYeeting = true;

            } else if (getSpeed() < yeetThreshold - 0.06f && isYeeting) {

                Main.logger.info("Player has stopped bunnyhopping");
                isYeeting = false;

            }

            if (isYeeting && !isPlayingSound) {
                mc.getSoundHandler().playSound(new MovingSoundBhops(player()));
            }
        }

    }

    public static EntityPlayer player() {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    private boolean isJumping() {

        try {

            // Use reflection to get private method
            Method isJumpMethod = client.getClass().getDeclaredMethod("isJumping", EntityPlayer.class);
            isJumpMethod.setAccessible(true);

            boolean invoke = (boolean) isJumpMethod.invoke(null, player());
            return invoke;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private static double getSpeed() {

        try {

            Method speedMethod = client.getClass().getDeclaredMethod("getSpeed", EntityPlayer.class);
            speedMethod.setAccessible(true);

            double invoke = (double) speedMethod.invoke(null, player());
            return invoke;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static boolean getEnabled() {

        try {

            Field enabledField = config.getClass().getDeclaredField("ENABLED");
            enabledField.setAccessible(true);

            boolean invoke = enabledField.getBoolean(null);
            return invoke;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

