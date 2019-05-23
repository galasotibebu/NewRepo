/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package geometrypackage;

/**
 * <h1> This Software Creates and Administer 2D Graphical objects</h1>
 * @author Anesu Mashoko, Tibebu Galaso, Kristof Giyori, Selam Amedie
 * @version 1.0
 * @since June
 **/
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Point;
import java.awt.RenderingHints;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.ArrayList;
//import java.util.Iterator;
//import javax.swing.JPanel;


public class PointFeature  {//my Point class
    //Point class instance: x,y coordinates
    private static double x;
    private static double y;
    public  static Color pointColor = new Color(66, 95, 244);
    private static int pointWidth = 15;
    private static int pointHeight = 15;
    
    private static int pointsList[];
    
    //public static double Points[];
    
//..............................................................................
//Constructor
    
    /**
     *
     */
    public PointFeature(){
        
    }
    /**
     *
     * @param x
     * @param y
     */
    public PointFeature(double x, double y){
        this.x = x;
        this.y = y;
    }
    
//..............................................................................
    //Getter and Setter Methods
    
    /**
     *
     * @return returns x coordinate of PointFeatures class
     */
    public static double getX() { return x; }
    /**
     *
     * @return returns y coordinate of PointFeatures class
     */
    public static double getY() { return y; }
    /**
     * this method reassigns the x coordinate of a PointClass class
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * this method reassigns the y coordinate of a PointClass class
     * @param y
     */
     public void setY(double y) {
        this.y = y;
    }
    
//..............................................................................
//Draw Point Method
  public static void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setPaint(pointColor);
        
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setRenderingHints(rh);
        
        int ovalX = (int)getX();
        int ovalY = (int)getY();
        int ovalW = pointWidth;
        int ovalH = pointHeight;
        
        g2d.fillOval(ovalX, ovalY, ovalW, ovalH);
    }
    
    /*public static void main(String[] args){
        System.out.print(PointClass.getX());
        System.out.print(PointClass.getY());
    }*/
   
}