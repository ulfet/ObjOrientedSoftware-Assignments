package de.rwth.swc.group10.FurnitureOrganizer.actions;

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

public class IOExportAsImage extends AbstractSelectedAction {
    public static final String ID = "furnisher.exportasimage";

    /**
     * Creates an action which acts on the selected figures on the current view
     * of the specified editor.
     *
     * @param editor
     */
    public IOExportAsImage(DrawingEditor editor) {
        super(editor);

        // set text and image
        ResourceBundleUtil labels = DrawLabels.getLabels();
        System.out.println(labels);
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ;
    }
}
