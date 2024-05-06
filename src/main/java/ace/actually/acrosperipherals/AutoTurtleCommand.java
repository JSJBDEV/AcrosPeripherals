package ace.actually.acrosperipherals;

import com.google.common.io.ByteStreams;
import dan200.computercraft.api.filesystem.MountConstants;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommand;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.core.filesystem.FileSystemException;
import dan200.computercraft.core.filesystem.FileSystemWrapper;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.computer.core.ServerComputerRegistry;
import dan200.computercraft.shared.computer.core.ServerContext;
import dan200.computercraft.shared.media.items.DiskItem;
import net.minecraft.server.level.ServerLevel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AutoTurtleCommand implements TurtleCommand {
    private int theId;
    public AutoTurtleCommand(int id)
    {
        theId=id;
    }

    @Override
    public TurtleCommandResult execute(ITurtleAccess turtle) {

        if(turtle.getLevel() instanceof ServerLevel serverLevel)
        {
            ServerComputerRegistry manager = ServerContext.get(serverLevel.getServer()).registry();
            ServerComputer computer = manager.getComputers().stream().filter(a->a.getID()==theId).findFirst().get();
            try {
                //an example of writing files to the filesystem on a turtleCommand
                if(!computer.getAPIEnvironment().getFileSystem().exists("/startup/go.lua"))
                {

                    FileSystemWrapper<SeekableByteChannel> file = computer.getAPIEnvironment().getFileSystem().openForWrite("/startup/go.lua", MountConstants.WRITE_OPTIONS);
                    file.get().write(ByteBuffer.wrap("term.write(\"Autorun Enabled!\")".getBytes(StandardCharsets.UTF_8)));
                    file.close();
                    computer.reboot();
                }

            } catch (FileSystemException | IOException e) {
                throw new RuntimeException(e);
            }

        }

        return TurtleCommandResult.success();
    }
}
