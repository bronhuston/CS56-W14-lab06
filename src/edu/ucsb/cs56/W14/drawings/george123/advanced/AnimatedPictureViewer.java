package edu.ucsb.cs56.w14.drawings.george123.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedPictureViewer {

    private DrawPanel panel = new DrawPanel();

    private Pokeball pokeball = new Pokeball(200, 200, 200);

    Thread anim;

    private int x = 0;
    private int y = 0;

    private int dx = 0;
    private int dy = 0;

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

          // Draw the Pokeball
          g2.setColor(Color.RED);
          Pokeball test = new Pokeball(x, y, 200);
          g2.draw(test);
       }
    }

    class Animation extends Thread {
      public void run() {
        try {
          while (true) {
            // Move the Pokeball in a square.
	    if (x == 0 && y == 0) { 
		dx = 0;
		dy = 10;
	    }
	    if (x == 0 && y == 400) {
		dx = 10;
		dy = 0;
	    }
            if (x == 440 && y == 400) {
    		dx = 0;
		dy = -10;
	    }
	    if (x == 440 && y == 0) {
		dx = -10;
		dy = 0;
	    }
	
	    x += dx;
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
