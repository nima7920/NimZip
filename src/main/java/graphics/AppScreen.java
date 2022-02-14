package graphics;

import javax.swing.*;

public class AppScreen {

    JFrame frame;
    AppPanel appPanel;

    public AppScreen() {
        appPanel = new AppPanel();
        prepareFrame();

    }

    void prepareFrame() {
        frame = new JFrame("NimZip");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBounds(0, 0, 600, 500);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(appPanel);
        appPanel.setVisible(true);
        frame.setVisible(true);
    }
}
