/* @(#)JAttributeTextField.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw.draw.gui;

import org.jhotdraw.gui.AttributeEditor;
import org.jhotdraw.gui.GuiLabels;
import org.jhotdraw.gui.JLifeFormattedTextArea;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ResourceBundle;

/**
 * An entry field that can be used to edit an attribute of a {@code Figure}.
 * 
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JAttributeTextArea<T> extends JLifeFormattedTextArea implements AttributeEditor<T> {
    private static final long serialVersionUID = 1L;

    /**
     * This variable is set to true, when the figures, which are currently
     * being edited by this field, have multiple values.
     */
    private boolean isMultipleValues;

    /** Creates new instance. */
    public JAttributeTextArea() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isFocusOwner() && isMultipleValues) {
            ResourceBundleUtil labels = GuiLabels.getLabels();
            Color c = getForeground();
            setForeground(new Color(0x0, true));
            super.paintComponent(g);
            Insets insets = getInsets();
            Insets margin = getMargin();
            FontMetrics fm = g.getFontMetrics(getFont());
            g.setFont(getFont().deriveFont(Font.ITALIC));
            setForeground(c);
            g.setColor(c);
            g.drawString(labels.getString("attribute.differentValues.text"),
                    insets.left + margin.left,
                    insets.top + margin.top + fm.getAscent());
        } else {
            super.paintComponent(g);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public JComponent getComponent() {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getAttributeValue() {
        return (T) getValue();
    }

    @Override
    public void setMultipleValues(boolean newValue) {
        isMultipleValues = newValue;
        repaint();
    }

    @Override
    public boolean isMultipleValues() {
        return isMultipleValues;
    }

    @Override
    public boolean getValueIsAdjusting() {
        return isFocusOwner();
    }

    @Override
    public void setAttributeValue(T newValue) {
        setValue(newValue);
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
        if (propertyName == "value") {
            super.firePropertyChange(ATTRIBUTE_VALUE_PROPERTY, oldValue, newValue);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
