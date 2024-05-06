package ace.actually.acrosperipherals.blocks;

import ace.actually.acrosperipherals.AutoTurtleCommand;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;

public class BootingPeripheral implements IPeripheral {

    private ITurtleAccess turtle;
    public BootingPeripheral(ITurtleAccess turtleAccess)
    {
        turtle=turtleAccess;
    }

    @Override
    public String getType() {

        return "booting";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof BootingPeripheral;
    }

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        System.out.println(turtle.getLevel());

        //we can technically do all the AutoTurtleCommand stuff directly here, but using a TurtleCommand means it
        //runs on the Main thread only
        turtle.executeCommand(new AutoTurtleCommand(computer.getID()));
    }



}
