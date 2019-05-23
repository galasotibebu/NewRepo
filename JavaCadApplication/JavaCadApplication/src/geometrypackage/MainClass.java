/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package geometrypackage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

/**
 * Creates the (Tool)Frame of the Software itself. The  is used as (GraphicalUser)Interface between the users actions
 * and the functionalities that are provided by this software.
 * @author heol1015
 */
@SuppressWarnings("serial")
public class MainClass extends JFrame implements ActionListener {
    
    /**
     * MainClass as (GraphicalUser)Interface between user and software
     * (invoked in main method)
     */
    public static MainClass frame;
    /**
     * Tooldrawingpanel
     * for displaying geometries in the editor object
     */
    public MainClass drawingpanel;
    /**
     * editor for storing and editing geometries
     */
    public Editor editor;
    /**
     * CSVInterface for importing and exporting data from/to CSV
     */
    public static CSVInterface csvinterface;
    /**
     * DBInterface for importing and exporting data from/to a Database
     */
    public static DBInterface dbinterface;
    
    //MenuBar Variables
    JMenuBar 	menubar;
    JMenu		datamenu;
    JMenu		toolabout;
    JMenuItem	csv;
    JMenuItem   db;
    
    //ToolBar Variables
    JToolBar 	toolbar;
    JButton		drawPoint;
    JButton		drawLine;
    JButton		drawTriangle;
    JButton		drawRectangle;
    JButton		selectElements;
    JButton		deleteElements;
    JButton 	moveElements;
    JButton		changeElements;
    
    Box toolBox;
    
    //Mouse Tracking Variables
    JLabel 	trackedCoord;
    int 		trackedX;
    int 		trackedY;
    
    //Mouse Selection Variables
    double selectionX1;
    double selectionY1;
    double selectionX2;
    double selectionY2;
    
    //DrawMode Tracking Variables
    JLabel 	trackedMode;
    
    Box trackingBox;
    
    //Functional Variables
    String drawMode = "default";
    boolean lineInitiated		= false;
    //Triangle
    boolean triangleInitiated1 	= false;
    boolean triangleInitiated2 	= false;
    //Rectangle
    boolean rectangleInitiated 	= false;
    //Selection
    boolean selectionInitiated 	= false;
    //Change
    boolean changeInitiated 	= false;
    
    //Movement& Change Variables
    boolean movementInitiated 	= false;
    boolean movingPoint 		= false;
    boolean movingLine 			= false;
    boolean movingTriangle 		= false;
    boolean movingRectangle 	= false;
    Ellipse2D selectionCircle;
    int identifier;
    int moveStartX;
    int moveStartY;
    int moveDestinationX;
    int moveDestinationY;
    boolean isStart = false;
    boolean isMiddle= false;
    boolean isEnd 	= false;
    Point2D point1;
    Point2D point2;
    Point2D point3;
    int objectArrayPosition;
    double x1;
    double y1;
    double x2;
    double y2;
    double x3;
    double y3;
    
    // Variables for ToolObjects
    MainClass point;
    LineFeature line;
    TriangleFeature triangle;
    RectangleFeature rectangle;
    
    
    /**
     * Initializes a new instance of editor and Tooldrawingpanel
     * ,
     * as well as Listeners for clicking and moving the mouse.
     * @author heol1015
     */
    public MainClass() {
        
        //creates object of editor
        editor = new Editor();
        //creates object of Tooldrawingpanel
        
        drawingpanel = new MainClass();
        drawingpanel.setBackground(Color.white);
        
        drawingpanel.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e) {
                
                point = new PointFeature();
                point.setPoint(e.getX(), e.getY());
                
                switch (drawMode) {
                    
                    case "PointMode":
                        editor.drawPoint(point);
                        drawingpanel
                                .requestToolObjectLists(editor);
                        drawingpanel
                                .repaint();
                        break;
                        
                    case "LineMode":
                        if( lineInitiated == false) {
                            line = new ToolLine();
                            line.addLineStart(point);
                            lineInitiated = true;
                            break;
                            
                        } else if (lineInitiated == true) {
                            line.addLineEnd(point);
                            editor.storeLineElements(line);
                            drawingpanel
                                    .requestToolObjectLists(editor);
                            drawingpanel
                                    .clearDrawingElements();
                            drawingpanel
                                    .repaint();
                            lineInitiated = false;
                            break;
                        }
                        
                    case "TriangleMode":
                        if ( triangleInitiated1 == false && triangleInitiated2 == false) {
                            triangle = new ToolTriangle();
                            triangle.addTriangleStart(point);
                            triangleInitiated1 = true;
                            break;
                            
                        } else if ( triangleInitiated1 == true && triangleInitiated2 == false) {
                            triangle.addTriangleMid(point);
                            triangleInitiated1 = false;
                            triangleInitiated2 = true;
                            break;
                            
                        } else if ( triangleInitiated1 == false && triangleInitiated2 == true) {
                            triangle.addTriangleEnd(point);
                            editor.storeTriangleElements(triangle);
                            drawingpanel
                                    .requestToolObjectLists(editor);
                            drawingpanel
                                    .clearDrawingElements();
                            drawingpanel
                                    .repaint();
                            triangleInitiated1 = false;
                            triangleInitiated2 = false;
                            break;
                        }
                        
                    case "RectangleMode":
                        if ( rectangleInitiated == false) {
                            rectangle = new ToolRectangle();
                            rectangle.addRetangleFirstCorner(point);
                            rectangleInitiated = true;
                            break;
                            
                        } else if ( rectangleInitiated == true) {
                            rectangle.addRectangleLastCorner(point);
                            editor.storeRectangleElements(rectangle);
                            drawingpanel
                                    .requestToolObjectLists(editor);
                            drawingpanel
                                    .clearDrawingElements();
                            drawingpanel
                                    .repaint();
                            rectangleInitiated = false;
                            break;
                        }
                        
                    case "SelectMode":
                        if ( selectionInitiated == false) {
                            editor.clearCurrentSelection();
                            drawingpanel.defineSelectionRectangle(null);
                            drawingpanel.requestToolObjectLists(editor);
                            drawingpanel.repaint();
                            
                            selectionX1 = point.x;
                            selectionY1 = point.y;
                            selectionInitiated = true;
                            break;
                            
                        } else if ( selectionInitiated == true) {
                            selectionX2 = point.x;
                            selectionY2 = point.y;
                            
                            //Determine 'height' and 'width' of the Selection Rectangle
                            double rectangleWidth = Math.abs(selectionX1-selectionX2);
                            double rectangleHeight = Math.abs(selectionY1-selectionY2);
                            
                            double rectangleStart;
                            double rectangleEnd;
                            
                            //Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
                            if( selectionX2 > selectionX1) {
                                rectangleStart = selectionX1;
                            } else {
                                rectangleStart = selectionX2;
                            }
                            if( selectionY2 > selectionY1) {
                                rectangleEnd = selectionY1;
                            } else {
                                rectangleEnd = selectionY2;
                            }
                            
                            //Compose Selection Rectangle
                            Rectangle2D selectionRectangle = new Rectangle2D.Double();
                            selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
                            drawingpanel.defineSelectionRectangle(selectionRectangle);
                            drawingpanel.repaint();
                            
                            editor.selectAffectedToolObjects(selectionRectangle);
                            drawingpanel.requestToolObjectLists(editor);
                            drawingpanel.repaint();
                            
                            selectionInitiated = false;
                            break;
                        }
                        
                    case "MoveMode":
                        
                        if ( movementInitiated == false) {
                            moveStartX = e.getX();
                            moveStartY = e.getY();
                            selectionCircle = new Ellipse2D.Double(e.getX()-13, e.getY()-13, 26, 26);
                            
                            //Moving Point 1
                            editor.managedToolPoints.forEach((ToolPoint point) -> {
                                if ( movingPoint == false) {
                                    point1 = new Point2D.Double(point.x, point.y);
                                    
                                    if (selectionCircle.contains(point1) && movingPoint == false) {
                                        x1 = point.x;
                                        y1 = point.y;
                                        identifier = point.getIdentifier();
                                        movingPoint = true;
                                        movementInitiated = true;
                                    }
                                }
                            });
                            
                            //Moving Line 1
                            editor.drawLine.forEach((LineFeature line) -> {
                                if ( movingLine == false && movingPoint == false) {
                                    point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
                                    point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);
                                    
                                    if (selectionCircle.contains(point1) && movingLine == false) {
                                        x1 = line.lineElements[0].x;
                                        y1 = line.lineElements[0].y;
                                        x2 = line.lineElements[1].x;
                                        y2 = line.lineElements[1].y;
                                        identifier = line.getIdentifier();
                                        movingLine = true;
                                        movementInitiated = true;
                                        isStart = true;
                                        
                                    } else if (selectionCircle.contains(point2) && movingLine == false) {
                                        x1 = line.lineElements[1].x;
                                        y1 = line.lineElements[1].y;
                                        x2 = line.lineElements[0].x;
                                        y2 = line.lineElements[0].y;
                                        identifier = line.getIdentifier();
                                        movingLine = true;
                                        movementInitiated = true;
                                        isEnd = true;
                                    }
                                }
                            });
                            
                            //Moving Triangle 1
                            editor.drawTriangle.forEach((Triangle triangle) -> {
                                if ( movingTriangle == false && movingPoint == false && movingLine == false) {
                                    point1 = new Point2D.Double(triangle.triangleElements[0].x, triangle.triangleElements[0].y);
                                    point2 = new Point2D.Double(triangle.triangleElements[1].x, triangle.triangleElements[1].y);
                                    point3 = new Point2D.Double(triangle.triangleElements[2].x, triangle.triangleElements[2].y);
                                    
                                    if (selectionCircle.contains(point1) && movingTriangle == false) {
                                        x1 = triangle.triangleElements[0].x;
                                        y1 = triangle.triangleElements[0].y;
                                        x2 = triangle.triangleElements[1].x;
                                        y2 = triangle.triangleElements[1].y;
                                        x3 = triangle.triangleElements[2].x;
                                        y3 = triangle.triangleElements[2].y;
                                        identifier = triangle.getIdentifier();
                                        movingTriangle = true;
                                        movementInitiated = true;
                                        isStart = true;
                                        
                                    } else if (selectionCircle.contains(point2) && movingTriangle == false) {
                                        x1 = triangle.triangleElements[1].x;
                                        y1 = triangle.triangleElements[1].y;
                                        x2 = triangle.triangleElements[2].x;
                                        y2 = triangle.triangleElements[2].y;
                                        x3 = triangle.triangleElements[0].x;
                                        y3 = triangle.triangleElements[0].y;
                                        identifier = triangle.getIdentifier();
                                        movingTriangle = true;
                                        movementInitiated = true;
                                        isMiddle = true;
                                        
                                    } else if (selectionCircle.contains(point3) && movingTriangle == false) {
                                        x1 = triangle.triangleElements[0].x;
                                        y1 = triangle.triangleElements[0].y;
                                        x2 = triangle.triangleElements[1].x;
                                        y2 = triangle.triangleElements[1].y;
                                        x3 = triangle.triangleElements[2].x;
                                        y3 = triangle.triangleElements[2].y;
                                        identifier = triangle.getIdentifier();
                                        movingTriangle = true;
                                        movementInitiated = true;
                                        isEnd = true;
                                        
                                    }
                                }
                            });
                            
                            //Moving Rectangles 1
                            editor.drawRectangle.forEach((ToolRectangle rectangle) -> {
                                if ( movingRectangle == false && movingTriangle == false && movingPoint == false && movingLine == false) {
                                    point1 = new Point2D.Double(rectangle.rectangleElements[0].x, rectangle.rectangleElements[0].y);
                                    point2 = new Point2D.Double(rectangle.rectangleElements[1].x, rectangle.rectangleElements[1].y);
                                    
                                    if (selectionCircle.contains(point1) && movingRectangle == false) {
                                        x1 = rectangle.rectangleElements[0].x;
                                        y1 = rectangle.rectangleElements[0].y;
                                        x2 = rectangle.rectangleElements[1].x;
                                        y2 = rectangle.rectangleElements[1].y;
                                        identifier = rectangle.getIdentifier();
                                        movingRectangle = true;
                                        movementInitiated = true;
                                        isStart = true;
                                        
                                    } else if ( selectionCircle.contains(point2) && movingRectangle == false){
                                        x1 = rectangle.rectangleElements[1].x;
                                        y1 = rectangle.rectangleElements[1].y;
                                        x2 = rectangle.rectangleElements[0].x;
                                        y2 = rectangle.rectangleElements[0].y;
                                        identifier = rectangle.getIdentifier();
                                        movingRectangle = true;
                                        movementInitiated = true;
                                        isEnd = true;
                                    }
                                }
                            });
                            break;
                            
                            
                        } else if ( movementInitiated == true) {
                            int moveDestinationX = e.getX();
                            int moveDestinationY = e.getY();
                            int moveDifferenceX = moveStartX - moveDestinationX;
                            int moveDifferenceY = moveStartY - moveDestinationY;
                            
                            //Moving Point 2
                            if ( movingPoint == true) {
                                for ( int i=0; i<editor.managedToolPoints.size(); i++) {
                                    int matchIdentifier = editor.managedToolPoints.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier){
                                        objectArrayPosition = i;
                                        editor.managedToolPoints.get(i).x = moveDestinationX;
                                        editor.managedToolPoints.get(i).y = moveDestinationY;
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                        movingPoint = false;
                                        movementInitiated = false;
                                    }
                                }
                                
                            }
                            
                            //Moving Line 2
                            if ( movingLine == true) {
                                for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                    int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier) {
                                        objectArrayPosition = i;
                                        editor.managedToolLines.get(i).lineElements[0].x = x1 - moveDifferenceX;
                                        editor.managedToolLines.get(i).lineElements[0].y = y1 - moveDifferenceY;
                                        editor.managedToolLines.get(i).lineElements[1].x = x2 - moveDifferenceX;
                                        editor.managedToolLines.get(i).lineElements[1].y = y2 - moveDifferenceY;
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .clearDrawingElements();
                                        drawingpanel
                                                .repaint();
                                        movingLine = false;
                                        movementInitiated = false;
                                        
                                    }
                                }
                                
                            }
                            
                            //Moving Triangle 2
                            if ( movingTriangle == true) {
                                for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                    int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier) {
                                        objectArrayPosition = i;
                                        editor.managedToolTriangles.get(i).triangleElements[0].x = x1 - moveDifferenceX;
                                        editor.managedToolTriangles.get(i).triangleElements[0].y = y1 - moveDifferenceY;
                                        editor.managedToolTriangles.get(i).triangleElements[1].x = x2 - moveDifferenceX;
                                        editor.managedToolTriangles.get(i).triangleElements[1].y = y2 - moveDifferenceY;
                                        editor.managedToolTriangles.get(i).triangleElements[2].x = x3 - moveDifferenceX;
                                        editor.managedToolTriangles.get(i).triangleElements[2].y = y3 - moveDifferenceY;
                                        
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                        movingTriangle = false;
                                        movementInitiated = false;
                                        
                                    }
                                }
                                
                            }
                            
                            //Moving Rectangles 2
                            if ( movingRectangle == true) {
                                for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                    int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier){
                                        objectArrayPosition = i;
                                        editor.drawRectangle.get(i).rectangleElements[0].x = x1 - moveDifferenceX;
                                        editor.drawRectangle.get(i).rectangleElements[0].y = y1 - moveDifferenceY;
                                        editor.drawRectangle.get(i).rectangleElements[1].x = x2 - moveDifferenceX;
                                        editor.drawRectangle.get(i).rectangleElements[1].y = y2 - moveDifferenceY;
                                    }
                                }
                                drawingpanel
                                        .requestToolObjectLists(editor);
                                drawingpanel
                                        .repaint();
                                movingRectangle = false;
                                movementInitiated = false;
                            }
                            isStart = false;
                            isMiddle = false;
                            isEnd = false;
                            break;
                        }
                        
                    case "ChangeMode":
                        moveStartX = e.getX();
                        moveStartY = e.getY();
                        
                        if ( changeInitiated == false) {
                            selectionCircle = new Ellipse2D.Double(e.getX()-13, e.getY()-13, 26, 26);
                            
                            //Changing Points
                            editor.managedToolPoints.forEach((ToolPoint point) -> {
                                if ( movingPoint == false) {
                                    point1 = new Point2D.Double(point.x, point.y);
                                    
                                    if (selectionCircle.contains(point1) && movingPoint == false) {
                                        x1 = point.x;
                                        y1 = point.y;
                                        identifier = point.getIdentifier();
                                        movingPoint = true;
                                        changeInitiated = true;
                                    }
                                }
                            });
                            
                            //Changing Line
                            editor.managedToolLines.forEach((ToolLine line) -> {
                                if ( movingLine == false && movingPoint == false) {
                                    point1 = new Point2D.Double(line.lineElements[0].x, line.lineElements[0].y);
                                    point2 = new Point2D.Double(line.lineElements[1].x, line.lineElements[1].y);
                                    
                                    if (selectionCircle.contains(point1) && movingLine == false) {
                                        identifier = line.getIdentifier();
                                        isStart = true;
                                        movingLine = true;
                                        changeInitiated = true;
                                        
                                    } else if (selectionCircle.contains(point2) && movingLine == false) {
                                        identifier = line.getIdentifier();
                                        isStart = false;
                                        isEnd = true;
                                        movingLine = true;
                                        changeInitiated = true;
                                    }
                                }
                            });
                            
                            //Changing Triangle
                            editor.managedToolTriangles.forEach((ToolTriangle triangle) -> {
                                if ( movingLine == false && movingPoint == false && movingTriangle== false) {
                                    point1 = new Point2D.Double(triangle.triangleElements[0].x, triangle.triangleElements[0].y);
                                    point2 = new Point2D.Double(triangle.triangleElements[1].x, triangle.triangleElements[1].y);
                                    point3 = new Point2D.Double(triangle.triangleElements[2].x, triangle.triangleElements[2].y);
                                    
                                    if (selectionCircle.contains(point1) && movingTriangle == false) {
                                        identifier = triangle.getIdentifier();
                                        isStart = true;
                                        movingTriangle = true;
                                        changeInitiated = true;
                                        
                                    } else if (selectionCircle.contains(point2) && movingTriangle == false) {
                                        identifier = triangle.getIdentifier();
                                        isMiddle = true;
                                        movingTriangle = true;
                                        changeInitiated = true;
                                        
                                    } else if (selectionCircle.contains(point3) && movingTriangle == false) {
                                        identifier = triangle.getIdentifier();
                                        isEnd = true;
                                        movingTriangle = true;
                                        changeInitiated = true;
                                    }
                                }
                            });
                            
                            editor.drawRectangle.forEach((ToolRectangle rectangle) -> {
                                if ( movingLine == false && movingPoint == false && movingTriangle== false && movingRectangle == false) {
                                    point1 = new Point2D.Double(rectangle.rectangleElements[0].x, rectangle.rectangleElements[0].y);
                                    point2 = new Point2D.Double(rectangle.rectangleElements[1].x, rectangle.rectangleElements[1].y);
                                    
                                    if (selectionCircle.contains(point1) && movingRectangle == false) {
                                        identifier = rectangle.getIdentifier();
                                        isStart = true;
                                        movingRectangle = true;
                                        changeInitiated = true;
                                        
                                    } else if (selectionCircle.contains(point2) && movingRectangle == false) {
                                        identifier = rectangle.getIdentifier();
                                        isEnd = true;
                                        movingRectangle = true;
                                        changeInitiated = true;
                                    }
                                }
                            });
                            break;
                        }
                        
                        if ( changeInitiated == true) {
                            int moveDestinationX = e.getX();
                            int moveDestinationY = e.getY();
                            
                            if ( movingPoint == true) {
                                for ( int i=0; i<editor.managedToolPoints.size(); i++) {
                                    int matchIdentifier = editor.managedToolPoints.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier){
                                        objectArrayPosition = i;
                                        editor.managedToolPoints.get(i).x = moveStartX;
                                        editor.managedToolPoints.get(i).y = moveStartY;
                                        
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                        movingPoint = false;
                                        changeInitiated = false;
                                    }
                                }
                                
                            }
                            
                            if (movingLine == true) {
                                if ( isStart == true) {
                                    for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                        int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            objectArrayPosition = i;
                                            editor.managedToolLines.get(i).lineElements[0].x = moveDestinationX;
                                            editor.managedToolLines.get(i).lineElements[0].y = moveDestinationY;
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .clearDrawingElements();
                                            drawingpanel
                                                    .repaint();
                                            movingLine = false;
                                            changeInitiated = false;
                                        }
                                        
                                    }
                                }
                                
                                if (movingLine == true) {
                                    
                                    if ( isStart == false) {
                                        for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                            int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                            
                                            if (identifier == matchIdentifier) {
                                                objectArrayPosition = i;
                                                editor.managedToolLines.get(i).lineElements[1].x = moveDestinationX;
                                                editor.managedToolLines.get(i).lineElements[1].y = moveDestinationY;
                                                
                                                drawingpanel
                                                        .requestToolObjectLists(editor);
                                                drawingpanel
                                                        .clearDrawingElements();
                                                drawingpanel
                                                        .repaint();
                                                movingLine = false;
                                                changeInitiated = false;
                                            }
                                        }
                                    }
                                    
                                }
                            }
                            
                            if (movingTriangle == true) {
                                
                                if (isStart == true && isMiddle == false && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            objectArrayPosition = i;
                                            editor.managedToolTriangles.get(i).triangleElements[0].x = moveDestinationX;
                                            editor.managedToolTriangles.get(i).triangleElements[0].y = moveDestinationY;
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            movingTriangle = false;
                                            changeInitiated = false;
                                            
                                        }
                                    }
                                }
                                
                                if (isMiddle == true && isStart == false && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            objectArrayPosition = i;
                                            editor.managedToolTriangles.get(i).triangleElements[1].x = moveDestinationX;
                                            editor.managedToolTriangles.get(i).triangleElements[1].y = moveDestinationY;
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            movingTriangle = false;
                                            changeInitiated = false;
                                        }
                                    }
                                }
                                
                                if (isEnd == true && isMiddle == false && isStart == false) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            objectArrayPosition = i;
                                            editor.managedToolTriangles.get(i).triangleElements[2].x = moveDestinationX;
                                            editor.managedToolTriangles.get(i).triangleElements[2].y = moveDestinationY;
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            movingTriangle = false;
                                            changeInitiated = false;
                                        }
                                    }
                                }
                            }
                            
                            if ( movingRectangle == true) {
                                
                                if (isStart == true && isEnd == false) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            objectArrayPosition = i;
                                            editor.drawRectangle.get(i).rectangleElements[0].x = moveDestinationX;
                                            editor.drawRectangle.get(i).rectangleElements[0].y = moveDestinationY;
                                        }
                                    }
                                    drawingpanel
                                            .requestToolObjectLists(editor);
                                    drawingpanel
                                            .repaint();
                                    movingRectangle = false;
                                    changeInitiated = false;
                                }
                                
                                if (isEnd == true && isStart == false) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            objectArrayPosition = i;
                                            editor.drawRectangle.get(i).rectangleElements[1].x = moveDestinationX;
                                            editor.drawRectangle.get(i).rectangleElements[1].y = moveDestinationY;
                                            
                                        }
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                        movingRectangle = false;
                                        changeInitiated = false;
                                    }
                                    
                                }
                                
                            }
                            
                            isStart = false;
                            isMiddle = false;
                            isEnd = false;
                        }
                }
            }
        });
        
        drawingpanel
                .addMouseMotionListener(new MouseAdapter() {
                    
                    //Live display when creating or editing displayed objects (for better user feedback only)
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        trackedX = e.getX();
                        trackedY = e.getY();
                        trackedCoord.setText("X: " + trackedX + "    Y: " + trackedY + "   ");
                        point = new ToolPoint();
                        point.setPoint(e.getX(), e.getY());
                        
                        if ( lineInitiated == true) {
                            
                            /*
                            * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.managedToolLines.size() >=1) {
                            editor.managedToolLines.remove(editor.managedToolLines.size()-1);
                            }
                            
                            line.addLineEnd(point);
                            editor.storeLineElements(line);
                            drawingpanel
                            .requestToolObjectLists(editor);
                            drawingpanel
                            .repaint();
                            */
                            
                            line.addLineEnd(point);
                            drawingpanel
                                    .storeDrawingLineElements(line);
                            drawingpanel
                                    .repaint();
                        }
                        
                        if ( triangleInitiated1 == true && triangleInitiated2 == false) {
                            
                            /*
                            * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.managedToolTriangles.size() >=1) {
                            editor.managedToolTriangles.remove(editor.managedToolTriangles.size()-1);
                            }
                            
                            triangle.addTriangleMid(point);
                            triangle.addTriangleEnd(point);
                            editor.storeTriangleElements(triangle);
                            drawingpanel
                            .requestToolObjectLists(editor);
                            drawingpanel
                            .repaint();
                            */
                            
                            triangle.addTriangleMid(point);
                            triangle.addTriangleEnd(point);
                            drawingpanel
                                    .storeDrawingTriangleElements(triangle);
                            drawingpanel
                                    .repaint();
                        }
                        
                        if ( triangleInitiated2 == true && triangleInitiated1 == false) {
                            
                            /*
                            * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.managedToolTriangles.size() >=1) {
                            editor.managedToolTriangles.remove(editor.managedToolTriangles.size()-1);
                            }
                            
                            if ( triangle.triangleElements[2] != null) {
                            triangle.triangleElements[2] = null;
                            }
                            triangle.addTriangleEnd(point);
                            editor.storeTriangleElements(triangle);
                            drawingpanel
                            .requestToolObjectLists(editor);
                            drawingpanel
                            .repaint();
                            */
                            
                            triangle.addTriangleEnd(point);
                            drawingpanel
                                    .storeDrawingTriangleElements(triangle);
                            drawingpanel
                                    .repaint();
                            
                        }
                        
                        if ( rectangleInitiated == true) {
                            
                            /*
                            * DEPRECATED VERSION - see Module Integration Test
                            if ( editor.drawRectangle.size() >=1) {
                            editor.drawRectangle.remove(editor.drawRectangle.size()-1);
                            }
                            
                            rectangle.addRectangleLastCorner(point);
                            editor.storeRectangleElements(rectangle);
                            drawingpanel
                            .requestToolObjectLists(editor);
                            drawingpanel
                            .repaint();
                            */
                            
                            rectangle.addRectangleLastCorner(point);
                            drawingpanel
                                    .storeDrawingRectangleElements(rectangle);
                            drawingpanel
                                    .repaint();
                            
                        }
                        
                        if ( selectionInitiated == true) {
                            selectionX2 = point.x;
                            selectionY2 = point.y;
                            
                            //Determine 'height' and 'width' of the Selection Rectangle
                            double rectangleWidth = Math.abs(selectionX1-selectionX2);
                            double rectangleHeight = Math.abs(selectionY1-selectionY2);
                            
                            double rectangleStart;
                            double rectangleEnd;
                            
                            //Determine 'top-left' and 'bottom-right' Point of the Selection Rectangle
                            if( selectionX2 > selectionX1) {
                                rectangleStart = selectionX1;
                            } else {
                                rectangleStart = selectionX2;
                            }
                            if( selectionY2 > selectionY1) {
                                rectangleEnd = selectionY1;
                            } else {
                                rectangleEnd = selectionY2;
                            }
                            
                            //Compose Selection Rectangle
                            Rectangle2D selectionRectangle = new Rectangle2D.Double();
                            selectionRectangle.setRect(rectangleStart, rectangleEnd, rectangleWidth, rectangleHeight);
                            drawingpanel
                                    .defineSelectionRectangle(selectionRectangle);
                            drawingpanel
                                    .requestToolObjectLists(editor);
                            drawingpanel
                                    .repaint();
                        }
                        
                        /*
                        * Live display of 'move'-Tool starting
                        * [Added in the course of the Module Integration Test]
                        * @author heol1015
                        */
                        if ( movementInitiated == true && changeInitiated == false) {
                            
                            if (movingPoint == true) {
                                for (int i = 0; i<editor.managedToolPoints.size(); i++) {
                                    int matchIdentifier = editor.managedToolPoints.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier) {
                                        editor.managedToolPoints.get(i).x = point.x;
                                        editor.managedToolPoints.get(i).y = point.y;
                                        
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                    }
                                }
                            }
                            
                            if ( movingLine == true) {
                                //If selected line point was the first line element
                                if (isStart == true && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                        int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            double diffX = editor.managedToolLines.get(i).lineElements[0].x - editor.managedToolLines.get(i).lineElements[1].x;
                                            double diffY = editor.managedToolLines.get(i).lineElements[0].y - editor.managedToolLines.get(i).lineElements[1].y;
                                            
                                            editor.managedToolLines.get(i).addLineStart(point);
                                            ToolPoint newPoint = new ToolPoint();
                                            newPoint.x = editor.managedToolLines.get(i).lineElements[0].x - diffX;
                                            newPoint.y = editor.managedToolLines.get(i).lineElements[0].y - diffY;
                                            editor.managedToolLines.get(i).addLineEnd(newPoint);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                                
                                //If selected line point was the last line element
                                if (isStart == false && isEnd == true) {
                                    for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                        int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            double diffX = editor.managedToolLines.get(i).lineElements[0].x - editor.managedToolLines.get(i).lineElements[1].x;
                                            double diffY = editor.managedToolLines.get(i).lineElements[0].y - editor.managedToolLines.get(i).lineElements[1].y;
                                            
                                            editor.managedToolLines.get(i).addLineEnd(point);
                                            ToolPoint newPoint = new ToolPoint();
                                            newPoint.x = editor.managedToolLines.get(i).lineElements[1].x + diffX;
                                            newPoint.y = editor.managedToolLines.get(i).lineElements[1].y + diffY;
                                            editor.managedToolLines.get(i).addLineStart(newPoint);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                                
                            }
                            
                            if ( movingTriangle == true) {
                                //If selected triangle point was the first triangle element
                                if ( isStart == true && isMiddle == false && isEnd == false) {
                                    
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            double diffX1 = editor.managedToolTriangles.get(i).triangleElements[0].x - editor.managedToolTriangles.get(i).triangleElements[1].x;
                                            double diffY1 = editor.managedToolTriangles.get(i).triangleElements[0].y - editor.managedToolTriangles.get(i).triangleElements[1].y;
                                            double diffX2 = editor.managedToolTriangles.get(i).triangleElements[0].x - editor.managedToolTriangles.get(i).triangleElements[2].x;
                                            double diffY2 = editor.managedToolTriangles.get(i).triangleElements[0].y - editor.managedToolTriangles.get(i).triangleElements[2].y;
                                            
                                            editor.managedToolTriangles.get(i).addTriangleStart(point);
                                            ToolPoint newPoint1 = new ToolPoint();
                                            ToolPoint newPoint2 = new ToolPoint();
                                            newPoint1.x = editor.managedToolTriangles.get(i).triangleElements[0].x - diffX1;
                                            newPoint1.y = editor.managedToolTriangles.get(i).triangleElements[0].y - diffY1;
                                            newPoint2.x = editor.managedToolTriangles.get(i).triangleElements[0].x - diffX2;
                                            newPoint2.y = editor.managedToolTriangles.get(i).triangleElements[0].y - diffY2;
                                            editor.managedToolTriangles.get(i).addTriangleMid(newPoint1);
                                            editor.managedToolTriangles.get(i).addTriangleEnd(newPoint2);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                                
                                //If selected triangle point was the middle triangle element
                                if ( isStart == false && isMiddle == true && isEnd == false) {
                                    
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            double diffX1 = editor.managedToolTriangles.get(i).triangleElements[1].x - editor.managedToolTriangles.get(i).triangleElements[0].x;
                                            double diffY1 = editor.managedToolTriangles.get(i).triangleElements[1].y - editor.managedToolTriangles.get(i).triangleElements[0].y;
                                            double diffX2 = editor.managedToolTriangles.get(i).triangleElements[1].x - editor.managedToolTriangles.get(i).triangleElements[2].x;
                                            double diffY2 = editor.managedToolTriangles.get(i).triangleElements[1].y - editor.managedToolTriangles.get(i).triangleElements[2].y;
                                            
                                            editor.managedToolTriangles.get(i).addTriangleMid(point);
                                            ToolPoint newPoint1 = new ToolPoint();
                                            ToolPoint newPoint2 = new ToolPoint();
                                            newPoint1.x = editor.managedToolTriangles.get(i).triangleElements[1].x - diffX1;
                                            newPoint1.y = editor.managedToolTriangles.get(i).triangleElements[1].y - diffY1;
                                            newPoint2.x = editor.managedToolTriangles.get(i).triangleElements[1].x - diffX2;
                                            newPoint2.y = editor.managedToolTriangles.get(i).triangleElements[1].y - diffY2;
                                            editor.managedToolTriangles.get(i).addTriangleStart(newPoint1);
                                            editor.managedToolTriangles.get(i).addTriangleEnd(newPoint2);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                                
                                //If selected triangle point was the last triangle element
                                if ( isStart == false && isMiddle == false && isEnd == true) {
                                    
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            double diffX1 = editor.managedToolTriangles.get(i).triangleElements[2].x - editor.managedToolTriangles.get(i).triangleElements[0].x;
                                            double diffY1 = editor.managedToolTriangles.get(i).triangleElements[2].y - editor.managedToolTriangles.get(i).triangleElements[0].y;
                                            double diffX2 = editor.managedToolTriangles.get(i).triangleElements[2].x - editor.managedToolTriangles.get(i).triangleElements[1].x;
                                            double diffY2 = editor.managedToolTriangles.get(i).triangleElements[2].y - editor.managedToolTriangles.get(i).triangleElements[1].y;
                                            
                                            editor.managedToolTriangles.get(i).addTriangleEnd(point);
                                            ToolPoint newPoint1 = new ToolPoint();
                                            ToolPoint newPoint2 = new ToolPoint();
                                            newPoint1.x = editor.managedToolTriangles.get(i).triangleElements[2].x - diffX1;
                                            newPoint1.y = editor.managedToolTriangles.get(i).triangleElements[2].y - diffY1;
                                            newPoint2.x = editor.managedToolTriangles.get(i).triangleElements[2].x - diffX2;
                                            newPoint2.y = editor.managedToolTriangles.get(i).triangleElements[2].y - diffY2;
                                            editor.managedToolTriangles.get(i).addTriangleMid(newPoint1);
                                            editor.managedToolTriangles.get(i).addTriangleStart(newPoint2);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                            }
                            
                            if (movingRectangle == true) {
                                
                                //If selected rectangle point was the first corner of the rectangle element
                                if (isStart == true && isEnd == false) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            double diffX1 = editor.drawRectangle.get(i).rectangleElements[0].x - editor.drawRectangle.get(i).rectangleElements[1].x;
                                            double diffY1 = editor.drawRectangle.get(i).rectangleElements[0].y - editor.drawRectangle.get(i).rectangleElements[1].y;
                                            
                                            editor.drawRectangle.get(i).addRetangleFirstCorner(point);
                                            ToolPoint newPoint = new ToolPoint();
                                            newPoint.x = editor.drawRectangle.get(i).rectangleElements[0].x - diffX1;
                                            newPoint.y = editor.drawRectangle.get(i).rectangleElements[0].y - diffY1;
                                            editor.drawRectangle.get(i).addRectangleLastCorner(newPoint);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                                
                                //If selected rectangle point was the last corner of the rectangle element
                                if (isStart == false && isEnd == true) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            double diffX1 = editor.drawRectangle.get(i).rectangleElements[1].x - editor.drawRectangle.get(i).rectangleElements[0].x;
                                            double diffY1 = editor.drawRectangle.get(i).rectangleElements[1].y - editor.drawRectangle.get(i).rectangleElements[0].y;
                                            
                                            editor.drawRectangle.get(i).addRectangleLastCorner(point);
                                            ToolPoint newPoint = new ToolPoint();
                                            newPoint.x = editor.drawRectangle.get(i).rectangleElements[1].x - diffX1;
                                            newPoint.y = editor.drawRectangle.get(i).rectangleElements[1].y - diffY1;
                                            editor.drawRectangle.get(i).addRetangleFirstCorner(newPoint);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                            }
                            
                        }
                        
                        /*
                        * Live display of 'change'-Tool starting
                        * [Added in the course of the Module Integration Test]
                        * @author heol1015
                        */
                        if ( changeInitiated == true && movementInitiated == false) {
                            
                            if (movingPoint == true) {
                                for (int i = 0; i<editor.managedToolPoints.size(); i++) {
                                    int matchIdentifier = editor.managedToolPoints.get(i).identifier;
                                    
                                    if (identifier == matchIdentifier) {
                                        editor.managedToolPoints.get(i).x = point.x;
                                        editor.managedToolPoints.get(i).y = point.y;
                                        drawingpanel
                                                .requestToolObjectLists(editor);
                                        drawingpanel
                                                .repaint();
                                    }
                                }
                            }
                            
                            if (movingLine == true) {
                                //If selected line point was the first line element
                                if ( isStart == true && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                        int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            editor.managedToolLines.get(i).addLineStart(point);
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                                
                                //If selected line point was the last line element
                                if ( isEnd == true && isStart == false) {
                                    for (int i = 0; i<editor.managedToolLines.size(); i++) {
                                        int matchIdentifier = editor.managedToolLines.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            editor.managedToolLines.get(i).addLineEnd(point);
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                            }
                            
                            if (movingTriangle == true) {
                                //If selected triangle point was the first triangle element
                                if ( isStart == true && isMiddle == false && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            editor.managedToolTriangles.get(i).addTriangleStart(point);
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                                
                                //If selected triangle point was the middle triangle element
                                if ( isStart == false && isMiddle == true && isEnd == false) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            editor.managedToolTriangles.get(i).addTriangleMid(point);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                                
                                //If selected triangle point was the last triangle element
                                if ( isStart == false && isMiddle == false && isEnd == true) {
                                    for (int i = 0; i<editor.managedToolTriangles.size(); i++) {
                                        int matchIdentifier = editor.managedToolTriangles.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier) {
                                            
                                            editor.managedToolTriangles.get(i).addTriangleEnd(point);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                            
                                        }
                                    }
                                }
                            }
                            
                            if (movingRectangle == true) {
                                //If selected rectangle point was the first corner of rectangle element
                                if (isStart == true && isEnd == false) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            editor.drawRectangle.get(i).addRetangleFirstCorner(point);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                                
                                //If selected rectangle point was the last corner of rectangle element
                                if (isStart == false && isEnd == true) {
                                    for ( int i=0; i<editor.drawRectangle.size(); i++) {
                                        int matchIdentifier = editor.drawRectangle.get(i).identifier;
                                        
                                        if (identifier == matchIdentifier){
                                            editor.drawRectangle.get(i).addRectangleLastCorner(point);
                                            
                                            drawingpanel
                                                    .requestToolObjectLists(editor);
                                            drawingpanel
                                                    .repaint();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }
    
    /**
     * Creates a new object of CSVInterface() which opens a new frame for
     * exporting objects as ".csv" or importing objects from a ".csv".
     * After that, some general methods for displaying a frame are applied.
     * @author heol1015
     */
    public void openCsvInterface() {
        csvinterface = new CSVInterface();
        csvinterface.setTitle("CSV Interface");
        csvinterface.setLocationRelativeTo(null);
        csvinterface.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        csvinterface.setVisible(true);
        
    }
    
    /**
     * Creates a new object of DBInterface() which opens a new frame for
     * exporting objects to a connected database or importing objects from a connected database.
     * After that, some general methods for displaying a frame are applied.
     * @author heol1015
     */
    public void openDbInterface() {
        dbinterface = new DBInterface();
        dbinterface.setTitle("DB Interface");
        dbinterface.setLocationRelativeTo(null);
        dbinterface.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dbinterface.setVisible(true);
        
    }
    
    /**
     * Replaces the @param editor with the contents of the @param neweditor
     * @author heol1015
     * @param neweditor
     */
    public void overwriteObjects(editor neweditor) {
        this.editor = neweditor;
        drawingpanel
                .requestToolObjectLists(editor);
        drawingpanel
                .repaint();
    }
    
    /**
     * Sets the Tooldrawingpanel
     * as ContentPane.
     * Adds the MenuBar and the ToolBar including the corresponding items and
     * defines the appearance of these elements.
     * Applies general methods for displaying a frame.
     * @author heol1015
     */
    public void setLayout() {
        
        //Set ContentPane
        setContentPane(drawingpanel
        );
        
        //Set MenuBar
        menubar 	= new JMenuBar();
        datamenu 	= new JMenu("Data");
        toolabout 	= new JMenu("About");
        csv 		= new JMenuItem("Import/Export .csv");
        db			= new JMenuItem("Database Manager");
        
        datamenu.add(csv);
        datamenu.add(db);
        menubar.add(datamenu);
        menubar.add(toolabout);
        setJMenuBar(menubar);
        
        //Coordinate Tracking
        menubar.add(Box.createHorizontalGlue());
        trackedCoord = new JLabel("[mouse deactivated]");
        trackedMode	 = new JLabel("[mode deactivated]    |    ");
        menubar.add(trackedMode);
        menubar.add(trackedCoord);
        
        trackedCoord.setHorizontalAlignment(JLabel.RIGHT);
        
        //Set Buttons
        drawPoint 	= new JButton("Point");
        drawPoint.setBackground(Color.decode("#f8f8ff"));
        drawLine	= new JButton("Line");
        drawLine.setBackground(Color.decode("#f8f8ff"));
        drawTriangle	= new JButton("Triangle");
        drawTriangle.setBackground(Color.decode("#f8f8ff"));
        drawRectangle	= new JButton("Rectangle");
        drawRectangle.setBackground(Color.decode("#f8f8ff"));
        selectElements	= new JButton("Select");
        selectElements.setBackground(Color.decode("#b3ccff"));
        deleteElements 	= new JButton("Delete");
        deleteElements.setBackground(Color.decode("#ffb3b3"));
        moveElements 	= new JButton("Move");
        moveElements.setBackground(Color.decode("#b3ffb3"));
        changeElements 	= new JButton("Change");
        changeElements.setBackground(Color.decode("#ffff80"));
        
        //Buttons add ActionListeners
        drawPoint.addActionListener(this);
        drawLine.addActionListener(this);
        drawTriangle.addActionListener(this);
        drawRectangle.addActionListener(this);
        selectElements.addActionListener(this);
        deleteElements.addActionListener(this);
        moveElements.addActionListener(this);
        changeElements.addActionListener(this);
        
        csv.addActionListener(this);
        db.addActionListener(this);
        
        //Set ToolBar
        toolbar = new JToolBar();
        toolbar.add(drawPoint);
        toolbar.add(drawLine);
        toolbar.add(drawTriangle);
        toolbar.add(drawRectangle);
        toolbar.add(selectElements);
        toolbar.add(deleteElements);
        toolbar.add(moveElements);
        toolbar.add(changeElements);
        
        toolBox = Box.createHorizontalBox();
        toolBox.add(toolbar);
        add(toolBox);
        
        // General Frame Settings
        setTitle("Drawer");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        
    }
    
    /**
     * Displays an OptionPane with advice about how to use this software.
     * @author heol1015
     */
    public void showUserGuideDialog() {
        
        //Shows initial OptionPane
        JOptionPane.showMessageDialog(frame,
                "User guide:\n" +
                        "- Choose a drawing mode by clicking on the corresponding button in the toolbar.\n" +
                        "- Return to 'default mode' by clicking on the same button again. \n" +
                        "- You can connect this software to a Database.\n" +
                        "- You can import and export your result from/to '.csv'.\n" +
                        "\n" +
                        "- Works best with MySql© database\n" +
                        "- For bug reporting and proposals contact: 'oliver.hennhoefer@mail.de'",
                "Intro Tutorial",
                JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Initializes a new instance of MainClass.
     * Sets the defined layout and displays an OptionPane with advice on how to use this software.
     * @author heol1015
     * @param args
     */
    public static void main(String[] args) {
        frame = new MainClass();
        frame.setLayout();
        frame.showUserGuideDialog();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        Object eTarget = e.getSource();
        
        if ( eTarget.equals(drawPoint)) {
            if ( !drawMode.equals("PointMode")) {
                drawMode = "PointMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if ( eTarget.equals(drawLine)) {
            if ( !drawMode.equals("LineMode")) {
                drawMode = "LineMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if ( eTarget.equals(drawTriangle)) {
            if ( !drawMode.equals("TriangleMode")) {
                drawMode = "TriangleMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if ( eTarget.equals(drawRectangle)) {
            if ( !drawMode.equals("RectangleMode")) {
                drawMode = "RectangleMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if ( eTarget.equals(deleteElements)) {
            editor.deleteAffectedToolObjects();
            editor.clearCurrentSelection();
            drawingpanel
                    .defineSelectionRectangle(null);
            drawingpanel
                    .requestToolObjectLists(editor);
            drawingpanel
                    .repaint();
            
        } else if ( eTarget.equals(selectElements)) {
            if ( !drawMode.equals("SelectMode")) {
                drawMode = "SelectMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if( eTarget.equals(moveElements)) {
            if ( !drawMode.equals("MoveMode")) {
                drawMode = "MoveMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if( eTarget.equals(changeElements)) {
            if ( !drawMode.equals("ChangeMode")) {
                drawMode = "ChangeMode";
                trackedMode.setText(drawMode+"    |    ");
            } else {
                drawMode = "default";
                trackedMode.setText(drawMode+"    |    ");
            }
            
        } else if ( eTarget.equals(csv)) {
            openCsvInterface();
        } else if ( eTarget.equals(db)) {
            openDbInterface();
        }
        
    }
    
}

