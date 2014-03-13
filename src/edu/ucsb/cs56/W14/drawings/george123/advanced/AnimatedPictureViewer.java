package edu.ucsb.cs56.w14.drawings.george123.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedPictureViewer {

    private DrawPanel panel = new DrawPanel();

    private Pokeball ipod = new Pokeball(200, 200, 200);

    Thread anim;

    private int x = 200;
    private int y = 200;

    private int dy = 10;

    public static void main (String[] args) {
      new AnimatedPictureViewer().go();
    }

    public void go() {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(panel);
      frame.setSize(640,640);
      frame.setVisible(true);

      frame.getContentPane().addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e){
        System.out.println("mouse entered");
          anim = new Animation();
          anim.start();
        }

        public void mouseExited(MouseEvent e){
          System.out.println("Mouse exited");
          // Kill the animation thread
          anim.interrupt();
          while (anim.isAlive()){}
          anim = null;
          panel.repaint();
        }
      });

    } // go()

    class DrawPanel extends JPanel {
       public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

         // Clear the panel first
          g2.setColor(Color.white);
          g2.fillRect(0,0,this.getWidth(), this.getHeight());

          // Draw the Ipod
          g2.setColor(Color.RED);
          Pokeball test = new Pokeball(x, y, 200);
          g2.draw(test);
       }
    }

    class Animation extends Thread {
      public void run() {
        try {
          while (true) {
            // Bounce off the walls

            if (y >= 400) { dy = -10; }
            if (y <= 0) { dy = 10; }

            y += dy;
            panel.repaint();
            Thread.sleep(50);
          }
        } catch(Exception ex) {
          if (ex instanceof InterruptedException) {
            // Do nothing - expected on mouseExited
          } else {
            ex.printStackTrace();
            System.exit(1);
          }
        }
      }
    }

}
