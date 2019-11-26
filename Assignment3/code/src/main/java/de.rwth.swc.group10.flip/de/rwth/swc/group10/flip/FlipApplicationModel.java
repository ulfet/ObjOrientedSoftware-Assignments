package de.rwth.swc.group10.flip;

import org.jhotdraw.annotation.Nullable;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.DrawLabels;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.draw.DrawApplicationModel;
import org.jhotdraw.samples.draw.DrawView;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.util.List;

public class FlipApplicationModel extends DrawApplicationModel {
    private static final long serialVersionUID = 1L;

    public FlipApplicationModel() {
    }

    @Override
    public List<JToolBar> createToolBars(Application a, @Nullable View pr) {
        ResourceBundleUtil labels = DrawLabels.getLabels();
        DrawView p = (DrawView) pr;
        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getEditor();
        }

        List<JToolBar> list = super.createToolBars(a, pr);

        JToolBar tb = new JToolBar();

        tb.add(new VerticalFlipAction(editor)).setFocusable(false);
        tb.add(new HorizontalFlipAction(editor)).setFocusable(false);

        tb.setName("Flip-Toolbar");
        list.add(tb);

        return list;
    }
}