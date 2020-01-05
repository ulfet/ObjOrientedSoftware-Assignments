package de.rwth.swc.group10.FurnitureOrganizer.actions;

import de.rwth.swc.group10.FurnitureOrganizer.elements.Room;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GroupFigure;
import org.jhotdraw.draw.tool.CreationTool;

import java.awt.event.MouseEvent;

public class CreateInRoomElement extends CreationTool {
    /**
	 *
	 */

	private static final long serialVersionUID = 1L;
	public static final String ID = "furnisher.create.wall";
	DrawingEditor editor;

    /**
     * Creates an action which acts on the selected figures on the current view
     * of the specified editor.
     *
     * @param editor
     */
    public CreateInRoomElement(DrawingEditor editor, Figure prototype) {
        super(prototype);
    	this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	System.out.println("Trying to place InRoomElement...");
    	// Check if cursor is on wall
    	Figure figurePointedAt = editor.getActiveView().findFigure(e.getPoint());
    	if (figurePointedAt != null)
    	{
    		if (figurePointedAt instanceof Room)
    		{
    			System.out.println("ROOM");
    		}
    		else
    		{
    			System.out.println("NO ROOM");
    		}
    	}
    	else
    	{
    		System.out.println("NONE AT ALL");
    	}
    	
    	if (figurePointedAt instanceof Room) {
            //if (isToolDoneAfterCreation()) {
                super.mousePressed(e);
                //fireToolDone();
            //}
        } else {
        	// Computer says no
            return;
        }
    }
}
