package dev.doublekekse.spring_boots.mixin;

import com.mojang.authlib.GameProfile;
import dev.doublekekse.spring_boots.registry.SpringBootsComponents;
import dev.doublekekse.spring_boots.registry.SpringBootsSoundEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow
    public abstract boolean isShiftKeyDown();

    @Unique
    private int ticksSinceJump;
    @Unique
    private boolean wasJumping;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    void move(CallbackInfo ci) {
        var boots = getItemBySlot(EquipmentSlot.FEET);

        if (!boots.has(SpringBootsComponents.SPRING_BOOTS)) {
            return;
        }

        ticksSinceJump++;

        if (isJumping() && !wasJumping) {
            ticksSinceJump = 0;
            wasJumping = true;
        } else if (!isJumping() && wasJumping) {
            wasJumping = false;
        }

        var delta = getDeltaMovement();

        var collision = ((EntityInvoker) this).invokeCollide(delta);

        if (delta.y != collision.y) {
            var apply = new Vec3(0, -2 * delta.y * multiplier(), 0);

            if (apply.y > 0.3) {
                addDeltaMovement(apply);
                playSound(SpringBootsSoundEvents.SPRING_BOOTS_JUMP, (float) Math.min(apply.y * .2f, 2), (float) (1 + ((apply.y - .2) * 0.01)));
            }
        }
    }

    @Unique
    private double multiplier() {
        if (wasJumping) {
            return Mth.clampedMap(Math.abs(ticksSinceJump - 2), 0, 40, 1.3, .8);
        }

        if (isShiftKeyDown()) {
            return 0.8;
        }


        return 1;
    }
}
