
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometrypackage;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Editor {

	/**
	 * ArrayList of MainClass
 This MainClass object can grow when a new point is created and shrink
 when a point is deleted.
	 */
	public ArrayList<PointFeature> drawingPoints = new ArrayList<>();

	/**
	 * ArrayList of LineFeature
	 * This LineFeature object can grow when a new Line is created and shrink
	 * when a Line is deleted.
	 */
	public ArrayList<LineFeature> drawingLines = new ArrayList<>();
	/**
	 * ArrayList of LineFeature
	 * This LineFeature object can grow when a new Line is created and shrink
	 * when a Line is deleted.
	 */
	public ArrayList<RectangleFeature> drawingRectangles = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature
	 * This RectangleFeature object can grow when a new Rectangle is created and shrink
	 * when a Rectangle is deleted.
	 */
	public ArrayList<TriangleFeature> drawingTriangles = new ArrayList<>();

	/*..............................................................
    ................Selection.....................................
	 */
	/**
	 * ArrayList of MainClass
 This Points are currently selected.
	 */
	public ArrayList<PointFeature> selectedPoints = new ArrayList<>();

	/**
	 * ArrayList of LineFeature
	 * This Lines are currently selected.
	 */
	public ArrayList<LineFeature> selectedLines = new ArrayList<>();
	/**
	 * ArrayList of RectangleFeature
	 * This Rectangles are are currently selected.
	 */
	public ArrayList<RectangleFeature> selectedRectangles = new ArrayList<>();
	/**
	 * ArrayList of TriangleFeature
	 * This Triangle are currently selected.
	 */
	public ArrayList<TriangleFeature> selectedTriangles = new ArrayList<>();

	/*....................................................................
    .......Deletion.......................................................
	 */

/**
 * Adds a ToolPoint at the end of the corresponding ArrayList.
 * @author 
 * @param toolPoint Type of Geometry
 */
public void addPoint(PointFeature point) {
	drawingPoints.add(point);
}
/**
 * Adds a ToolFeature at the end of the corresponding ArrayList.
 * @author 
 * @param toolline Type of Geometry
 */
public void addLineElements(LineFeature line) {
	drawingLines.add(line);
}
/**
 * Adds a ToolFeature at the end of the corresponding ArrayList.
 * @author 
 * @param toolline Type of Geometry
 */
public void storeLineElements(LineFeature line) {
	drawingLines.add(line);
}

/**
 * Adds a TriangleFeature at the end of the corresponding ArrayList.
 * @author 
 * @param tooltriangle Type of Geometry
 */
public void storeTriangleElements(TriangleFeature triangle) {
	drawingTriangles.add(triangle);
}

/**
 * Adds a ToolRectangle at the end of the corresponding ArrayList.
 * @author 
 * @param toolrectangle Type of Geometry
 */
public void storeRectangleElements(RectangleFeature rectangle) {
	drawingRectangles.add(rectangle);
}

/**
 * Clears the ArrayLists that contain the objects that are added by the selection rectangle
 * @author 
 */
public void clearCurrentSelection() {
	selectedPoints = new ArrayList<>();
	selectedLines = new ArrayList<>();
	selectedTriangles = new ArrayList<>();
	selectedRectangles = new ArrayList<>();
}

/**
 * Iterates through every object of every type of geometry and calculates whether the objects lies
 * inside the selection rectangle or outside.
 * If the objects lies inside the selection rectangle it will be added to the ArrayList of selected objects.
 * The function contains() is provided by Java.
 * @author 
 * @param selection_rectangle Selection rectangle defined by the user
 */
/*public void selectAffectedToolObjects(Rectangle2D selecting_rectangle) {
	PointFeature.forEach((PointFeature point) -> {
		Ellipse2D point_object 		= point.drawPoint();
		Rectangle2D queryArea		= point_object.getBounds2D();

		//if selection_rectangle contains the object then add this object so the selectedToolPoints
		if (selection_rectangle.contains(queryArea) == true) {
			selectedToolPoints.add(point);
		}
	});

	drawLine.forEach((LineFeature line) -> {
		Line2D line_object 			= line.createToolFeature();
		Rectangle2D queryArea		= line_object.getBounds2D();

		//if selection_rectangle contains the object then add this object so the selectedToolFeatures
		if ( selection_rectangle.contains(queryArea) == true) {
			selectedToolFeatures.add(line);
		}
	});

	drawtriangle.forEach((TriangleFeature triangle) -> {
		Path2D triangle_object 		= triangle.createTriangleFeature();
		Rectangle2D queryArea		= triangle_object.getBounds2D();

		//if selection_rectangle contains the object then add this object so the selectedTriangleFeatures
		if (selection_rectangle.contains(queryArea) == true) {
			selectedTriangleFeatures.add(triangle);
		}
	});

	RectangleFeature.forEach((RectangleFeature rectangle) -> {
		Rectangle2D rectangle_object 			= rectangle.createToolRectangle();
		Rectangle2D queryArea		= rectangle_object.getBounds2D();

		//if selection_rectangle contains the object then add this object so the selectedToolRectangles
		if ( selection_rectangle.contains(queryArea) == true) {
			selectedToolRectangles.add(rectangle);
		}
	});
}

/**
 * Iterates through every object of every type of geometry in the ArrayList of the selected objects
 * and requests the id of the objects. After that it iterates through the ArrayLists of objects that
 * are currently displayed and deletes objects with the same id as the object in the ArrayLists of selected objects.
 * @author 
 */
public void deleteAffectedToolObjects() {
	selectedPoints.forEach((PointFeature point) -> {
		int identifier = point.getShapesId();
		deletePoint(identifier);
	});

	selectedLines.forEach((LineFeature line) -> {
		int identifier = line.getShapesId();
		deleteLine(identifier);
	});

	selectedTriangles.forEach((TriangleFeature triangle) -> {
		int identifier = triangle.getShapesId();
		deleteTriangle(identifier);
	});

	selectedRectangles.forEach((RectangleFeature rectangle) -> {
		int identifier = rectangle.getShapesId();
		deleteRectangle(identifier);
	});
}

/**
 * Iterates through every drawn ToolPoint in the corresponding ArrayList and deletes the object with the
 * same id as the provided id in the parameter.
 * @author 
 * @param identifier Identifies the point that should be deleted
 */
private void deletePoint(int identifier) {
	//loop through every point of managedToolPoints
	for ( int i=0; i<selectedPoints.size(); i++) {
		PointFeature point = selectedPoints.get(i);
		int pointIdentifier = point.getShapesId();
		//if identifier from selectedToolPoints and from managedToolPoints match then delete this point
		if (pointIdentifier == identifier) {
			selectedPoints.remove(i);
		}
	}
}

/**
 * Iterates through every drawn ToolFeature in the corresponding ArrayList and deletes the object with the
 * same id as the provided id in the parameter.
 * @author 
 * @param identifier Identifies the line that should be deleted
 */
private void deleteLine(int identifier) {
	//loop through every line of drawLine
	for ( int i=0; i<drawingLines.size(); i++) {
		LineFeature line = drawingLines.get(i);
		int lineIdentifier = line.getShapesId();
		//if identifier from selectedToolFeatures and from drawLine match then delete this line
		if (lineIdentifier == identifier) {
			drawingLines.remove(i);
		}
	}
}

/**
 * Iterates through every drawn TriangleFeature in the corresponding ArrayList and deletes the object with the
 * same id as the provided id in the parameter.
 * @author 
 * @param identifier Identifies the triangle that should be deleted
 */
private void deleteTriangle(int identifier) {
	//loop through every triangle of drawtriangle
	for ( int i=0; i<drawingTriangles.size(); i++) {
		TriangleFeature triangle = drawingTriangles.get(i);
		int triangleIdentifier = triangle.getShapesId();
		//if identifier from selectedTriangleFeatures and from drawtriangle match then delete this triangle
		if (triangleIdentifier == identifier) {
			drawingTriangles.remove(i);
		}
	}
}

/**
 * Iterates through every drawn ToolRectangle in the corresponding ArrayList and deletes the object with the
 * same id as the provided id in the parameter.
 * @author 
 * @param identifier Identifies the rectangle that should be deleted
 */
private void deleteRectangle(int identifier) {
	//loop through every rectangle of managedToolRectangles
	for ( int i=0; i<drawingRectangles.size(); i++) {
		RectangleFeature rectangle = drawingRectangles.get(i);
		int triangleIdentifier = rectangle.getShapesId();
		//if identifier from selectedToolRectangle and from managedToolRectangles match then delete this rectangle
		if (triangleIdentifier == identifier) {
			drawingRectangles.remove(i);
		}
	}
}
}


