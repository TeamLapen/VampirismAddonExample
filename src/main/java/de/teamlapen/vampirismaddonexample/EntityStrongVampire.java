package de.teamlapen.vampirismaddonexample;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.entity.goals.*;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.vampirism.entity.vampire.VampireBaseEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

/**
 * Just a vampire entity for testing/demonstration
 */
public class EntityStrongVampire extends VampireBaseEntity {
    public EntityStrongVampire(EntityType<EntityStrongVampire> entityType, World world) {
        super(entityType, world, true);
        hasArms = true;

    }


    public static AttributeModifierMap.MutableAttribute getAttributeBuilder() {
        return VampireBaseEntity.getAttributeBuilder().createMutableAttribute(Attributes.MAX_HEALTH, 100).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new BreakDoorGoal(this, difficulty -> difficulty == Difficulty.HARD));
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, CreatureEntity.class, 10.0F, 1.0D, 1.1D, VampirismAPI.factionRegistry().getPredicate(this.getFaction(), false, true, false, false, VReference.HUNTER_FACTION)));
        this.goalSelector.addGoal(2, new RestrictSunVampireGoal<>(this));
        this.goalSelector.addGoal(3, new FleeSunVampireGoal<>(this, 0.9, false));
        this.goalSelector.addGoal(3, new FleeGarlicVampireGoal(this, 0.9, false));
        this.goalSelector.addGoal(4, new AttackMeleeNoSunGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new BiteNearbyEntityVampireGoal<>(this));
        this.goalSelector.addGoal(7, new MoveToBiteableVampireGoal<>(this, 0.75));
        this.goalSelector.addGoal(8, new MoveThroughVillageGoal(this, 0.6D, true, 600, () -> false));
        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, PlayerEntity.class, 20.0F, 0.6F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, HunterBaseEntity.class, 17.0F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), true, false, true, false, null)));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), false, true, false, false, null)));//TODO maybe make them not attack hunters, although it looks interesting

    }
}
