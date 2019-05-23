/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometrypackage;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author admin
 */
public class ShapesParent {	
	/**
	 * A unique ID of geometry objects
	 */
	public int ShapesId = setShapesId(ToolFrame.frame.editor);
	
	/**
	 * The type of geometry
 (see constructor of ShapesParent and constructor of MainClass, LineFeature, TriangleFeature and RectangleFeature
	 */
	public String objectType;
	
	/**
	 * Constructor sets the type of the objects.
	 * (see: Constructors of ToolLine, ToolTriangle, ToolRectangle)
	 * @author heol1015
	 * @param objectType String provided by the constructor of the geometrie objects when created
	 */
	public ShapesParent (String objectType) {
		this.objectType = objectType;
	}
	
	/**
	 * Returns the identifier of an object.
	 * @author heol1015
	 * @return Identifier of the object
	 */
	public int getShapesId() {
		return this.ShapesId;
	}
	
	/**
	 * Returns the type of an object.
	 * @author heol1015
	 * @return Type of the object
	 */
	public String getObjectType() {
		return this.objectType;
	}

	/**
	 * Iterates through objects of every type of geometry and adds every ShapesId
	 * to the ShapeIdList, as long as the length of the ShapeIdList is > 0, the ShapeId that 
	 * is returned is the length of the SapesIdList + 1
	 * @author heol1015
	 * @param editor
	 * @return New unique identifier
	 */
	public int setShapesId(Editor editor) {
		ArrayList<Integer> ShapesIdList = new ArrayList<Integer>();
		int latestShapesId;
		
		editor.drawPoint.forEach((MainClass point) -> {
			ShapesIdList.add(point.getShapesId());
		});
		editor.drawLine.forEach((LineFeature line) -> {
			ShapesIdList.add(line.getShapesId());
		});
		/*editor.drawTriangle.forEach((TriangleFeature triangle) -> {
			ShapesIdList.add(triangle.getShapesId());
		});
		editor.drawRectangle.forEach((RectangleFeature rectangle) -> {
			ShapesIdList.add(rectangle.getShapesId());
		});*/
                
                
		editor.selectedPoint.forEach((MainClass point) -> {
			ShapesIdList.add(point.getShapesId());
		});
		editor.selectedLines.forEach((LineFeature line) -> {
			ShapesIdList.add(line.getShapesId());
		});
		editor.selectedTriangles.forEach((TriangleFeature triangle) -> {
			ShapesIdList.add(triangle.getShapesId());
		});
		editor.selectedRectangles.forEach((RectangleFeature rectangle) -> {
			ShapesIdList.add(rectangle.getShapesId());
		});
		
		if (ShapesIdList.isEmpty()) {
			latestShapesId = 0;
		} else {
			latestShapesId = Collections.max(ShapesIdList);
		}
		return latestShapesId + 1;
	}

}
