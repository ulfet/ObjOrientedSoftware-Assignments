package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TEXT;
import org.jhotdraw.draw.ImageFigure;

import java.io.File;
import java.io.IOException;

public class PutRefrigerator extends ImageFigure {

    public PutRefrigerator(){
        super();
        File file1 = new File("resources/refigerator.jpg");
        System.out.println("Exists: " + file1.exists());
        System.out.println("Can read: " + file1.canRead());
        try {
            loadImage(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


