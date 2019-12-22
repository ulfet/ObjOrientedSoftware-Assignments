package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TEXT;

public class PutStove extends TextAreaFigure {
    public static final String ID = "furnisher.put.stove";
    public final String furnitureType = "Stove";

    public static int counter = 0;

    public PutStove() {
        this(  Integer.toString(getCounter())     );
        System.out.println("PutStove constructor called");
        setFontSize(10);
    }

    public static void incrementCounter() {
        PutStove.counter += 1;
    }

    public static int getCounter() {
        return PutStove.counter;
    }

    public PutStove(String text) {
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

        PutStove that = (PutStove) super.clone();
        that.setText(furnitureType + ":" + Integer.toString(getCounter()));
        that.bounds = (Rectangle2D.Double) this.bounds.clone();
        return that;
    }
}
