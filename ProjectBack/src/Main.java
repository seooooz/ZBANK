import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame{
	/*�����ִ� �̹����� �����ӿ� �׷��ٰ���.*/
	private Image background=new ImageIcon(Main.class.getResource("C:\\Users\\Administrator.User -2022RMRTU\\Desktop\\images\\Check.png")).getImage();//����̹���
	/*������*/
	public Main() {
		homeframe();
	}
	public void homeframe() {
		setTitle("1");//Ÿ��Ʋ
		setSize(400,600);//�������� ũ��
		setResizable(false);//â�� ũ�⸦ �������� ���ϰ�
		setLocationRelativeTo(null);//â�� ��� ������
		setLayout(null);//���̾ƿ��� ������� ���������ϰ� ����.
		setVisible(true);//â�� ���̰�	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame�� ���������� ����ǰ�
	}
	public void paint(Graphics g) {//�׸��� �Լ�
		g.drawImage(background, 0, 0, null);//background�� �׷���
	}
	public static void main(String[] args){
		new Main();
	}
}