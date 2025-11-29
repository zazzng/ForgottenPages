package Main;
import javax.swing.JFrame;

public class GameApp extends JFrame {
	public static int WIDTH = 1112;
	public static int HEIGHT = 834;
	private static final long serialVersionUID = 1L;
	
	public GameApp() {
		//Init the JFrame
		this.setTitle("Forgotten Pages");
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create an instance
		GamePanel panel = new GamePanel(this);
		this.add(panel);
//		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GameApp();
	}
}