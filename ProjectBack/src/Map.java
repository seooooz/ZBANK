import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;

public class Map {

	public static void main(String[] args) throws URISyntaxException {

		final URI uri = new URI("http://localhost:8000/1.html");
		class OpenUrlAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(uri);
			}
		}
		JFrame frame = new JFrame("Links");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(308, 400);
		Container container = frame.getContentPane();
		JButton button = new JButton();
		button.setText("\uC9C0\uB3C4");
		button.setBounds(75, 128, 104, 67);
//		button.setText(
//				"<HTML>Click the <FONT color=\"#000099\"><U>link</U></FONT>" + " to go to the Java website.</HTML>");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		button.setToolTipText(uri.toString());
		button.addActionListener(new OpenUrlAction());
		frame.getContentPane().setLayout(null);
		container.add(button);
		frame.setVisible(true);
	}

	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				/* TODO: error handling */ }
		} else {
			/* TODO: error handling */ }
	}
}