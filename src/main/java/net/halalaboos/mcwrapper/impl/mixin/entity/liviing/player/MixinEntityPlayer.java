package net.halalaboos.mcwrapper.impl.mixin.entity.liviing.player;

import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.impl.mixin.entity.liviing.MivinEntityLiving;
import net.minecraft.entity.player.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.player.EntityPlayer.class)
public abstract class MixinEntityPlayer extends MivinEntityLiving implements Player {

	@Shadow public PlayerCapabilities capabilities;

}
