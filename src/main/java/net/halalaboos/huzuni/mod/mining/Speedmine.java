package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.mc.Reflection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

/**
 * Modifies player mine speed on all blocks.
 * */
public class Speedmine extends BasicMod {

	public final Value speed = new Value("Mine speed", 1F, 1F, 2F, "Mine speed modifier");
	public final Value breakPercent = new Value("Break Percent", 0F, 97F, 100F, 1F, "Block damage percent to break at");
	private final Toggleable noSlow = new Toggleable("No Slowdown", "Allows you to dig under yourself quicker.");

	private boolean digging = false;
	
	private float curBlockDamage = 0;

	private EnumFacing facing;

	private BlockPos position;

	public Speedmine() {
		super("Speedmine", "Mines blocks at a faster rate", Keyboard.KEY_V);
		this.setCategory(Category.MINING);
		setAuthor("Halalaboos");
		this.addChildren(speed, breakPercent, noSlow);
	}

	@Override
	protected void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	protected void onDisable() {
		huzuni.eventManager.removeListener(this);
	}
	
	@EventMethod
	public void onPacket(PacketEvent event) {
		if (event.type == PacketEvent.Type.SENT && mc.playerController != null && !mc.playerController.isInCreativeMode()) {
			if (event.getPacket() instanceof CPacketPlayerDigging) {
				CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
				if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
					digging = true;
					this.position = packet.getPosition();
					this.facing = packet.getFacing();
					this.curBlockDamage = 0;
				} else if (packet.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK || packet.getAction() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
					digging = false;
					this.position = null;
					this.facing = null;
				}
			}
		}
	}

	@EventMethod
	public void onUpdate(UpdateEvent event) {
		if (event.type == UpdateEvent.Type.PRE) {
			if (mc.playerController.isInCreativeMode()) {
				Reflection.setBlockHitDelay(0, mc.playerController);
				return;
			}
			if (digging) {
				IBlockState blockState = this.mc.world.getBlockState(position);
				float multiplier = noSlow.isEnabled() && mc.player.fallDistance <= 1F
						&& mc.player.fallDistance > 0 ? 5F : 1F;
				curBlockDamage += blockState.getPlayerRelativeBlockHardness(this.mc.player, this.mc.world, this.position) * (speed.getValue()) * multiplier;
				if (curBlockDamage >= breakPercent.getValue() / 100F) {
					mc.playerController.onPlayerDestroyBlock(position);
					mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.position, this.facing));
					curBlockDamage = 0F;
					digging = false;
				}
			}
		}
	}

}
