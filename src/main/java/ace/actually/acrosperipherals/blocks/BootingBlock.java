package ace.actually.acrosperipherals.blocks;

import dan200.computercraft.shared.media.items.DiskItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BootingBlock extends Block {
    public BootingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(level instanceof ServerLevel serverLevel)
        {
            if(player.getItemInHand(interactionHand).getItem() instanceof DiskItem diskItem)
            {

                try {
                    SeekableByteChannel startup = diskItem.createDataMount(player.getItemInHand(interactionHand), serverLevel).openForRead("startup.lua");

                    ByteBuffer byteBuffer = ByteBuffer.allocate((int) startup.size());
                    startup.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array()));
                    startup.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
