package dev.levkush.wurstplusfour.setting.type;

import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.setting.Setting;

import java.util.function.Predicate;

public class DoubleSetting extends Setting<Double> {

    private final double min;
    private final double max;

    public DoubleSetting(String name, Double value, Double min, Double max, Hack parent) {
        super(name, value, parent);
        this.min = min;
        this.max = max;
    }

    public DoubleSetting(String name, Double value, Double min, Double max, ParentSetting parent) {
        super(name, value, parent);
        this.min = min;
        this.max = max;
    }

    public DoubleSetting(String name, Double value, Double min, Double max, Hack parent, Predicate shown) {
        super(name, value, parent, shown);
        this.min = min;
        this.max = max;
    }

    public DoubleSetting(String name, Double value, Double min, Double max, ParentSetting parent, Predicate shown) {
        super(name, value, parent, shown);
        this.min = min;
        this.max = max;
    }

    public Double getValue() {
        return this.value;
    }

    public Double getMax() {
        return this.max;
    }

    public Double getMin() {
        return this.min;
    }

    public double getNumber() {
        return this.value;
    }

    public void setNumber(double value) {
        this.value = value;
        if (this.getParent().isEnabled())
          this.getParent().onSettingChange();
    }

    public Float getAsFloat(){
        return (float) (double) this.value;
    }

    public double getMaximumValue() {
        return this.max;
    }

    public double getMinimumValue() {
        return this.min;
    }

    public int getPrecision() {
        return 2;
    }


    @Override
    public String getType() {
        return "double";
    }
}
