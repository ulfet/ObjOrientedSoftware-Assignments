/* @(#)AlignToolBar.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.event.SelectionComponentDisplayer;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.samples.svg.SVGLabels;
import org.jhotdraw.util.ResourceBundleUtil;

import org.jhotdraw.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * AlignToolBar.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class AlignToolBar extends AbstractToolBar {
    private static final long serialVersionUID = 1L;

    @Nullable private SelectionComponentDisplayer displayer;

    /** Creates new instance. */
    public AlignToolBar() {
        ResourceBundleUtil labels = SVGLabels.getLabels();
        setName(labels.getString(getID() + ".toolbar"));
    }

    @Override
    public void setEditor(DrawingEditor newValue) {
        if (displayer != null) {
            displayer.dispose();
            displayer = null;
        }
        super.setEditor(newValue);
        if (newValue != null) {
            displayer = new SelectionComponentDisplayer(editor, this);
            displayer.setMinSelectionCount(2);
            displayer.setVisibleIfCreationTool(false);
        }
    }

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1: {
                p = new JPanel();
                p.setOpaque(false);
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                // Abort if no editor is set
                if (editor == null) {
                    break;
                }

                ResourceBundleUtil labels = SVGLabels.getLabels();

                GridBagLayout layout = new GridBagLayout();
                p.setLayout(layout);

                GridBagConstraints gbc;
                AbstractButton btn;
                AbstractSelectedAction d;

                gbc = new GridBagConstraints();
                gbc.gridy = 0;
                btn = new JButton(d = new AlignAction.West(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.setText(null);
                p.add(btn, gbc);

                gbc.insets = new Insets(0, 3, 0, 0);
                btn = new JButton(d = new AlignAction.East(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setText(null);
                p.add(btn, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(3, 0, 0, 0);
                btn = new JButton(d = new AlignAction.North(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setText(null);
                p.add(btn, gbc);

                gbc.insets = new Insets(3, 3, 0, 0);
                btn = new JButton(d = new AlignAction.South(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setText(null);
                p.add(btn, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.insets = new Insets(3, 0, 0, 0);
                btn = new JButton(d = new AlignAction.Horizontal(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setText(null);
                p.add(btn, gbc);

                gbc.gridx = 1;
                gbc.insets = new Insets(3, 3, 0, 0);
                btn = new JButton(d = new AlignAction.Vertical(editor, labels));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setText(null);
                p.add(btn, gbc);
            }
            break;
        }
        return p;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected String getID() {
        return "align";
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
