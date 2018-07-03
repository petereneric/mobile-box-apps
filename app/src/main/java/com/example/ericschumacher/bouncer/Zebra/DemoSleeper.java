package com.example.ericschumacher.bouncer.Zebra;

/**
 * Created by Eric on 03.07.2018.
 */

package com.zebra.connectivitydemo;

public class DemoSleeper {

    private DemoSleeper() {

    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
