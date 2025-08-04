package dev.doublekekse.spring_boots.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import dev.doublekekse.spring_boots.registry.SpringBootsComponents;
import dev.doublekekse.spring_boots.registry.SpringBootsSoundEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MoverType;
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

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    void move(CallbackInfo ci) {
        var boots = getItemBySlot(EquipmentSlot.FEET);

        if (!boots.has(SpringBootsComponents.SPRING_BOOTS)) {
            return;
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
        if (isShiftKeyDown()) {
            return 0.8;
        }

        if (isJumping()) {
            return 1.2;
        }

        return 1;
    }
}
