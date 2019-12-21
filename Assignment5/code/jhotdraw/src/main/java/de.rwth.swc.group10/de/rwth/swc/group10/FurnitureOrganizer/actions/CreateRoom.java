package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;

public class CreateRoom extends AbstractSelectedAction {
    public static final String ID = "furnisher.create.room";

    /**
     * Creates an action which acts on the selected figures on the current view
     * of the specified editor.
     *
     * @param editor
     */
    public CreateRoom(DrawingEditor editor) {
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
