package dev.doublekekse.spring_boots.registry;

import dev.doublekekse.spring_boots.SpringBoots;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;

public final class SpringBootsComponents {
    public static final DataComponentType<Unit> SPRING_BOOTS = register(
        DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC).cacheEncoding().build(),
        "spring_boots"
    );

    private static <A, T extends DataComponentType<A>> T register(T componentType, String path) {
        return Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            SpringBoots.id(path),
            componentType
        );
    }

    public static void register() {
    }
}
