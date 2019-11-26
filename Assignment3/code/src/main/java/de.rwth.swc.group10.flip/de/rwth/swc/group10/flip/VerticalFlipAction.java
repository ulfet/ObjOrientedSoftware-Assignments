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
        ResourceBundleUtil labels = DrawLabels.getLabels();
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!getView().getSelectedFigures().isEmpty()) {
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);

            for (Figure f : getView().getSelectedFigures()) {
                if (f.isTransformable()) {
                    f.willChange();
                    System.out.println(f.getStartPoint());
                    System.out.println(f.getEndPoint());
                    // TODO: This does not mirror the figure!
                    tx.translate(0, 2 * f.getEndPoint().getY() * -1);
                    f.transform(tx);
                    f.changed();


                    fireUndoableEditHappened(new TransformEdit(Collections.singleton(f), tx));
                    break;
                }
            }
        }
    }
}
