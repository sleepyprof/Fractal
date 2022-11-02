package de.gdietz.gui.swing;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.event.FocusEvent;

public class JNumberCachedTextField extends JFormattedTextField {

    private final NumberFormat format;
    private final boolean acceptEmptyAsZero;
    private final boolean bigNumbers;
    private Number valueCache;

    protected class NumberFormatterFactory extends AbstractFormatterFactory {

        private final NumberFormatter formatter;

        public NumberFormatterFactory() {
            formatter = new NumberFormatter();
        }

        public AbstractFormatter getFormatter(JFormattedTextField tf) {
            return formatter;
        }

    }

    protected class NumberFormatter extends AbstractFormatter {

        public Number stringToValue(String text) throws ParseException {
            if (format != null)
                return format.parse(text);
            if (bigNumbers) {
                try {
                    return new BigDecimal(text);
                } catch (Exception e) {
                    throw new ParseException("Cound not parse BigDecimal (" + e.getMessage() + ")", 0);
                }
            }
            return Double.parseDouble(text);
        }

        public String valueToString(Number value) {
            if (format != null)
                return format.format(value);
            return value.toString();
        }

        public String valueToString(Object value) throws ParseException {
            if (value == null)
                return null;
            if (value instanceof Number)
                return valueToString((Number) value);
            return value.toString();
        }

    }

    private JNumberCachedTextField(NumberFormat format, boolean acceptEmptyAsZero, boolean bigNumbers) {
        super();
        this.format = format;
        this.acceptEmptyAsZero = acceptEmptyAsZero;
        this.bigNumbers = bigNumbers;
        valueCache = null;
        AbstractFormatterFactory ff = new NumberFormatterFactory();
        setFormatterFactory(ff);
        workaroundDeleteTwiceMacOS();
    }

    public JNumberCachedTextField(NumberFormat format, boolean acceptEmptyAsZero) {
        this(format, acceptEmptyAsZero, false);
    }

    public JNumberCachedTextField(NumberFormat format) {
        this(format, true);
    }

    public JNumberCachedTextField(boolean bigNumbers, boolean acceptEmptyAsZero) {
        this(null, acceptEmptyAsZero, bigNumbers);
    }

    public JNumberCachedTextField(boolean bigNumbers) {
        this(bigNumbers, true);
    }

    private void workaroundDeleteTwiceMacOS() {
        final Action originalDeleteAction = getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        getActionMap().put(DefaultEditorKit.deletePrevCharAction,
                new AbstractAction() {
                    ActionEvent previousEvent;
                    public void actionPerformed(ActionEvent e) {
                        if (previousEvent == null || e.getWhen() - previousEvent.getWhen() > 1)
                            originalDeleteAction.actionPerformed(e);
                        previousEvent = e;
                    }
                });
    }


    public void setValue(Number value) {
        if (valueCache == null || !valueCache.equals(value)) {
            valueCache = value;
            super.setValue(value);
            setCaretPosition(0);
        }
    }

    public Number getValue() {
        if (acceptEmptyAsZero) {
            String text = getText();
            if (text.isEmpty())
                return 0.0;
        }
        return (Number) super.getValue();
    }

    public Number getValueCommit() {
        valueCache = getValue();
        return valueCache;
    }

    public double getDoubleValueCommit() {
        return getValueCommit().doubleValue();
    }

    public float getFloatValueCommit() {
        return getValueCommit().floatValue();
    }

    public int getIntValueCommit() {
        return getValueCommit().intValue();
    }

    public long getLongValueCommit() {
        return getValueCommit().longValue();
    }

    public BigDecimal getBigDecimalValueCommit() {
        Number result = getValueCommit();
        if (result instanceof BigDecimal)
            return (BigDecimal) result;
        return BigDecimal.valueOf(result.doubleValue());
    }

    protected void processFocusEvent(FocusEvent e) {
        super.processFocusEvent(e);
        if (e.getID() == FocusEvent.FOCUS_GAINED) {
            setCaretPosition(0);
            if (getText() != null && isEditable()) {
                moveCaretPosition(getText().length());
            }
        }
    }

}