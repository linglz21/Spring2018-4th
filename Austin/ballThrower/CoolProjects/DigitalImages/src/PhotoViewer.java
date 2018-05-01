import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PhotoViewer extends JFrame implements ActionListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	PhotoPanel photo;
	Button selectFile;
	JComboBox<String> transform;
	Label pixelColor;

	public PhotoViewer(){
		this.setLayout(null);
		selectFile = new Button("Change Image");
		selectFile.setBounds(5,5,150,20);
		selectFile.addActionListener(this);
		this.add(selectFile);
		
		String [] selections = {"Select a transform","invertColors","swapColors","darken","lighten","make gray","rotate clockwise", "flip horizontal"};
		transform = new JComboBox<String>(selections);
		transform.setBounds(160,5,150,20);
		transform.addActionListener(this);
		this.add(transform);
		
		pixelColor = new Label("");
		pixelColor.setBounds(315,5,250,20);
		pixelColor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		this.add(pixelColor);
		
		photo = new PhotoPanel(600);
		selectImage();
		photo.setBounds(25, 35, photo.getWidth(), photo.getHeight());
		this.add(photo);
		this.setSize(650, 700);
		addMouseMotionListener(this);
	}
	
	public static void main(String[] args) {
		PhotoViewer pv = new PhotoViewer();
		pv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pv.setVisible(true);
	}
	
	private void selectImage(){
		JFileChooser fd = new JFileChooser("./resources");
		fd.setFileFilter(new FileNameExtensionFilter("Images","jpg","gif","png","bmp"));
		fd.showOpenDialog(this);
		photo.loadImage(fd.getSelectedFile());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == selectFile)selectImage();
		if(e.getSource() == transform){
			System.out.println(transform.getSelectedIndex());
			//String [] selections = {"Select a transform","invertColors","swapColors","darken","lighten","make gray","rotate clockwise", "flip horizontal"};
			switch (transform.getSelectedIndex()){
			case 1 : photo.invertColors(); break;
			case 2 : photo.swapColors(); break;
			case 3 : photo.darken(); break;
			case 4 : photo.lighten(); break;
			case 5 : photo.makeGray(); break;
			case 6 : photo.rotateClockwise(); break;
			case 7 : photo.flipHorizontal(); break;
			default : break;
			}
			
			transform.setSelectedIndex(0);
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int pixel = photo.getPixel();
		int x = photo.getMouseX();
		int y = photo.getMouseY();
		int r = (pixel & 0xFF0000) >> 16;
		int g = (pixel & 0x00FF00) >> 8;
		int b = (pixel & 0x0000FF) >> 0;
		
		pixelColor.setText(String.format("%s%3d %s%3d %s%3d %s%3d %s%3d", "x= ",x," y=",y," r=",r," g=",g," b=",b));
		repaint();
	}
}
