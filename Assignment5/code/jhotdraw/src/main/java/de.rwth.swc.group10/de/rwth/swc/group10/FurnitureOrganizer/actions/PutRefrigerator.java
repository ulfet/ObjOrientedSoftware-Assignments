package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TEXT;

public class PutRefrigerator extends TextAreaFigure {
    public static final String ID = "furnisher.put.refrigerator";
    public final String furnitureType = "Refrigerator";

    public static int counter = 0;

    public PutRefrigerator() {
        this(  Integer.toString(getCounter())     );
        System.out.println("PutRefrigerator constructor called");
        setFontSize(10);
    }

    public static void incrementCounter() {
        PutRefrigerator.counter += 1;
    }

    public static int getCounter() {
        return PutRefrigerator.counter;
    }

    public PutRefrigerator(String text) {
        setText(text);
    }

    @Override
    public String getText() {
        return get(TEXT);
    }

    @Override
    public TextAreaFigure clone() {
        incrementCounter();
        System.out.println( furnitureType + " clone" + "Called");

        PutRefrigerator that = (PutRefrigerator) super.clone();
        that.setText(furnitureType + ":" + Integer.toString(getCounter()));
        that.bounds = (Rectangle2D.Double) this.bounds.clone();
        return that;
    }
}
