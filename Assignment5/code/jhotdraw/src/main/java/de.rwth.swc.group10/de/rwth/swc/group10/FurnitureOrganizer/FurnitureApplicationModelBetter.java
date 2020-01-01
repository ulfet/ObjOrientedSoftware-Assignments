package de.rwth.swc.group10.FurnitureOrganizer;

import de.rwth.swc.group10.FurnitureOrganizer.actions.*;
import de.rwth.swc.group10.FurnitureOrganizer.tools.BackgroundImageTool;
import org.jhotdraw.annotation.Nullable;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.liner.CurvedLiner;
import org.jhotdraw.draw.liner.ElbowLiner;
import org.jhotdraw.draw.tool.*;
import org.jhotdraw.samples.draw.DrawApplicationModel;
import org.jhotdraw.samples.draw.DrawView;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;

public class FurnitureApplicationModelBetter extends DrawApplicationModel {
    private static final long serialVersionUID = 1L;

    private void addFloorPlanButtonsTo(JToolBar tb, DrawingEditor editor) {
        addDefaultFloorPlanButtonsTo(tb, editor,
                ButtonFactory.createDrawingActions(editor),
                ButtonFactory.createSelectionActions(editor));
    }

    public void addDefaultFloorPlanButtonsTo(JToolBar tb, final DrawingEditor editor,
                                            Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = DrawLabels.getLabels();

        ButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();

        ButtonFactory.addToolTo(tb, editor, new CreationTool(new RectangleFigure()), "furnisher.create.door", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new RectangleFigure()), "furnisher.create.room", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new RectangleFigure()), "furnisher.create.wall", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new RectangleFigure()), "furnisher.create.window", labels);
    }

    private void addFurnitureButtonsTo(JToolBar tb, DrawingEditor editor) {
        addDefaultFurnitureButtonsTo(tb, editor,
                ButtonFactory.createDrawingActions(editor),
                ButtonFactory.createSelectionActions(editor));
    }

    public void addDefaultFurnitureButtonsTo(JToolBar tb, final DrawingEditor editor,
                                             Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = DrawLabels.getLabels();

        ButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();

        ButtonFactory.addToolTo(tb, editor, new CreationTool(new PutChair()), "furnisher.put.chair", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new PutElse()), "furnisher.put.else", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new PutFurniture()), "furnisher.put.furniture", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new PutPlant()), "furnisher.put.plant", labels);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new PutRefrigerator()), "furnisher.put.refrigerator", labels);
    }

    private void addIOButtonsTo(JToolBar tb, DrawingEditor editor) {
        addDefaultIOButtonsTo(tb, editor,
                ButtonFactory.createDrawingActions(editor),
                ButtonFactory.createSelectionActions(editor));
    }

    private void addDefaultIOButtonsTo(JToolBar tb, final DrawingEditor editor,
                                       Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = DrawLabels.getLabels();

        ButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();

        ButtonFactory.addToolTo(tb, editor, new BackgroundImageTool(new ImageFigure()), "furnisher.io.background", labels);
    }

    @Override
    public List<JToolBar> createToolBars(Application a, @Nullable View pr) {
        // save the editor for further usage
        DrawView p = (DrawView) pr;
        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getEditor();
        }

        ResourceBundleUtil labels = DrawLabels.getLabels();

        // create the default drawing toolbars
        List<JToolBar> list = super.createToolBars(a, pr);

        JToolBar tb = new JToolBar();
        addFloorPlanButtonsTo(tb, editor);
        tb.setName("FloorplanButtons - Furnisher");
        list.add(tb);

        tb = new JToolBar();
        addFurnitureButtonsTo(tb, editor);
        tb.setName("FurnitureButtons - Furnisher");
        list.add(tb);
        // Add new toolbar with Flip Buttons

        tb = new JToolBar();
        tb.setName("IO Operations - Furnisher");
        addIOButtonsTo(tb, editor);
        //tb.add(new IOImportImageAsFloorplan(editor)).setFocusable(false);
        tb.add(new IOExportAsImage(editor)).setFocusable(false);
        list.add(tb);
//
//        tb = new JToolBar();
//        tb.setName("Room Structure - Furnisher");
//        tb.add(new CreateDoor(editor)).setFocusable(false);
//        tb.add(new CreateRoom(editor)).setFocusable(false);
//        tb.add(new CreateWall(editor)).setFocusable(false);
//        tb.add(new CreateWindow(editor)).setFocusable(false);
//        list.add(tb);
//
//        tb = new JToolBar();
//        tb.setName("Furniture Addition - Furnisher");
//        tb.add(new PutChair(editor)).setFocusable(false);
//        tb.add(new PutElse(editor)).setFocusable(false);
//        tb.add(new PutFurniture(editor)).setFocusable(false);
//        tb.add(new PutPlant(editor)).setFocusable(false);
//        tb.add(new PutRefrigerator(editor)).setFocusable(false);
//        list.add(tb);

        return list;
    }
}
