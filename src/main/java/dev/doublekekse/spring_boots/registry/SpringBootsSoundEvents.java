package dev.doublekekse.spring_boots.registry;

import dev.doublekekse.spring_boots.SpringBoots;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class SpringBootsSoundEvents {
    public static final SoundEvent SPRING_BOOTS_JUMP = register("spring_boots_jump");

    private static SoundEvent register(String path) {
        var id = SpringBoots.id(path);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {

    }
}
