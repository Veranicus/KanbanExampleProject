package sample.controllor;

import sample.delay.DelayUtil;
import sample.task.GeneralTask;

import java.util.concurrent.Callable;

public class Controllor implements Callable {


    public static boolean controlTask(GeneralTask generalTask) {
        if (DelayUtil.getRandomDoubleBetweenRange(1, 100) <= 5) {
            return false;
        } else return true;
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
