package codes.phases;

import gui.draw.MainWindow;
import javax.swing.*;
import java.awt.*;

public class PhaseOne {

    public void run() {
        JFrame j = new JFrame();
        j.setTitle("Dijkstra Algorithm");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 600));
        j.add(new MainWindow());
        j.setVisible(true);
    }

}
