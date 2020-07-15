package game_graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
 
public class Game extends Canvas implements Runnable {
	public static JFrame frame;
	private boolean isRunning = true;
	private Thread thread;
	private final int WIDTH = 160;
	private final int HEIGTH = 120;
	private final int SCALE = 30;
	
	private BufferedImage image;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGTH*SCALE));
		Init_Frame();
		image  = new BufferedImage(160,120,BufferedImage.TYPE_INT_RGB);
		
	}
	
	public void Init_Frame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	public synchronized void stop() {
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		// game logics 
	}
	
	public void render() {
		// game rendering 
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g  = image.getGraphics();
		g.setColor(new Color(19,19,19));
		g.fillRect(0,0,WIDTH,HEIGTH);
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGTH*SCALE,null);
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTciks = 60.0;
		double ns = 1000000 / amountOfTciks;
		double delta= 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS:" + frames);
				frames = 0;
				timer += 1000;
			}
		}
		
	}

}
