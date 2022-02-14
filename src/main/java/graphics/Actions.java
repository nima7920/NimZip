package graphics;

import admin.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Actions implements ActionListener {

    AppPanel appPanel;
    File inputFile, outPutFile;
    Admin admin;

    public Actions(AppPanel appPanel) {
        this.appPanel = appPanel;
        admin = new Admin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == appPanel.chooseFileButton) {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            int i = fc.showOpenDialog(appPanel);
            if (i == JFileChooser.APPROVE_OPTION) {
                inputFile = fc.getSelectedFile();
                appPanel.inputLabel.setText(inputFile.getAbsolutePath());
            }

        } else if (e.getSource() == appPanel.chooseDestinationButton) {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            int i = fc.showOpenDialog(appPanel);
            if (i == JFileChooser.APPROVE_OPTION) {
                outPutFile = fc.getSelectedFile();
                appPanel.outputLabel.setText(outPutFile.getAbsolutePath());
            }

        } else if (e.getSource() == appPanel.zipButton) {
            if (inputFile != null && outPutFile != null) {
                if (admin.compress(inputFile, outPutFile, (Integer) appPanel.vComboBox.getSelectedItem(),
                        (Integer) appPanel.bComboBox.getSelectedItem(), appPanel.hashRadio.isSelected() ? 0 :1))
                    JOptionPane.showMessageDialog(appPanel, "done !"
                    ,"NimZip",JOptionPane.OK_OPTION);
                else
                    JOptionPane.showMessageDialog(appPanel, "an error occurred",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(appPanel,"please choose input and output",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == appPanel.unzipButton) {

            if (inputFile != null && outPutFile != null) {
                if (admin.decompress(inputFile, outPutFile, (Integer) appPanel.vComboBox.getSelectedItem(),
                        (Integer) appPanel.bComboBox.getSelectedItem(), appPanel.hashRadio.isSelected() ? 0 :1))
                    JOptionPane.showMessageDialog(appPanel, "done !"
                            ,"NimZip",JOptionPane.OK_OPTION);
                else
                    JOptionPane.showMessageDialog(appPanel, "an error occurred",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(appPanel,"please choose input and output",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
