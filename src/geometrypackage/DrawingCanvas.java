package geometrypackage;

import java.awt.Color;
import java.awt.Graphics; 
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The ToolDrawPanel class requests ArrayLists with geometry objects from an ObjectManager object and displays them.
 * @author 
 *
 */
@SuppressWarnings("serial")
public class DrawingCanvas extends JPanel{
	
	/**
	 * ArrayList of PointFeature objects to be drawn
	 */
	private ArrayList<PointFeature> PointLists = new ArrayList<>();
	/**
	 * ArrayList of LineFeature objects to be drawn
	 */
	private ArrayList<LineFeature> LineLists = new ArrayList<>();
	/**
	 * ArrayList of TriangleFeature objects to be drawn
	 */
	private ArrayList<TriangleFeature> TriangleLists = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature objects to be drawn
	 */
	private ArrayList<RectangleFeature> RectangleLists = new ArrayList<>();
	
	
	
	/**
	 * ArrayList of PointFeature objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<PointFeature> pointList_selection = new ArrayList<>();
	/**
	 * ArrayList of LineFeature objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<LineFeature> lineList_selection = new ArrayList<>();
	/**
	 * ArrayList of TriangleFeature objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<TriangleFeature> triangleList_selection = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature objects that are currently selected (will be drawn in another color for visual feedback)
	 */
	private ArrayList<RectangleFeature> rectangleList_selection = new ArrayList<>();
	
	/*
	 * [Added in the course of the Module Integration Test]
	 * @author 
	 */
	
	/**
	 * ArrayList of the different states of LineFeature objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<LineFeature> Line_drawing = new ArrayList<>();
	/**
	 * ArrayList of the different states of TriangleFeature objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<TriangleFeature> Triangle_drawing = new ArrayList<>();
	/**
	 * ArrayList of the different states of RectangleFeature objects that modified by 'move' or 'selected'
	 * [Added in the course of the Module Integration Test]
	 */
	private ArrayList<RectangleFeature> Rectangle_drawing = new ArrayList<>();
	
	/**
	 * Defines the selection rectangle for displaying it
	 */
	private Rectangle2D selectionRectangle = null;
	
	/**
	 * Replaces the current contents of the ArrayLists in the DrawingCanvas
	 * with the contents of the corresponding ArrayLists in the Editor class
	 * @author 
	 * @param objectmanager
	 */
	public void requestToolObjectLists( Editor editor) {
		//Draw Objects
		PointLists = editor.drawingPoints;
		LineLists = editor.drawingLines;
		TriangleLists = editor.drawingTriangles;
		RectangleLists = editor.drawingRectangles;	
		//Selection Objects
		pointList_selection = editor.selectedPoints;
		lineList_selection = editor.selectedLines;
		triangleList_selection = editor.selectedTriangles;
		rectangleList_selection = editor.selectedRectangles;
	}	
	
	/**
	 * Adds a LineFeature at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author 
	 * @param line
	 */
	public void storeDrawingLineElements(LineFeature line) {
		Line_drawing.add(line);
	}
	
	/**
	 * Adds a TriangleFeature at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author 
	 * @param triangle
	 */
	public void storeDrawingTriangleElements(TriangleFeature triangle) {
		Triangle_drawing.add(triangle);
	}
	
	/**
	 * Adds a RectangleFeature at the end of the corresponding ArrayList.
	 * [Added in the course of the Module Integration Test]
	 * @author 
	 * @param rectangle
	 */
	public void storeDrawingRectangleElements(RectangleFeature rectangle) {
		Rectangle_drawing.add(rectangle);
	}
	
	/**
	 * Clears all ArrayLists for live-displaying objects when drawn.
	 * [Added in the course of the Module Integration Test]
	 * @author 
	 */
	public void clearDrawingElements() {
		Line_drawing.clear();
		Triangle_drawing.clear();
		Rectangle_drawing.clear();
	}
	
	/**
	 * Sets the selection Rectangle
	 * @author 
	 * @param Rectangle
	 */
	public void defineSelectionRectangle(Rectangle2D Rectangle) {
		this.selectionRectangle = Rectangle;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color drawColor = new Color(0, 0, 0, 0.75f);
		g2d.setPaint(drawColor);
		
		//Draw Points
		PointLists.forEach((PointFeature point) -> {
			Ellipse2D pt = point.createPointFeature();
			g2d.fill(pt);
		});
		
		//Draw Lines
		LineLists.forEach((LineFeature line) -> {	
			Line2D ln = line.drawLine();
			g2d.draw(ln);
		});
		
		//Draw Triangles
		TriangleLists.forEach((TriangleFeature triangle) -> {
			Path2D pth_t = triangle.createTriangleFeature();
			g2d.draw(pth_t);
		});
		
		//Draw Rectangles
		RectangleLists.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rct = rectangle.createRectangleFeature();
			g2d.draw(rct);
		});
		
		//Draw Selections
		Color selectionColor = new Color(1, 0, 0, 1f);
		g2d.setPaint(selectionColor);
		
		if ( selectionRectangle != null) {
			g2d.draw(selectionRectangle);
		}
		
		//Draw selected Objects in Red
		pointList_selection.forEach((PointFeature point) -> {
			Ellipse2D ptS = point.createPointFeature();
			g2d.fill(ptS);
		});
		
		lineList_selection.forEach((LineFeature line) -> {
			Line2D lnS = line.drawLine();
			g2d.draw(lnS);
		});
		
		triangleList_selection.forEach((TriangleFeature triangle) -> {
			Path2D trS = triangle.createTriangleFeature();
			g2d.draw(trS);
		});
		
		rectangleList_selection.forEach((RectangleFeature rectangle) -> {
			Rectangle2D rcS = rectangle.createRectangleFeature();
			g2d.draw(rcS);
		});
		
		Color drawingColor = Color.white;
		g2d.setPaint(drawingColor);
		
		//Live Display of states of objects modified by 'move' or 'change'
		Line_drawing.forEach((LineFeature drawLine) -> {
			Line2D dln = drawLine.drawLine();
			g2d.draw(dln);		
		});
		
		Triangle_drawing.forEach((TriangleFeature drawtriangle) -> {
			Path2D dtr = drawtriangle.createTriangleFeature();
			g2d.draw(dtr);		
		});
		
		Rectangle_drawing.forEach((RectangleFeature drawRectangle) -> {
			Rectangle2D drc = drawRectangle.createRectangleFeature();
			g2d.draw(drc);		
		});
							 
	}

}

