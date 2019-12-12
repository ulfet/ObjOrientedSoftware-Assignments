/* @(#)JLifeFormattedTextField.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw.gui;

import org.jhotdraw.annotation.Nullable;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * A JFormattedTextField which updates its value while the user is editing
 * the field.
 * 
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JLifeFormattedTextField extends JFormattedTextField {
    private static final long serialVersionUID = 1L;

    /**
     * Last valid value.
     */
    @Nullable private Object value;

    /** The DocumentHandler listens for document changes while the user is
     * editing the field.
     */
    private class DocumentHandler implements DocumentListener {

    @Override
        public void insertUpdate(DocumentEvent e) {
            updateValue();
        }

    @Override
        public void removeUpdate(DocumentEvent e) {
            updateValue();
        }

    @Override
        public void changedUpdate(DocumentEvent e) {
            updateValue();
        }
    }
    /** The DocumentHandler handles document changes while the user is
     * editing the field.
     */
    private DocumentHandler documentHandler;
    /**
     * This variable is used to prevent endless update loops.
     * We increase its value on each entry in one of the update methods
     * and decrease it on each exit.
     */
    private int updatingDepth;

    /** Creates new instance. */
    public JLifeFormattedTextField() {
    }

    @Override
    public void setDocument(Document newValue) {
        Document oldValue = getDocument();
        super.setDocument(newValue);

        if (documentHandler == null) {
            documentHandler = new DocumentHandler();
        }

        if (oldValue != null) {
            oldValue.removeDocumentListener(documentHandler);
        }
        if (newValue != null) {
            newValue.addDocumentListener(documentHandler);
        }
        updateValue();
    }

    @Override
    public void setValue(Object newValue) {
        Object oldValue = this.value;
        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
            return;
        }
        if (newValue != null && getFormatterFactory() == null) {
            setFormatterFactory(getDefaultFormatterFactory(newValue));
        }
        this.value = newValue;
        firePropertyChange("value", oldValue, newValue);
        updateText();
    }

    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Updates the value from the text of the field.
     */
    protected void updateValue() {
        if (updatingDepth++ == 0) {
            if (getFormatter() != null) {
                try {
                    Object newValue = getFormatter().stringToValue(getText());
                    setValue(newValue);
                } catch (ParseException ex) {
                    //ex.printStackTrace();// do nothing
                }
            }
        }
        updatingDepth--;
    }

    /**
     * Updates the text of the field from the value.
     */
    protected void updateText() {
        if (updatingDepth++ == 0) {
            if (getFormatter() != null) {
                try {
                    String newText = getFormatter().valueToString(getValue());
                    setText(newText);
                    if (!isFocusOwner()) {
                        // This is like selectAll(), but we set the
                        // cursor at the start of the field, because
                        // the start of the field contains the most
                        // significant part of the field content.
                        setCaretPosition(getDocument().getLength());
                        moveCaretPosition(0);
                    }
                } catch (ParseException ex) {
                    //ex.printStackTrace(); do nothing
                }
            }
        }
        updatingDepth--;
    }

    /**
     * Returns an AbstractFormatterFactory suitable for the passed in
     * Object type.
     */
    private AbstractFormatterFactory getDefaultFormatterFactory(Object type) {
        if (type instanceof DateFormat) {
            return new DefaultFormatterFactory(new DateFormatter((DateFormat) type));
        }

        if (type instanceof NumberFormat) {
            return new DefaultFormatterFactory(new NumberFormatter(
                    (NumberFormat) type));
        }

        if (type instanceof Format) {
            return new DefaultFormatterFactory(new InternationalFormatter(
                    (Format) type));
        }

        if (type instanceof Date) {
            return new DefaultFormatterFactory(new DateFormatter());
        }

        if (type instanceof Number) {
            AbstractFormatter displayFormatter = new NumberFormatter();
            ((NumberFormatter) displayFormatter).setValueClass(type.getClass());
            AbstractFormatter editFormatter = new NumberFormatter(
                    new DecimalFormat("#.#"));
            ((NumberFormatter) editFormatter).setValueClass(type.getClass());

            return new DefaultFormatterFactory(displayFormatter,
                    displayFormatter, editFormatter);
        }

        return new DefaultFormatterFactory(new DefaultFormatter());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}