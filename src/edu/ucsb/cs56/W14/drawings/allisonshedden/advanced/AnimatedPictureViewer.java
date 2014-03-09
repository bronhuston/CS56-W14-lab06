package edu.ucsb.cs56.w14.drawings.allisonshedden.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class AnimatedPictureViewer {

    private DrawPanel panel = new DrawPanel();

    JFrame frame = new JFrame("Allison's Animation of Cereal Bowls");

    private CerealBowl cb = new CerealBowl(100, 100, 150, 100);
    
    Thread anim;  
    
    private int x = 0;
    private int y = 140;
    
    private int dx = 7;
    private int dy = 7;

    private int a = 490;
    private int b = 140;

    private int da = 7;
    private int db = 7;

    public static void main (String[] args) {
      new AnimatedPictureViewer().go();
    }

    public void go() {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(panel);
      frame.setSize(640,480);
      frame.setVisible(true);
      center(frame);
     
      frame.getContentPane().addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e){
        System.out.println("Mouse entered");
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
	      public void mouseClicked(MouseEvent e){
		  System.out.println("Mouse clicked");
	      }
       });
}
    class DrawPanel extends JPanel {
       public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

	// Random Color
	int red = (int)(Math.random() * 256);
	int blue = (int)(Math.random() * 256);
	int yellow = (int)(Math.random() * 256);
	Color randomC = new Color(red, blue, yellow);

         // Clear the panel first
          g2.setColor(Color.black);
          g2.fillRect(0,0,this.getWidth(), this.getHeight());


          // Draw the Cereal Bowl
          g2.setColor(randomC);
	  g2.setStroke(new BasicStroke(3));

	  CerealBowl cb = new CerealBowl(a, b, 150, 100);

          CerealBowl cb2 = new CerealBowl(x, y, 150, 100);

	  CerealBowlWithChex cbwc = new CerealBowlWithChex(95, 140, 450, 300);

	  g2.drawString("Psychedelic Cereal Bowls", 245, 20);

          g2.draw(cb);
	  g2.draw(cb2);
	  g2.draw(cbwc);
       }
    }
    
    class Animation extends Thread {
      public void run() {
        try {
	    while(true) {
            // Bounce off the walls

            if (x >= 490) { dx = -7; }
            if (x <= 0) { dx = 7; }
	    if (y >= 380) { dy = -7; }
	    if (y <= 100) { dy = 7; }

	    if (a >= 490) { da = -7; }
            if (a <= 0) { da = 7; }
            if (b >= 380) { db = -7; }
            if (b <= 100) { db = 7; }

            x += dx;
            y += dy;
            
            a += da;                
	    b += db;
	    
	    panel.repaint();
            Thread.sleep(50);
	   
	    }
	}catch(Exception ex) {
          if (ex instanceof InterruptedException) {
            // Do nothing - expected on mouseExited
          } else {
            ex.printStackTrace();
            System.exit(1);
          }
        }
      }
    }
    
    public static void center(JFrame frame) {

        // Gets the size of the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculates the new location of the window
        int w = frame.getSize().width;
        int h = frame.getSize().height;

        int x = (d.width - w) / 2;
        int y = (d.height - h) / 2;

        // Moves this component to a new location

        frame.setLocation(x, y);
    }


}
