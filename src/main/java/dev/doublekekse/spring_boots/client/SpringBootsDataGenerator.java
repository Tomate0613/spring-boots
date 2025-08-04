package dev.doublekekse.spring_boots.client;

import dev.doublekekse.spring_boots.registry.SpringBootsItems;
import dev.doublekekse.spring_boots.registry.SpringBootsSoundEvents;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SpringBootsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
        pack.addProvider(EnglishLangProvider::new);
    }

    private static class EnglishLangProvider extends FabricLanguageProvider {
        protected EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(SpringBootsItems.SPRING_BOOTS, "Spring Boots");
            translationBuilder.add(SpringBootsSoundEvents.SPRING_BOOTS_JUMP, "Boing");
        }
    }

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerators) {
            itemModelGenerators.generateFlatItem(SpringBootsItems.SPRING_BOOTS, ModelTemplates.FLAT_ITEM);
        }
    }
}
