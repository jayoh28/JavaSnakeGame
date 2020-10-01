import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

	private static final int SCREEN_WIDTH =800;
	private static final int SCREEN_HEIGHT = 800;
	private static final int DOT_SIZE = 40;
	private static final int GAME_DOTS = (SCREEN_WIDTH*SCREEN_HEIGHT)/DOT_SIZE;
	private static final int DELAY = 80;

	private final int x[] = new int[GAME_DOTS];
	private final int y[] = new int[GAME_DOTS];

	private int lengthOfSnake = 6;
	private int dotsEaten;
	private int dotX; 
	private int dotY;;
	char directions = 'R';
	boolean running = false;

	private Timer timer;
	private Random random;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new NewKeyAdapter());
		startGame();
	}

	public void startGame() {
		createDot();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		if(running) {
//			for(int i=0; i<SCREEN_HEIGHT/DOT_SIZE; i++) {
//				g.drawLine(i*DOT_SIZE, 0, i*DOT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*DOT_SIZE, i*SCREEN_WIDTH, i*DOT_SIZE);
//			}
			g.setColor(Color.red);
			g.fillOval(dotX, dotY, DOT_SIZE, DOT_SIZE);

			for (int i=0; i< lengthOfSnake; i++) {
				if(i==0) {  //head of the snake
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
				}
				else {  //body of the snake
					g.setColor(new Color(90, 180, 0));
					g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+dotsEaten,(SCREEN_WIDTH -metrics.stringWidth("Score: "+ dotsEaten))/2 , g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}

	public void createDot() {
		dotX = random.nextInt((int)(SCREEN_WIDTH/DOT_SIZE))*DOT_SIZE;
		dotY = random.nextInt((int)(SCREEN_HEIGHT/DOT_SIZE))*DOT_SIZE;
	}

	public void move() {
		for(int i= lengthOfSnake;i>0; i-- ) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		switch (directions) {
		case 'U':
			y[0] = y[0] - DOT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + DOT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - DOT_SIZE;
			break;
		case 'R':
			x[0] =x[0] + DOT_SIZE;
			break;
		}
	}

	public void checkDots() {
		if ((x[0]== dotX) && (y[0]==dotY)) {
			lengthOfSnake++;
			dotsEaten++;
			createDot();
		}
	}

	public void checkCollisions() {
		for(int i= lengthOfSnake; i>0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {  //head collision with body
				running = false;
			}
		}

		if(x[0] <0) {   //head collision with left border
			running = false;
		}

		if(x[0] > SCREEN_WIDTH) {  //head collision with right border
			running = false;
		}

		if(y[0] <0) {    //head collision with top border
			running = false;
		}

		if (y[0] > SCREEN_HEIGHT) {   //head collision with bottom border
			running = false;
		}

		if(!running) {
			timer.stop();
		}

	}

	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over !",(SCREEN_WIDTH -metrics.stringWidth("Game Over !"))/2 , SCREEN_HEIGHT/2);

		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+dotsEaten,(SCREEN_WIDTH -metrics2.stringWidth("Score: "+ dotsEaten))/2 , g.getFont().getSize());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkDots();
			checkCollisions();
		}
		repaint();
	}

	public class NewKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(directions != 'R') {
					directions = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(directions != 'L') {
					directions = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(directions != 'D') {
					directions = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(directions != 'U') {
					directions = 'D';
				}
				break;
			}
		}
	}
}
