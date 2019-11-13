package observer3;

import javax.swing.*;
import java.awt.*;

public class DisplayColors {

    public static void main(String[] args) {
        SwingFacade.launch(new DisplayColors().mainPanel(), "Compute Complementary Colors");
    }

    protected ColorPanel originalColorPanel;
    protected ColorPanel complementaryColorPanel;

    protected JSlider hueSlider;
    protected JSlider saturationSlider;
    protected JSlider brightnessSlider;

    protected JLabel hueValueLabel;
    protected JLabel saturationValueLabel;
    protected JLabel brightnessValueLabel;

    protected JPanel colorsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        originalColorPanel = createColorPanel(Color.getHSBColor(0, (float) .5, (float) .5), true);
        p.add(SwingFacade.createTitledPanel("Original Color", originalColorPanel));
        complementaryColorPanel = createColorPanel(Color.getHSBColor((float) .5, (float) .5, (float) .5), false);
        originalColorPanel.addPropertyChangeListener(complementaryColorPanel);
        p.add(SwingFacade.createTitledPanel("Complementary Color", complementaryColorPanel));
        return p;
    }

    protected JPanel mainPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        JPanel colorsPanel = colorsPanel();
        JPanel subP = new JPanel();
        subP.setLayout(new GridLayout(3, 1));
        hueSlider = slider("h");
        subP.add(sliderBox("H", hueSlider, hueValueLabel));
        saturationSlider = slider("s");
        saturationSlider.setValue(50);
        subP.add(sliderBox("S", saturationSlider, saturationValueLabel));
        brightnessSlider = slider("b");
        brightnessSlider.setValue(50);
        subP.add(sliderBox("B", brightnessSlider, brightnessValueLabel));
        p.add(subP);
        p.add(colorsPanel);
        return p;
    }

    private JSlider slider(String name) {
        JSlider slider = new JSlider();
        slider.setName(name);
        slider.addChangeListener(originalColorPanel);
        slider.addChangeListener(complementaryColorPanel);
        slider.setValue(slider.getMinimum());
        return slider;
    }

    private Box sliderBox(String sliderLabel, JSlider slider, JLabel valueLabel) {
        if (valueLabel == null) {
            valueLabel = new JLabel();
            valueLabel.setFont(SwingFacade.getStandardFont());
            valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            valueLabel.setForeground(Color.black);
        }
        Box b = Box.createHorizontalBox();
        JLabel label = new JLabel(sliderLabel);
        label.setFont(SwingFacade.getStandardFont());
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        label.setForeground(Color.black);
        b.add(label);
        b.add(valueLabel);
        b.add(slider);
        b.setPreferredSize(new Dimension(600, 50));
        return b;
    }

    protected ColorPanel createColorPanel(Color initialColor, boolean isPrimary) {
        ColorPanel colorPanel = new ColorPanel(initialColor, isPrimary);
        colorPanel.setPreferredSize(new Dimension(300, 200));
        return colorPanel;
    }
}
    
