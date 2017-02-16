package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent.Type;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.minecraft.block.material.Material;

/**
 * Allows the player to glide downward.
 * */
public class Glide extends BasicMod {
	
	public Glide() {
		super("Glide", "Allows an individual to glide down like an angel");
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
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
	public void onUpdate(UpdateEvent event) {
		if (mc.player.motionY <= -0.0315F && !mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder() && !mc.player.isInsideOfMaterial(Material.LAVA) && !mc.player.isCollidedVertically && event.type == Type.PRE) {
			mc.player.motionY = -0.0315F;
			mc.player.jumpMovementFactor *= 1.21337f;
		}
	}

}
