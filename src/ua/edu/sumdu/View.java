package ua.edu.sumdu;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel{

    public void display() {
        JFrame f = new JFrame("My library");
        f.getContentPane().setBackground(Color.WHITE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
        f.add(this);
        f.add(Box.createVerticalGlue());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
