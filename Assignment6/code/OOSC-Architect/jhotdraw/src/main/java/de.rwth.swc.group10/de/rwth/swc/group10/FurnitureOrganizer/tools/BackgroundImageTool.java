package de.rwth.swc.group10.FurnitureOrganizer.tools;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.ImageFigure;
import org.jhotdraw.draw.ImageHolderFigure;
import org.jhotdraw.draw.action.MoveAction;
import org.jhotdraw.draw.tool.ImageTool;

public class BackgroundImageTool extends ImageTool {
    /**
     * Move actions, which moves the figure to (0,0)
     */
    private class ZeroPointMoveAction extends MoveAction {
        public ZeroPointMoveAction(DrawingEditor editor, Figure figure) {
            super(editor, -(int)figure.getStartPoint().getX(), -(int)figure.getStartPoint().getY());
        }
    }

    public BackgroundImageTool(ImageHolderFigure prototype) {
        super(prototype);
    }

    @Override
    protected void creationFinished(Figure createdFigure) {
        super.creationFinished(createdFigure);

        // move the created image to (0,0)
        MoveAction ma = new ZeroPointMoveAction(editor, createdFigure);
        ma.actionPerformed(null);

        // Set it as "background"
        getView().getDrawing().sendToBack(createdFigure);
        // remove it from selection
        getView().removeFromSelection(createdFigure);
        // and disable selection of this image
        ((ImageFigure) createdFigure).setSelectable(false);
    }
}
