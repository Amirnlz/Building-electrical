package gui.draw;

import codes.algorithm.DijkstraAlgorithm;
import codes.graph.Graph;
import codes.picture.InputPicture;
import codes.picture.PictureProcess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    static JLabel label = new JLabel();
    static boolean flag = false;

    public MainWindow(){
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }

    private void setGraphPanel(){
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));
        graphPanel.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        setButtons();
    }

    private void setButtons(){
        JButton run = new JButton();
        setupIcon(run, "run");
        JButton reset = new JButton();
        reset.setBounds(100, 100, 100, 100);
        setupIcon(reset, "reset");
        final JButton info = new JButton();
        setupIcon(info, "info");
        final JButton visible = new JButton();
        setupIcon(visible, "visible");

        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(reset);
        buttonPanel.add(run);
        buttonPanel.add(info);
        buttonPanel.add(visible);

        visible.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag == false) {
                    label.setVisible(false);
                    flag= true;
                }
                else{
                    label.setVisible(true);
                    flag= false;
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Click on empty space to create new node\n" +
                                "Drag from node to node to create an edge\n" +
                                "Click on edges to set the weight\n\n" +
                                "Combinations:\n" +
                                "Shift + Left Click       :    Set node as source\n" +
                                "Shift + Right Click     :    Set node as destination\n" +
                                "Ctrl  + Drag               :    Reposition Node\n" +
                                "Ctrl  + Click                :    Get Path of Node\n" +
                                "Ctrl  + Shift + Click   :    Delete Node/Edge\n");
            }
        });

        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
                try{
                    dijkstraAlgorithm.run();
                    graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });

        add(buttonPanel, BorderLayout.WEST);
    }

    private void setupIcon(JButton button, String img){
        try {
            Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                    "/gui/images/" + img + ".png")));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}