package dev.levkush.wurstplusfour.event.events;

import dev.levkush.wurstplusfour.event.processor.Event;

/**
 * @author Madmegsox1
 * @since 05/06/2021
 */

public class TestEvent extends Event {
    public long startTime;
    public TestEvent(){
        startTime = System.nanoTime();
    }
}
