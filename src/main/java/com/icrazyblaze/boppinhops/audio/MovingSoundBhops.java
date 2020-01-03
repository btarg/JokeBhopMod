package com.icrazyblaze.boppinhops.audio;

import com.icrazyblaze.boppinhops.DetectBhops;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundBhops extends MovingSound {
    private final EntityPlayer player;

    public MovingSoundBhops(EntityPlayer player) {
        super(DetectBhops.bhopSoundEvent, SoundCategory.PLAYERS);
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
        this.player = player;
    }

    public void update() {
        DetectBhops.isPlayingSound = !this.isDonePlaying();
        if (DetectBhops.isYeeting) {
            this.volume = 0.5f;
            this.pitch = 1.0f;
            this.xPosF = (float) this.player.posX;
            this.yPosF = (float) this.player.posY + 20;
            this.zPosF = (float) this.player.posZ;
        } else {
            this.donePlaying = true;
        }

    }
}