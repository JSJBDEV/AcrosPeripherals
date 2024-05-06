package ace.actually.acrosperipherals;

import ace.actually.acrosperipherals.blocks.BootingBlock;
import ace.actually.acrosperipherals.blocks.BootingTurtleUpgrade;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.mixin.registry.sync.RegistriesAccessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcrosPeripherals implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("acrosperipherals");
    public static final String ID = "acrosperipherals";

    @Override
    public void onInitialize() {
        registerBlocks();
        registerItems();
        TurtleUpgradeSerialiser<BootingTurtleUpgrade> bootingTurtleUpgradeSerialiser = ModTurtleUpgradeSerialisers.BOOTING_TURTLE_UPGRADE_SERIALISER;
    }

    public static final BootingBlock BOOTING_BLOCK = new BootingBlock(BlockBehaviour.Properties.of());
    private void registerBlocks()
    {
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation(ID,"booting_block"),BOOTING_BLOCK);
    }

    private void registerItems()
    {
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation(ID,"booting_block"),new ItemNameBlockItem(BOOTING_BLOCK,new Item.Properties()));
    }



    public static final class ModTurtleUpgradeSerialisers {
        private static <T extends TurtleUpgradeSerialiser<?>> T register(ResourceLocation name, T serialiser) {
            @SuppressWarnings("unchecked")
            var registry = (Registry<? super TurtleUpgradeSerialiser<?>>) RegistriesAccessor.getROOT().get(TurtleUpgradeSerialiser.registryId().location());
            if (registry == null) throw new IllegalStateException("ComputerCraft has not initialised yet?");
            Registry.register(registry, name, serialiser);
            return serialiser;
        }
        public static final TurtleUpgradeSerialiser<BootingTurtleUpgrade> BOOTING_TURTLE_UPGRADE_SERIALISER = register(
                new ResourceLocation(ID,"booting"),
                TurtleUpgradeSerialiser.simpleWithCustomItem(BootingTurtleUpgrade::new)
        );
    }
}
