package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.event.events.PerspectiveEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.event.processor.EventPriority;
import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.hack.Hack;

/**
 * @author SankuGG
 * @since 01/05/2021
 *  -> src from prism
 */

@Hack.Registration(name = "Aspect", description = "Does aspect shit", category = Hack.Category.RENDER, isListening = false)
public class Aspect extends Hack{
    DoubleSetting aspect = new DoubleSetting("Aspect",  mc.displayWidth / mc.displayHeight + 0.0, 0.0 ,3.0, this);

    @CommitEvent(priority = EventPriority.LOW)
    public void onPerspectiveEvent(PerspectiveEvent event){
        event.setAspect(aspect.getValue().floatValue());
    }
}
