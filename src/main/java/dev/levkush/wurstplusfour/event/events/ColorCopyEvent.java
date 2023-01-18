package dev.levkush.wurstplusfour.event.events;

import dev.levkush.wurstplusfour.event.EventStage;
import dev.levkush.wurstplusfour.gui.component.Component;
import dev.levkush.wurstplusfour.util.elements.Colour;

public class ColorCopyEvent extends EventStage {
    public Colour copedColor;
    public Component colorComponent;
    public EventType eventType;

    public ColorCopyEvent(Colour val, Component component, EventType eventType){
        this.copedColor = val;
        this.colorComponent = component;
        this.eventType = eventType;
    }

    public enum EventType {
        COPY,
        PAST
    }
}
