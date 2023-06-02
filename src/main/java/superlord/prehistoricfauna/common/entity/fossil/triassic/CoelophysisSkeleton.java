package superlord.prehistoricfauna.common.entity.fossil.triassic;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import superlord.prehistoricfauna.common.entity.PrehistoricEntity;
import superlord.prehistoricfauna.init.PFItems;

public class CoelophysisSkeleton extends PrehistoricEntity {
	private static final EntityDataAccessor<Boolean> SLAIN = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SEIZING = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SAUNTERING = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SLUMP = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STRIDE = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PUSHING = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> LOOKING = SynchedEntityData.defineId(CoelophysisSkeleton.class, EntityDataSerializers.BOOLEAN);

	public boolean isPushableState() {
		return this.entityData.get(PUSHING);
	}

	private void setPushable(boolean isPushable) {
		this.entityData.set(PUSHING, isPushable);
	}
	
	public boolean isLooking() {
		return this.entityData.get(LOOKING);
	}

	private void setLooking(boolean isLooking) {
		this.entityData.set(LOOKING, isLooking);
	}
	
	public boolean isSlain() {
		return this.entityData.get(SLAIN);
	}

	private void setSlain(boolean isSlain) {
		this.entityData.set(SLAIN, isSlain);
	}
	
	public boolean isSeizing() {
		return this.entityData.get(SEIZING);
	}

	private void setSeizing(boolean isSeizing) {
		this.entityData.set(SEIZING, isSeizing);
	}
	
	public boolean isSauntering() {
		return this.entityData.get(SAUNTERING);
	}

	private void setSauntering(boolean isSauntering) {
		this.entityData.set(SAUNTERING, isSauntering);
	}
	
	public boolean isSleeping() {
		return this.entityData.get(SLEEPING);
	}

	private void setSleeping(boolean isSleeping) {
		this.entityData.set(SLEEPING, isSleeping);
	}
	
	public boolean isSlumped() {
		return this.entityData.get(SLUMP);
	}

	private void setSlumped(boolean isSlumped) {
		this.entityData.set(SLUMP, isSlumped);
	}
	
	public boolean isStriding() {
		return this.entityData.get(STRIDE);
	}

	private void setStriding(boolean isStriding) {
		this.entityData.set(STRIDE, isStriding);
	}
	
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SLAIN, false);
		this.entityData.define(SEIZING, false);
		this.entityData.define(SAUNTERING, false);
		this.entityData.define(SLEEPING, false);
		this.entityData.define(SLUMP, false);
		this.entityData.define(STRIDE, false);
		this.entityData.define(PUSHING, false);
		this.entityData.define(LOOKING, false);
	}
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsSlain", this.isSlain());
		compound.putBoolean("IsSeizing", this.isSeizing());
		compound.putBoolean("IsSauntering", this.isSauntering());
		compound.putBoolean("IsSleeping", this.isSleeping());
		compound.putBoolean("IsSlumping", this.isSlumped());
		compound.putBoolean("IsStriding", this.isStriding());
		compound.putBoolean("IsPushable", this.isPushableState());
		compound.putBoolean("IsLooking", this.isLooking());
	}
	
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound); 
		this.setSlain(compound.getBoolean("IsSlain"));
		this.setSeizing(compound.getBoolean("IsSeizing"));
		this.setSauntering(compound.getBoolean("IsSauntering"));
		this.setSleeping(compound.getBoolean("IsSleeping"));
		this.setSlumped(compound.getBoolean("IsSlumping"));
		this.setStriding(compound.getBoolean("IsStriding"));
		this.setPushable(compound.getBoolean("IsPushable"));
		this.setLooking(compound.getBoolean("IsLooking"));
	}
	
	public CoelophysisSkeleton(EntityType<? extends CoelophysisSkeleton> type, Level worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D);
	}

	protected int getExperiencePoints(Player player) {
		return 0;
	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return this.isPushableState();
	}
	
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
	    if (itemstack.getItem() == PFItems.GEOLOGY_HAMMER.get()) {
	    	if (!this.isSauntering() && !this.isSeizing() && !this.isSlain() && !this.isSleeping() && !this.isSlumped() && !this.isStriding() && !player.isShiftKeyDown()) {
	    		this.setSauntering(true);
	    	} else if (this.isSauntering() && !player.isShiftKeyDown()) {
	    		this.setSauntering(false);
	    		this.setSeizing(true);
	    	} else if (this.isSeizing() && !player.isShiftKeyDown()) {
	    		this.setSeizing(false);
	    		this.setStriding(true);
	    	} else if (this.isStriding() && !player.isShiftKeyDown()) {
	    		this.setStriding(false);
	    		this.setSleeping(true);
	    	} else if (this.isSleeping() && !player.isShiftKeyDown()) {
	    		this.setSleeping(false);
	    		this.setSlumped(true);
	    	} else if (this.isSlumped() && !player.isShiftKeyDown()) {
	    		this.setSlumped(false);
	    		this.setSlain(true);
	    	} else if (this.isSlain() && !player.isShiftKeyDown()) {
	    		this.setSlain(false);
	    	} else if (player.isShiftKeyDown() && !this.isPushableState() && !this.isLooking()) {
	    		this.setPushable(true);
				player.displayClientMessage(new TranslatableComponent("entity.prehistoricfauna.skeleton.pushable"), true);
	    	} else if (player.isShiftKeyDown() && this.isPushableState()) {
	    		this.setPushable(false);
	    		this.setLooking(true);
				player.displayClientMessage(new TranslatableComponent("entity.prehistoricfauna.skeleton.rotating"), true);
	    	} else if (player.isShiftKeyDown() && this.isLooking()) {
	    		this.setLooking(false);
				player.displayClientMessage(new TranslatableComponent("entity.prehistoricfauna.skeleton.neutral"), true);
	    	}
	    }
        return super.mobInteract(player, hand);
	}

	protected void doPush(Entity entityIn) {
	}

	private void playBrokenSound() {
		this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.SKELETON_HURT, this.getSoundSource(), 1.0F, 1.0F);
	}

	private void playParticles() {
		if (this.level instanceof ServerLevel) {
			((ServerLevel)this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BONE_BLOCK.defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
		}
	}


	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof Player) {
			this.playBrokenSound();
			this.playParticles();
			Player player = (Player)source.getDirectEntity();
			if (!player.isCreative()) {
				this.spawnFossil(source);
			}
			this.remove(RemovalReason.KILLED);
		}
		return false;
	}

	public boolean canBeHitWithPotion() {
		return false;
	}

	public void onKillCommand() {
		this.kill();
	}

	private void spawnFossil(DamageSource p_213815_1_) {
	      Block.popResource(this.level, this.blockPosition(), new ItemStack(PFItems.COELOPHYSIS_SKELETON.get()));
	}
	
	static class LookAtPlayerGoal extends net.minecraft.world.entity.ai.goal.LookAtPlayerGoal {

		CoelophysisSkeleton entity;
		
		public LookAtPlayerGoal(CoelophysisSkeleton entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
			super(entityIn, watchTargetClass, maxDistance);
			entity = entityIn;
		}
		
		public boolean canUse() {
			if (entity.isLooking()) {
				return super.canUse();
			} else {
				return false;
			}
		}
		
		public boolean canContinueToUse() {
			return super.canContinueToUse() && entity.isLooking();
		}
		
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
		return null;
	}
	
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(PFItems.COELOPHYSIS_SKELETON.get());
	}

}