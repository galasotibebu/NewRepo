package geometrypackage;

import java.awt.geom.Rectangle2D;

/**
 * Class for RectangleFeatures that stores rectangle coordinates and provides methods for creating drawable rectangle geometries for the ToolDrawPanel,
 * or for importing/exporting the rectangle coordinates.
 * Inherits from ObjectFundamenals. 
 * @author 
 */
public class RectangleFeature extends ShapesParent {
	
	/**
	 * Stores the first and the last PointFeature of a RectangleFeature object
	 */
	public PointFeature[] rectangleElements = new PointFeature[2];
	
	/**
	 * The constructor defines the type of geometry
	 * (see: constructor of ObjectFundamentals)
	 * @author 
	 */
	public RectangleFeature() {		
		super("Rectangle");
	}
	
	/**
	 * Adds the first corner of the Rectangle as the first entry of the List of rectangle elements
	 * @author 
	 * @param point
	 */
	public void addRetangleFirstCorner(PointFeature point) {
		rectangleElements[0] = point;
	}
	
	/**
	 * Adds the last croner of the Rectangle as the first entry of the List of rectangle elements
	 * @author 
	 * @param point
	 */
	public void addRectangleLastCorner(PointFeature point) {
		rectangleElements[1] = point;
	}
	
	/**
	 * Creates a drawable Rectangle2D object with the points of the RectangleFeature object
	 * @author 
	 * @return Drawable rectangle geometry
	 */
	public Rectangle2D createRectangleFeature() {		
		Rectangle2D Rectangle = new Rectangle2D.Double();
		
		double width;
		double height;		
		width = Math.abs(rectangleElements[0].x-rectangleElements[1].x);
		height = Math.abs(rectangleElements[0].y-rectangleElements[1].y);
		
		double rectangleStart;
		double rectangleEnd;
		if( rectangleElements[1].x > rectangleElements[0].x) {
			rectangleStart = rectangleElements[0].x;
		} else {
			rectangleStart = rectangleElements[1].x;
		}
		if( rectangleElements[1].y > rectangleElements[0].y) {
			rectangleEnd = rectangleElements[0].y;
		} else {
			rectangleEnd = rectangleElements[1].y;
		}
		
		Rectangle.setRect(rectangleStart, rectangleEnd, width, height);

		return Rectangle;
	}
	
	/**
	 * Returns the coordinates of the RectangleFeature object as a String
	 * @author 
	 * @return String of coordinates
	 */
	public String getGeometryAsText() {
		String textGeometry 	= 	String.valueOf(this.rectangleElements[0].x) + " " + String.valueOf(this.rectangleElements[0].y) + " " +
									String.valueOf(this.rectangleElements[1].x) + " " + String.valueOf(this.rectangleElements[1].y);
		return textGeometry;	
	}
	
	/**
	 * Sets the Rectangle from a String (provided by database of '.csv').
	 * @author 
	 * @param csvGeometry String containing the rectangle coordinates
	 * @return Whether the operation was successfully operated or not
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");			
			PointFeature startpoint = new PointFeature();
			PointFeature endpoint = new PointFeature();
			
			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			rectangleElements[0] = startpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			rectangleElements[1] = endpoint;
			
			
			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}

}

