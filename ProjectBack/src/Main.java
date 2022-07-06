import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame{
	/*여기있는 이미지를 프레임에 그려줄거임.*/
	private Image background=new ImageIcon(Main.class.getResource("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Check.png")).getImage();//배경이미지
	/*생성자*/
	public Main() {
		homeframe();
	}
	public void homeframe() {
		setTitle("1");//타이틀
		setSize(400,600);//프레임의 크기
		setResizable(false);//창의 크기를 변경하지 못하게
		setLocationRelativeTo(null);//창이 가운데 나오게
		setLayout(null);//레이아웃을 내맘대로 설정가능하게 해줌.
		setVisible(true);//창이 보이게	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
	}
	public void paint(Graphics g) {//그리는 함수
		g.drawImage(background, 0, 0, null);//background를 그려줌
	}
	public static void main(String[] args){
		new Main();
	}
}