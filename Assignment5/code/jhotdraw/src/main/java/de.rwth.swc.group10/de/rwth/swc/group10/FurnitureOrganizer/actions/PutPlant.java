package de.rwth.swc.group10.FurnitureOrganizer.actions;
import org.jhotdraw.draw.ImageFigure;

import java.io.File;
import java.io.IOException;

public class PutPlant extends ImageFigure {

    public PutPlant(){
        super();
        File file1 = new File("resources/plant.PNG");
        System.out.println("Exists: " + file1.exists());
        System.out.println("Can read: " + file1.canRead());
        try {
            loadImage(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

