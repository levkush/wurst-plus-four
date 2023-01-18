package dev.levkush.wurstplusfour.plugin;

/**
 * @author Madmegsox1
 * @since 06/07/2021
 */

public interface Plugin {
    default void init(){
    }

    default String name(){
        return getClass().getSimpleName();
    }
}
