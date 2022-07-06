import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Test extends JFrame {
	BufferedImage img = null;
	
	public Test() {
		setTitle("배경화면 테스트");
		
		JLayeredPane layerpane = new JLayeredPane();
		layerpane.setLocation(0, 0);
		layerpane.setSize(400, 600);
		layerpane.setLayout(null);
		
		try {
			img = ImageIO.read(new File("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Check.png"));
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "실패");
			System.exit(0);
		}
		
		myPanel panel = new myPanel();
		panel.setSize(400,600);
		layerpane.add(panel);
		
		getContentPane().setLayout(null);
		getContentPane().add(layerpane);
		
		setBounds(700, 50, 410, 600);
		setVisible(true);
		setResizable(false);		//창크기 수정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	class myPanel extends JPanel	{
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	public static void main(String[] args) {
		new Test();
	}
}