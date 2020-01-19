package actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GroupFigure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.util.ResourceBundleUtil;

import de.rwth.swc.group10.FurnitureOrganizer.elements.Wall;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class CreateInWallElement extends CreationTool {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "furnisher.create.door";
	
	DrawingEditor editor;

    /**
     * Creates an action which acts on the selected figures on the current view
     * of the specified editor.
     *
     * @param editor
     */
    public CreateInWallElement(DrawingEditor editor, Figure prototype) {
        super(prototype);
    	this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	System.out.println("Trying to place InWallElement...");
    	// Check if cursor is on wall
    	Figure figurePointedAt = editor.getActiveView().findFigure(e.getPoint());
    	if (figurePointedAt != null)
    	{
    		if (figurePointedAt instanceof GroupFigure)
    		{
    			System.out.println("WALL");
    		}
    		else
    		{
    			System.out.println("NO WALL");
    		}
    	}
    	else
    	{
    		System.out.println("NONE AT ALL");
    	}
    	
    	if (figurePointedAt instanceof GroupFigure) {
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
