package net.halalaboos.huzuni.mod.combat;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.minecraft.network.play.client.CPacketUseEntity;

/**
 * Attempts to force criticals by jumping.
 * */
public class Criticals extends BasicMod {
	
	public Criticals() {
		super("Criticals", "Automagically critical with each hit");
		this.setCategory(Category.COMBAT);
		setAuthor("brudin");
	}
	
	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}
	
	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	@EventMethod
	public void onPacket(PacketEvent event) {
		if (event.type == PacketEvent.Type.SENT) {
			if (event.getPacket() instanceof CPacketUseEntity) {
				CPacketUseEntity packetUseEntity = (CPacketUseEntity)event.getPacket();
				if (packetUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
					if (shouldCritical()) {
						doCrit();
					}
				}
			}
		}
	}
	
	private void doCrit() {
		boolean preGround = mc.player.onGround;
		mc.player.onGround = false;
		mc.player.jump();
		mc.player.onGround = preGround;
	}
	
	private boolean shouldCritical() {
		return !mc.player.isInWater() && mc.player.onGround && !mc.player.isOnLadder();
	}
	
}
