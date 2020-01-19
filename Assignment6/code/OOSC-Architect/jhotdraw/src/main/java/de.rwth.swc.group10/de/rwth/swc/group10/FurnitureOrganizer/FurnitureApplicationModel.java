package de.rwth.swc.group10.FurnitureOrganizer;

import de.rwth.swc.group10.FurnitureOrganizer.actions.*;
import org.jhotdraw.annotation.Nullable;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.draw.DrawApplicationModel;
import org.jhotdraw.samples.draw.DrawView;

import javax.swing.*;
import java.util.List;

public class FurnitureApplicationModel extends DrawApplicationModel {
    private static final long serialVersionUID = 1L;

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
        // create the default drawing toolbars
        List<JToolBar> list = super.createToolBars(a, pr);

        // Add new toolbar with Flip Buttons
//        JToolBar tb = new JToolBar();
//        tb = new JToolBar();
//        tb.setName("IO Operations - Furnisher");
//        tb.add(new IOImportImageAsFloorplan(editor)).setFocusable(false);
//        tb.add(new IOExportAsImage(editor)).setFocusable(false);
//        list.add(tb);
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
////        tb.add(new PutChair(editor)).setFocusable(false);
//        tb.add(new PutElse(editor)).setFocusable(false);
//        tb.add(new PutFurniture(editor)).setFocusable(false);
//        tb.add(new PutPlant(editor)).setFocusable(false);
//        tb.add(new PutRefrigerator(editor)).setFocusable(false);
//        list.add(tb);
//
        return list;
    }
}
