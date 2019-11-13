package observer3;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ColorPanel extends JPanel implements ChangeListener, PropertyChangeListener {
    private Color color;
    private boolean isPrimary;

    public ColorPanel(Color initialColor, boolean isPrimary) {
        this.color = initialColor;
        this.isPrimary = isPrimary;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        this.setBackground(color);
        super.paintComponent(g);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (!isPrimary)
            return;
        JSlider slider = (JSlider) e.getSource();
        if (slider == null)
            return;
        float[] currentColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        switch (slider.getName()) {
            case "h":
                currentColor[0] = (float) slider.getValue() / 100;
                break;
            case "s":
                currentColor[1] = (float) slider.getValue() / 100;
                break;
            case "b":
                currentColor[2] = (float) slider.getValue() / 100;
                break;
        }
        float newHue = currentColor[0];
        float newSaturation = currentColor[1];
        float newBrightness = currentColor[2];
        Color newColor = Color.getHSBColor(newHue, newSaturation, newBrightness);
        setColor(newColor);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == this)
            return;
        System.out.println(evt);
        float[] currentColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float complementaryHue = currentColor[0] - (float) 0.5;
        if (complementaryHue < 0) {
            complementaryHue = complementaryHue + 1;
        }
        Color complementaryColor = Color.getHSBColor(complementaryHue, currentColor[1], currentColor[2]);
        setColor(complementaryColor);
    }
}