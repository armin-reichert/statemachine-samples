package de.amr.samples.fsm.lamp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LampView extends JPanel {

	private Icon lightOnImage;
	private Icon lightOffmage;
	private final JLabel bulb;
	private final JButton toggleButton;

	public LampView() {
		try {
			BufferedImage bulbs = ImageIO.read(getClass().getResourceAsStream("/bulbs.png"));
			int w = bulbs.getWidth() / 2, h = bulbs.getHeight();
			lightOffmage = new ImageIcon(bulbs.getSubimage(0, 0, w, h));
			lightOnImage = new ImageIcon(bulbs.getSubimage(w, 0, w, h));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bulb = new JLabel("", lightOffmage, JLabel.CENTER);
		toggleButton = new JButton("Toggle");
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		add(bulb, BorderLayout.CENTER);
		add(toggleButton, BorderLayout.SOUTH);
	}

	public void addButtonHandler(Consumer<Boolean> handler) {
		toggleButton.setAction(new AbstractAction("Press") {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.accept(true);
			}
		});
	}

	public void showLight(boolean on) {
		bulb.setIcon(on ? lightOnImage : lightOffmage);
	}
}