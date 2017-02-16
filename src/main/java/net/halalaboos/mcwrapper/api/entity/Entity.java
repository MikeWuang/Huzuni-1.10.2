package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.util.Identifiable;
import net.halalaboos.mcwrapper.api.util.Nameable;
import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;
import net.halalaboos.mcwrapper.api.world.Fluid;
import net.halalaboos.mcwrapper.api.world.World;

public interface Entity extends Identifiable, Nameable {

	/**
	 * The World the Entity is currently in.
	 */
	World getWorld();

	/**
	 * The position of the Entity.
	 */
	Vector3d getPosition();

	/**
	 * The previous position of the Entity.
	 */
	Vector3d getPreviousPosition();

	/**
	 * The velocity of the Entity (e.g. motionX, motionY, motionZ)
	 */
	Vector3d getVelocity();

	/**
	 * The rotation of the Entity.
	 */
	Rotation getRotation();

	/**
	 * Whether or not the Entity is dead - if it is, then it will be removed from the World.
	 */
	boolean isDead();

	/**
	 * The distance to the entity.
	 */
	double getDistanceTo(Entity entity);

	/**
	 * The distance to the position.
	 */
	double getDistanceTo(Vector3d pos);

	/**
	 * The remaining time the Entity is immune to damage.
	 */
	int getHurtResistantTime();

	/**
	 * Whether or not the Entity is inside of the specified fluid.
	 */
	boolean isInFluid(Fluid fluid);

	boolean isInsideOfWater();

	float getWidth();
	float getHeight();
	float getEyeHeight();

	float getFallDistance();
	void setFallDistance(float fallDistance);

	float getStepHeight();
	void setStepHeight(float stepHeight);

	int getId();
}
