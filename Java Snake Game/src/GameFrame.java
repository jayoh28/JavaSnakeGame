import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame() {
		
		GamePanel newPanel = new GamePanel();
		
		this.add(newPanel);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
