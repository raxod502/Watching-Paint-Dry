import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Arrays.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class Paint extends JApplet implements MouseListener, MouseMotionListener {
	static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	BufferedImage wall;
	static Random random = new Random();
	int[][] age;
	Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.PINK, Color.RED};
	int mouse = 0;
	int life = (50 * 60 * 2) / 7;
	boolean finished = false;

	static JFrame frame;

	public static void main(String[] args) {
		frame = new JFrame("Watching Paint Dry: The Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet app = new Paint();
		app.setFocusable(true);
		frame.add("Center", app);
		frame.pack();
		app.init();
	}

	@Override public int getWidth() {
		return 800;
	}

	@Override public int getHeight() {
		return 600;
	}

	public void init() {
		setLayout(new BorderLayout());
		addMouseListener(this);
		addMouseMotionListener(this);

		wall = config.createCompatibleImage(getWidth(), getHeight(), Transparency.OPAQUE);
		Graphics draw = wall.createGraphics();
		draw.setColor(Color.WHITE);
		draw.fillRect(0, 0, getWidth(), getHeight());
		age = new int[getWidth()][getHeight()];
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				for (int x=0; x<getWidth(); x++) {
					for (int y=0; y<getHeight(); y++) {
						if (wall.getRGB(x, y) != Color.WHITE.getRGB()) {
							try {
								age[x][y] += 1;
								int index = age[x][y] / life;
								Color startingColor = colors[index];
								Color endingColor = colors[index+1];
								wall.setRGB(x, y, new Color(
									startingColor.getRed() + (endingColor.getRed() - startingColor.getRed()) * Math.min(age[x][y]%life, life) / life,
									startingColor.getGreen() + (endingColor.getGreen() - startingColor.getGreen()) * Math.min(age[x][y]%life, life) / life,
									startingColor.getBlue() + (endingColor.getBlue() - startingColor.getBlue()) * Math.min(age[x][y]%life, life) / life).getRGB());
							}
							catch (Exception e) {
								if (!finished) {
									frame.setTitle("Happy Father's Day from Radon! <3");
									int ratio = life / ((50 * 60 * 2) / 7 / 30);
									for (int w=0; w<getWidth(); w++) {
										for (int z=0; z<getHeight(); z++) {
											age[w][z] /= ratio;
										}
									}
									life /= ratio;
								}
								finished = true;
							}
						}
					}
				}
				repaint();
			}
		};
		new javax.swing.Timer(20, action).start();
	}

	public void paint(Graphics g) {
		g.drawImage(wall,
			0, 0, getWidth(), getHeight(),
			0, 0, getWidth(), getHeight(),
			null);
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public void mousePressed(MouseEvent e) {
		for (int x=Math.max(0, e.getX()-10); x<Math.min(getWidth(), e.getX()+10); x++) {
			for (int y=Math.max(0, e.getY()-10); y<Math.min(getHeight(), e.getY()+10); y++) {
				wall.setRGB(x, y, colors[0].getRGB());
				age[x][y] = 0;
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		mousePressed(e);
	}

	public void mouseMoved(MouseEvent e) {
		//
	}

	public void mouseClicked(MouseEvent e) {
		//
	}

	public void mouseReleased(MouseEvent e) {
		//
	}

	public void mouseEntered(MouseEvent e) {
		//
	}

	public void mouseExited(MouseEvent e) {
		//
	}
}
