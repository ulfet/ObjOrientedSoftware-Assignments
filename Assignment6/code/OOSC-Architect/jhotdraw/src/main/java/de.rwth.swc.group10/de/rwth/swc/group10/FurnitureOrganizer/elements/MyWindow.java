package de.rwth.swc.group10.FurnitureOrganizer.elements;

import org.jhotdraw.draw.ImageFigure;

import java.io.File;
import java.io.IOException;

public class MyWindow extends ImageFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyWindow(){
        super();
        File file1 = new File("resources/window.png");
        System.out.println("Exists: " + file1.exists());
        System.out.println("Can read: " + file1.canRead());
        try {
            loadImage(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
