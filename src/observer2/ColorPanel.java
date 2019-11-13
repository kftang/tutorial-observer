package observer2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.color.ColorSpace;

public class ColorPanel extends JPanel implements ChangeListener {
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
        float complementaryHue = newHue - (float) 0.5;
        if (complementaryHue < 0) {
            complementaryHue = complementaryHue + 1;
        }
        Color complementaryColor = Color.getHSBColor(complementaryHue, newSaturation, newBrightness);
        if (isPrimary)
            setColor(newColor);
        else
            setColor(complementaryColor);
    }
}