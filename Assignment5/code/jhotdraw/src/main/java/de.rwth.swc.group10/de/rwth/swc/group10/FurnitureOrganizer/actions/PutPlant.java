package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TEXT;

public class PutPlant extends TextAreaFigure {
    public static final String ID = "furnisher.put.plant";
    public final String furnitureType = "plant";

    public static int counter = 0;

    public PutPlant() {
        this(  Integer.toString(getCounter())     );
        System.out.println("PutPlant constructor called");
        setFontSize(10);
    }

    public static void incrementCounter() {
        PutPlant.counter += 1;
    }

    public static int getCounter() {
        return PutPlant.counter;
    }

    public PutPlant(String text) {
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

        PutPlant that = (PutPlant) super.clone();
        that.setText(furnitureType + ":" + Integer.toString(getCounter()));
        that.bounds = (Rectangle2D.Double) this.bounds.clone();
        return that;
    }
}
