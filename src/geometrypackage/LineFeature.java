package geometrypackage;

import java.awt.geom.Line2D;

/**
 * Class for Lines that stores line coordinates and provides methods for creating drawable line geometries for the ToolDrawPanel,
 * or for importing/exporting the line coordinates.
 * Inherits from ObjectFundamenals. 
 * @author 
 */
public class LineFeature extends ShapesParent {
	
	/**
	 * Stores the first and the last PointFeature of a ToolLine object
	 */
	public PointFeature[] lineElements = new PointFeature[2];	
	
	/**
	 * The constructor defines the type of geometry
	 * (see: constructor of ObjectFundamentals)
	 * @author 
	 */
	public LineFeature() {		
		super("Line");
	}
	
	/**
	 * Adds the first point of the line as the first entry of the List of line elements
	 * @author 
	 * @param point
	 */
	public void addLineStart(PointFeature point) {
		lineElements[0] = point;		
	}
	
	/**
	* Adds the second point of the line as the first entry of the List of line elements
	 * @author 
	 * @param point
	 */
	public void addLineEnd(PointFeature point) {
		lineElements[1] = point;	
	}
	
	/**
	 * Creates a drawable Line2D object with the points of the ToolLine object
	 * @author 
	 * @return Drawable line geometry
	 */
	public Line2D drawLine() {
		Line2D line = new Line2D.Double((int)this.lineElements[0].x, (int)this.lineElements[0].y, (int)this.lineElements[1].x, (int)this.lineElements[1].y);
		return line;
	}
	
	/**
	 * Returns the coordinates of the PointFeature object as a String
	 * @author 
	 * @return String of coordinates
	 */
	public String getGeometryAsText() {
		String textGeometry = String.valueOf(this.lineElements[0].x) + " " + String.valueOf(this.lineElements[0].y) + " " + String.valueOf(this.lineElements[1].x) + " " + String.valueOf(this.lineElements[1].y);
		return textGeometry;		
	}
	
	/**
	 * Sets the Line from a String (provided by database of '.csv').
	 * @author 
	 * @param csvGeometry String containing the line coordinates
	 * @return Whether the operation was successfully operated or not
	 */
	public boolean setGeometryFromCSV(String csvGeometry) {
		try {
			String[] coordinates = csvGeometry.split(" ");			
			PointFeature startpoint = new PointFeature();
			PointFeature endpoint = new PointFeature();
			
			startpoint.setPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			lineElements[0] = startpoint;
			endpoint.setPoint(Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
			lineElements[1] = endpoint;
			
			return true;
		} catch (NumberFormatException e) {
			System.err.println("Parsing Error");
			return false;
		}
	}
}

