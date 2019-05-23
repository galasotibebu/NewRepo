import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class gui extends JFrame implements ActionListener{
	
		JDialog f2 = new JDialog();
		JDialog f3 = new JDialog();
		JDialog f4 = new JDialog();
		JDialog f5 = new JDialog();
		JDialog f6 = new JDialog();
		
		JMenuBar menubar=new JMenuBar();
		JButton stat = new JButton("Coordinates");
		JLabel canv = new JLabel("Location of canvas");
		
	public gui(){ 
				
		this.setJMenuBar(menubar);
		this.setLayout(new BorderLayout());
		
		this.add(canv, BorderLayout.CENTER);
		this.add(stat, BorderLayout.SOUTH);
		
		f2.setSize(100,200);
		f2.setLayout(new GridLayout(5,1));
		f2.setLocation(800,000);
		
		f3.setSize(100,200);
		f3.setLayout(new GridLayout(5,1));
		f3.setLocation(800,200);
		
		f4.setSize(100,200);
		f4.setLayout(new GridLayout(5,1));
		f4.setLocation(800,400);
		
		f5.setSize(100,150);
		f5.setLayout(new GridLayout(4,1));
		f5.setLocation(930,200);
		
		f6.setSize(100,150);
		f6.setLayout(new GridLayout(4,1));
		f6.setLocation(930,400);
		
		
		JMenu file = new JMenu("File");
		menubar.add(file);
		
		JMenu object = new JMenu("Object");
		menubar.add(object);
		
		JMenu help = new JMenu("Help");
		menubar.add(help);
		
		JMenuItem inport = new JMenuItem("Import");
		file.add(inport);
		JMenuItem export = new JMenuItem("Export");
		file.add(export);
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		
		JMenuItem point = new JMenuItem("Point");
		object.add(point);
		JMenuItem line = new JMenuItem("Line");
		object.add(line);
		JMenuItem polygon = new JMenuItem("Polygon");
		object.add(polygon);
		
		JLabel l2 = new JLabel("Point editor");
		f2.add(l2);
		JButton createpo = new JButton("Create");
		f2.add(createpo);
		JButton editpo = new JButton("Move");
		f2.add(editpo);
		JButton erasepo = new JButton("Erase");
		f2.add(erasepo);
		JButton displpo = new JButton("Select");
		f2.add(displpo);
		
		JLabel l3 = new JLabel("Line editor");
		f3.add(l3);
		JButton createli = new JButton("Create");
		f3.add(createli);
		JButton editli = new JButton("Edit lines");
		f3.add(editli);
		JButton eraseli = new JButton("Erase");
		f3.add(eraseli);
		JButton displli = new JButton("Select");
		f3.add(displli);
		
		JLabel l4 = new JLabel("Polygon editor");
		f4.add(l4);
		JButton createply = new JButton("Create");
		f4.add(createply);
		JButton editply = new JButton("Edit polygons");
		f4.add(editply);
		JButton eraseply = new JButton("Erase");
		f4.add(eraseply);
		JButton displply = new JButton("Select");
		f4.add(displply);
		
		JLabel l5 = new JLabel("Edit lines");
		f5.add(l5);
		JButton rotli = new JButton("Rotate");
		f5.add(rotli);
		JButton leng = new JButton("Lengthen");
		f5.add(leng);
		JButton moveli = new JButton("Move");
		f5.add(moveli);
		
		JLabel l6 = new JLabel("Edit polygons");
		f6.add(l6);
		JButton rotply = new JButton("Rotate");
		f6.add(rotply);
		JButton extend = new JButton("Extend");
		f6.add(extend);
		JButton moveply = new JButton("Move");
		f6.add(moveply);
		
		JMenuItem helpp = new JMenuItem("Help");
		help.add(helpp);
		
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lab = new JLabel("asd");
		lab.setHorizontalAlignment(JLabel.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.setSize(800,600);
		this.setTitle("Object Editor");
		
		quit.addActionListener(this);
		point.addActionListener(this);
		line.addActionListener(this);
		polygon.addActionListener(this);
		editli.addActionListener(this);
		editply.addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e){
		String str=e.getActionCommand();
		if(str=="Quit"){
			System.exit(0);
		}else if(str=="Point"){
			f2.setVisible(true);
		}else if(str=="Line"){
			f3.setVisible(true);
		}else if(str=="Polygon"){
			f4.setVisible(true);
		}else if(str=="Edit lines"){
			f5.setVisible(true);
		}else if(str=="Edit polygons"){
			f6.setVisible(true);
		}
	}

	public static void main (String [] args){
		JFrame fra = new gui(); 
	}
}