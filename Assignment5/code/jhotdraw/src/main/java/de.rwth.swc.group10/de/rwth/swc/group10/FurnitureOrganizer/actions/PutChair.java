package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;



import static org.jhotdraw.draw.AttributeKeys.TEXT;

public class PutChair extends TextAreaFigure {
    public static final String ID = "furnisher.put.chair";
    public final String furnitureType = "Chair";

    public static int counter = 0;

    public PutChair() {
        this(  Integer.toString(getCounter())     );
        System.out.println("PutChair constructor called");
        setFontSize(10);

    }

    public static void incrementCounter() {
        PutChair.counter += 1;
    }

    public static int getCounter() {
        return PutChair.counter;
    }

    public PutChair(String text) {
        setText(text);
    }

    @Override
    public String getText() {
        return get(TEXT);
    }


    public TextAreaFigure clone() {
        incrementCounter();
        System.out.println( furnitureType + " clone" + "Called");
        PutChair that = (PutChair) super.clone();

        that.setText(furnitureType + ":" + Integer.toString(getCounter()));
        that.bounds = (Rectangle2D.Double) this.bounds.clone();

        return that;
    }
}
