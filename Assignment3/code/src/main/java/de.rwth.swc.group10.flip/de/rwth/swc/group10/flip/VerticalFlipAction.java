package de.rwth.swc.group10.flip;

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
            // Create the transformation
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);

            // Iterate over all selected figures
            for (Figure f : getView().getSelectedFigures()) {
                // Only transform the figure, if it is possible
                if (f.isTransformable()) {
                    f.willChange();

                    System.out.println(f.getStartPoint());
                    System.out.println(f.getEndPoint());

                    // TODO: This does not mirror the figure!
                    tx.translate(2 * f.getEndPoint().getX(), 0);

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
