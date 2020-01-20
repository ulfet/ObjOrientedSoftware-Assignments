package de.rwth.swc.group10.FurnitureOrganizer.elements;

import org.jhotdraw.draw.ImageFigure;

import java.io.File;
import java.io.IOException;

public class Door extends ImageFigure {
	
	public Door(){
        super();
        File file1 = new File("resources/door.png");
        System.out.println("Exists: " + file1.exists());
        System.out.println("Can read: " + file1.canRead());
        try {
            loadImage(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
