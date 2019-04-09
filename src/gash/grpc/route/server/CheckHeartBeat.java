package gash.grpc.route.server;

import java.util.TimerTask;
import java.util.Map;

public abstract class CheckHeartBeat extends TimerTask{

    @Override
    public void run() {
        // Do Nothing Here
        runHeartBeat();
    }

    protected abstract void runHeartBeat();
}