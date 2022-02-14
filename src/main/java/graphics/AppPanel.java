package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppPanel extends JPanel {

    Actions actions;
    JButton chooseFileButton, chooseDestinationButton, zipButton, unzipButton;
    JComboBox<Integer> vComboBox, bComboBox;
    JLabel titleLabel, vLabel, bLabel;
    JTextArea inputLabel, outputLabel;
   JRadioButton hashRadio , listRadio;
ButtonGroup bg;
    BufferedImage backgroundImage;

    public AppPanel() {
        setLayout(null);
        setBounds(0, 0, 600, 500);
        actions = new Actions(this);
        prepareButtons();
        prepareLabels();
        prepareCombos();
        prepareRadios();
        try {
            backgroundImage = ImageIO.read(new File(".\\src\\main\\resources\\NimZip.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareButtons() {
        chooseFileButton = new JButton("Choose input file");
        chooseFileButton.setBounds(75, 130, 200, 30);
        chooseFileButton.addActionListener(actions);
        add(chooseFileButton);

        chooseDestinationButton = new JButton("Choose output file");
        chooseDestinationButton.setBounds(325, 130, 200, 30);
        chooseDestinationButton.addActionListener(actions);
        add(chooseDestinationButton);

        zipButton = new JButton("zip");
        zipButton.setBounds(75, 300, 200, 30);
        zipButton.addActionListener(actions);
        add(zipButton);

        unzipButton = new JButton("unzip");
        unzipButton.setBounds(325, 300, 200, 30);
        unzipButton.addActionListener(actions);
        add(unzipButton);

    }

    private void prepareLabels() {
        titleLabel = new JLabel("NimZip");
        titleLabel.setFont(new Font("serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.yellow);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(150, 10, 300, 30);
        add(titleLabel);


        inputLabel = new JTextArea("input");
        inputLabel.setEditable(false);
        inputLabel.setOpaque(false);
        inputLabel.setLineWrap(true);
        inputLabel.setBounds(75, 50, 200, 30);
        add(inputLabel);

        outputLabel = new JTextArea("output");
        outputLabel.setEditable(false);
        outputLabel.setOpaque(false);
        outputLabel.setLineWrap(true);
        outputLabel.setBounds(325, 50, 200, 30);
        add(outputLabel);

        vLabel = new JLabel("v:");
        vLabel.setBounds(75, 200, 200, 30);
        add(vLabel);

        bLabel = new JLabel("b:");
        bLabel.setBounds(325, 200, 200, 30);
        add(bLabel);

    }

    private void prepareCombos() {
        vComboBox = new JComboBox<>();
        for (int i = 0; i < 32; i++) {
            vComboBox.addItem(i);
        }
        vComboBox.setBounds(75, 250, 200, 30);
        add(vComboBox);

        bComboBox = new JComboBox<>();
        for (int i = 0; i < 8; i++) {
            bComboBox.addItem(i);
        }
        bComboBox.setBounds(325, 250, 200, 30);
        add(bComboBox);
    }

    private void prepareRadios(){
        bg=new ButtonGroup();
        hashRadio=new JRadioButton("HashWindow ( faster , requires more internal memory)",true);
        hashRadio.setBounds(75,350,400,30);
        hashRadio.setOpaque(false);
        bg.add(hashRadio);
        listRadio=new JRadioButton("ListWindow ( slower , requires low internal memory)");
        listRadio.setBounds(75,390,400,30);
        listRadio.setOpaque(false);
        bg.add(listRadio);

        add(hashRadio);
        add(listRadio);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        paintImage(g2d);
    }

    private void paintImage(Graphics2D g2d) {
        g2d.drawImage(backgroundImage, 0, 0, 600, 500, null);
    }
}
