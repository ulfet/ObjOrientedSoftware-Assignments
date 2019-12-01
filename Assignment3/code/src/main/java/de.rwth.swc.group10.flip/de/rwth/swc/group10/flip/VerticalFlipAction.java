package de.rwth.swc.group10.flip;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.util.Collections;

/**
 * This is the Action for a vertical flip of a selected figure
 */
public class VerticalFlipAction extends AbstractSelectedAction {

    public static final String ID = "flip.vertical";

    /**
     * Creates an action which acts on the selected figures on the current view
     * of the specified editor.
     *
     * @param editor
     */
    public VerticalFlipAction(DrawingEditor editor) {
        super(editor);

        // set text and image
        ResourceBundleUtil labels = DrawLabels.getLabels();
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Only perform the action, if there is something selected
        if (!getView().getSelectedFigures().isEmpty()) {


            // Iterate over all selected figures
            for (Figure f : getView().getSelectedFigures()) {
                // Only transform the figure, if it is possible
                if (f.isTransformable()) {
                    // Create the transformation
                    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                    // Move to the right location
                    tx.translate(-2 * (f.getStartPoint().getX() + f.getBounds().getWidth()), 0);

//                    tx.translate(0, -getEditor().getActiveView().getComponent().getHeight());

                    switch (f.get(AttributeKeys.ORIENTATION)){
                        case WEST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.EAST); break;
                        case EAST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.WEST); break;
                        case NORTH_WEST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.NORTH_EAST); break;
                        case NORTH_EAST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.NORTH_WEST); break;
                        case SOUTH_WEST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.SOUTH_EAST); break;
                        case SOUTH_EAST: f.set(AttributeKeys.ORIENTATION, AttributeKeys.Orientation.SOUTH_WEST); break;
                    }
                    f.willChange();
                    f.transform(tx);
                    f.changed();

                    // Break after the first transformation, because this depends on the selected figure
                    fireUndoableEditHappened(new TransformEdit(Collections.singleton(f), tx));
                    break;
                }
            }
        }
    }


}
