package dev.doublekekse.spring_boots;

import dev.doublekekse.spring_boots.registry.SpringBootsComponents;
import dev.doublekekse.spring_boots.registry.SpringBootsItems;
import dev.doublekekse.spring_boots.registry.SpringBootsSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

public class SpringBoots implements ModInitializer {
    private static final String MOD_ID = "spring_boots";

    @Override
    public void onInitialize() {
        SpringBootsComponents.register();
        SpringBootsItems.register();
        SpringBootsSoundEvents.register();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.accept(SpringBootsItems.SPRING_BOOTS);
        });
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
