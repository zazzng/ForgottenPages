package Main;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import javax.swing.JFrame;
import com.InteractableObject.Tokens.Pen;
import com.InteractableObject.Tokens.Pillow;
import com.InteractableObject.Tokens.Painting;
import com.InteractableObject.Tokens.Key;
import com.InteractableObject.Tokens.Tokens;
import com.InteractableObject.Buttons.StartButton;
import com.InteractableObject.Buttons.BackButton;
import com.InteractableObject.Buttons.IntroductionButton;
import com.InteractableObject.Buttons.RestartButton;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import util.MinimHelper;

import com.StasticObject.Spots.Bed;
import com.StasticObject.Spots.Diary;
import com.StasticObject.Spots.PenCase;
import com.StasticObject.Spots.Sketch;
import com.StasticObject.Spots.Spots;
import com.StasticObject.Spots.PaintingFrame;
import com.StasticObject.Spots.Drawer;

import com.Recursion.CloudPanel;
import com.Recursion.Triangle;
import com.Screen.IntroScreen;
import com.Screen.IntroductionScreen;
import com.Screen.EndScreen;

import com.IngameBackgound.Room.DecoratorPattern.Component;
import com.IngameBackgound.Room.DecoratorPattern.ConcreteComponent;
import com.IngameBackgound.Room.Cat;
import com.IngameBackgound.Room.WallPainting;
import com.IngameBackgound.Room.CatOnRug;
import com.IngameBackgound.Room.WallShell;
import com.IngameBackgound.Room.Chandelier;
import com.IngameBackgound.Room.Cloud;
import com.IngameBackgound.Room.AnotherWallPainting;
import com.IngameBackgound.Room.BlueBackground;

/*
 * 1. The environment has the cloud drawn using Perlin noise and the triangle fractals drawn by recursion.
 * While the cloud is used to show the process of the game. Everytime when one item is returned back to its place, 
 * cloud will be collapsed until disappear and the diary reveal and be ready for player to enter the password.
 * The triangle fractals is used as one of the question for the password.
 * 
 * 2.The background is hand-drawn. The room and some decorations are implemented by using decoration and factory pattern.
 * Also, the cloud is moving outside the window, and the window can change its opacity as the way to show the difference of day and night.
 * 
 * 3. The bottom of the screen is for the instruction. Everytime when one token is rightly returned, the question appears there. 
 * You can click everywhere but not the designated spots, the instruction will return back to general instruction.
 * If you forget the question, always be able to comeback by click on the designated spots. 
 * ** Designated spots: the spots of the items after rightly being returned.
 * 
 * 4. Sound effects are added for the mouse click, background music and items-and-spots merge. 
 * 
 * 5. The intro screen is hand drawn and has start button and the how-to-play button. 
 * While start button leads to the main screen, the how-to-play screen opens the introduction screen where 
 * player can get the basic information of the game.
 * When the goal is achieved, there is an hand drawn - ending screen and restart button which leads back to the start screen allowing 
 * the process to relaunch.
 * 
 * 6. There are 8 named packages in total included the Main and util packages. 
 * Each of the package has the relevant name which actually show its function.
 * For example, the com.interactableObjet.Token package contains tokens/items that can be interacted like click on and move around.
 * 
 * 7. abstract superclass: 
 * - Token class in the com.interactableObjet.Token package
 * - Spots class in the com.StasticObject.Spots package
 * 
 * interface:
 * - Button interface in the com.InteractableObject.Buttons package
 * - Component interface in the com.IngameBackground.Room package inside the DecoratorPattern.java
 * 
 * inclusion polymorphism:
 * - superclass/interface as parameter for a method: 
 * public Spots getSpot(Spots linkedSpot) {
    	if (this.spot == linkedSpot) {
            return linkedSpot;
        } else {
            return null;
        }
    } 
    This method is under the Token class in the com.interactableObjet.Token package.
 * - superclass/interface typed ArrayList that mixed objects of subclass:
 * Please use private class MyMouseMotionListioner extends MouseMotionAdapter
 * and private class MyMouseListener extends MouseAdapter for reference.
 * 
 * 8. Block comments are added at the top of all class
 * 
 ********************* ECOS *********************
 * - All visual images are hand drawn
 * - Day and night change is shown outside the window which is achieved by changing the opacity of the first layer of the background
 * - Sophisticated complex shapes: implemented fractals and Perlin-moise clouds as complex shapes that respond to player actions
 * - Sophisticated micro-animation: includes the cloud moving through windows, cloud collapse, triangle fractal reveal and diary appearance responsive to player actions 
 * - Created an scratch paper for player. Open it by using SHIFT + A
 * 
 */ 

public class GamePanel extends JPanel implements ActionListener {
	Timer timer;
	private Pen pen;
	private PenCase penCase;
	private Pillow pillow;
	private Bed bed;
	private Diary diary;
	private StartButton startButton;
	private RestartButton restartButton;
	private IntroductionButton introButton;
	private BackButton backButton;
	private Sketch sketch;
	private Painting painting;
	private PaintingFrame pFrame;
	private Key key;
	private Drawer drawer;

	private IntroScreen introScreen;
	private EndScreen endScreen;
	private IntroductionScreen howToPlayScreen;
	
	private JFrame frame;

	private double mouseX;
	private double mouseY;

	private int state = 0;
	private int cloudState = 0;
	private int unlockedToken = 0;
	private int previousTokenCount = 0;
	private int tokenCategory = 0;

	private boolean penCounted = false;
	private boolean pillowCounted = false;
	private boolean paintingCounted = false;
	private boolean keyCounted = false;
	private boolean triangleState = false;

	private String currentInstruction = null;

	private CloudPanel cloud;
	private Triangle triangle;
	private Component component;
	private Cloud cloudMoving;

	private Minim minim;
	private AudioPlayer backgroundMusic, buttonClick, mergeSound;
	private boolean musicStarted = false;
	private boolean userIsTypingPassword = false;
	
	static final float NEAR_DISTANCE = 150.0f;

	ArrayList<Spots> spotsList = new ArrayList<>();
	ArrayList<Tokens> tokensList = new ArrayList<>();

	public GamePanel(JFrame frame) {
//		this.setBackground(Color.WHITE);
		this.frame = frame;

		addMouseListener(new MyMouseListener());
		addMouseMotionListener(new MyMouseMotionListioner());
		addKeyListener(new MyKeyListener());
		setFocusable(true);
		requestFocusInWindow();
		
		penCase = new PenCase(GameApp.WIDTH / 2 - GameApp.WIDTH / 4, GameApp.HEIGHT / 2 + 80, 1);
		pFrame = new PaintingFrame(GameApp.WIDTH / 2 - 100, GameApp.HEIGHT / 3, 0.056);
		bed = new Bed(GameApp.WIDTH / 2 + 70, GameApp.HEIGHT / 2 - 70, 1.5);
		drawer = new Drawer(95, GameApp.HEIGHT / 2 - 163, 1.35);
		diary = new Diary(GameApp.WIDTH / 2 - GameApp.WIDTH / 8 + 50, GameApp.HEIGHT / 2 + 135, 0.1, this);
		sketch = new Sketch(100, 100, 1, this);

		key = new Key(drawer, GameApp.WIDTH / 2 - GameApp.WIDTH / 4, GameApp.HEIGHT / 2 - 80, 0.07);
		pen = new Pen(penCase, GameApp.WIDTH / 2 - GameApp.WIDTH / 4, GameApp.HEIGHT / 2 + 165, 0.11);
		pillow = new Pillow(bed, GameApp.WIDTH / 2 + 160, GameApp.HEIGHT / 2 + 120, 0.2);
		painting = new Painting(pFrame, GameApp.WIDTH / 2 - 190, GameApp.HEIGHT / 2 + 140, 0.6);

		startButton = new StartButton(GameApp.WIDTH / 2 + GameApp.WIDTH / 4, GameApp.HEIGHT / 2 + 250);
		restartButton = new RestartButton(GameApp.WIDTH / 2 + GameApp.WIDTH / 4, GameApp.HEIGHT / 2 + 250);
		introButton = new IntroductionButton(GameApp.WIDTH / 2 + GameApp.WIDTH / 4, GameApp.HEIGHT / 2 + 130);
		backButton = new BackButton(GameApp.WIDTH / 2, GameApp.HEIGHT / 2 + 270);
		
		introScreen = new IntroScreen(GameApp.WIDTH / 2, GameApp.HEIGHT / 2, 0.5);
		endScreen = new EndScreen(GameApp.WIDTH / 2, GameApp.HEIGHT / 2, 0.5);
		howToPlayScreen = new IntroductionScreen (GameApp.WIDTH / 2, GameApp.HEIGHT / 2, 0.53);
		
		cloud = new CloudPanel(250, 180);
		triangle = new Triangle(GameApp.WIDTH / 2 + 200, GameApp.HEIGHT / 2);

		minim = new Minim(new MinimHelper());

		try {
			backgroundMusic = minim.loadFile("BackgroundMusic.mp3");
			buttonClick = minim.loadFile("ButtonClick.mp3");
			mergeSound = minim.loadFile("MergeSound.mp3");

		} catch (Exception e) {
			System.err.println("Error loading audio files");
		}

		spotsList.add(bed);
		spotsList.add(drawer);
		spotsList.add(pFrame);
		spotsList.add(penCase);

		tokensList.add(key);
		tokensList.add(pen);
		tokensList.add(pillow);
		tokensList.add(painting);

		createNewRoomBase();
		timer = new Timer(33, this);
		timer.start();
	}

	private void createNewRoomBase() {
		component = new ConcreteComponent(GameApp.WIDTH / 2, GameApp.HEIGHT / 2, 0.5);
		cloudMoving = new Cloud(component, 800, 210, 1, 4);
		component = cloudMoving; 
		component = new BlueBackground(component, GameApp.WIDTH / 2, GameApp.HEIGHT / 2, 0.5);
		component = new Cat(component, GameApp.WIDTH / 2 - 130, GameApp.HEIGHT / 2 + 15, 0.5);
		component = new WallPainting(component, GameApp.WIDTH / 2 - GameApp.WIDTH / 20,
				GameApp.HEIGHT / 2 - GameApp.HEIGHT / 4, 0.5);
		component = new CatOnRug(component, GameApp.WIDTH / 2 + GameApp.WIDTH / 4,
				GameApp.HEIGHT / 2 + GameApp.HEIGHT / 4, 0.5);
		component = new WallShell(component, GameApp.WIDTH - GameApp.WIDTH / 12,
				GameApp.HEIGHT / 2 - GameApp.HEIGHT / 3, 0.5);
		component = new Chandelier(component, GameApp.WIDTH - GameApp.WIDTH / 14,
				GameApp.HEIGHT / 2 - GameApp.HEIGHT / 2.3, 1);
		component = new AnotherWallPainting(component, GameApp.WIDTH - GameApp.WIDTH / 14,
				GameApp.HEIGHT / 2 - GameApp.HEIGHT / 4.5, 1.25);
	}

	public void actionPerformed(ActionEvent e) {
		if (!musicStarted) {
			backgroundMusic.loop();
			musicStarted = true;
		}
		if (state == 1) {
			if (cloud != null) {
				cloud.onTick();
			}
			if (unlockedToken > previousTokenCount) {
				cloud.setWidth(cloud.getWidth() - (cloud.getWidth() / 4));
				cloud.setHeight(cloud.getHeight() - (cloud.getHeight() / 4));
				previousTokenCount = unlockedToken;
			} else if (unlockedToken == 4) {
				cloudState = 0;
			}
		}
		if (cloudMoving != null) {
			cloudMoving.move();
		} else {
		    System.out.println("Cloud is null!");
		}

		repaint();
	}

	private class MyKeyListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			if (diary.isTyping() ) { // check: is the diary in input mode?
				char c = e.getKeyChar();

				if (c == '\n') {
					diary.submit();
				} else if (Character.isDigit(c) && diary.userInput.length() < 4) {
					diary.addCharacter(c);
				} else if (diary.userInput.length() > 0 && c == '\b') {
					diary.removeLastCharacter();
				}
				repaint();
			}

			if (sketch.isTyping()  && userIsTypingPassword == false) {
				char c = e.getKeyChar();

				if (sketch.userInput.length() > 0 && c == '\b') {
					sketch.removeLastCharacter();
				} else if (c == '\n') {
					sketch.addCharacter('\n');
				} else {
					sketch.addCharacter(c);
				}
				repaint();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_A) {
				System.out.println("skerchhh");
				sketch.toggleInput();
				repaint();
			}
		}
	}
	private class MyMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			tokenCategory = 0;
			diary.closeInput();
			userIsTypingPassword = false;
		}

		public void mousePressed(MouseEvent e) {
			buttonClick.play(0);
			mouseX = e.getX();
			mouseY = e.getY();

			if (state == 1) {
				triangleState = false;
				tokenCategory = 0;
				for (Tokens token : tokensList) {
					if (token.checkMouseHit(mouseX, mouseY)) {
						tokenCategory = token.getTokenCategory();
						break;
					}
				}
				repaint();
			}
		}

		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();

			if (state == 0 && startButton.clicked(mouseX, mouseY)) {
				state = 1;
			}
			
			if(state == 0 && introButton.clicked(mouseX,  mouseY)) {
				state = 4; //state 4 is how to play screen
			}
			if(state == 4 && backButton.clicked(mouseX, mouseY)) {
				state = 0;
			}
			if (state == 3 && startButton.clicked(mouseX, mouseY)) {
				if (backgroundMusic != null) {
			        backgroundMusic.close(); // or close(), depending on your audio library
			    }
				frame.dispose();
				frame = new GameApp();
			}
			currentInstruction = null;
			cloud.hideInstruction();
			triangleState = false;

			for (Spots spot : spotsList) {
				spot.hideInstruction();
			}

			if (state == 1 || state == 2) {
				if (e.getClickCount() == 1) {
					if (unlockedToken == 4) {
						cloudState = 0;
					} else {
						cloudState = 1;
					}

					if (cloud.checkMouseHit(mouseX, mouseY)) {
						cloud.showInstruction();
						currentInstruction = cloud.getInstruction();
					}

					if (diary.checkMouseHit(mouseX, mouseY)) {
						userIsTypingPassword = true;
						diary.showInstruction();
						diary.toggleInput();
						currentInstruction = diary.getInstruction();
					}

					for (Spots spot : spotsList) {
						if (spot.isActive() && spot.checkMouseHit(mouseX, mouseY)) {
							spot.showInstruction();
							currentInstruction = spot.getInstruction();
							if (spot instanceof Drawer) {
								triangleState = true;
							}
							break;
						}
					}
				}
			}
			repaint();
		}
	}

	private class MyMouseMotionListioner extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();

			for (Tokens token : tokensList) {
				if (token.getTokenCategory() == tokenCategory && token.checkMouseHit(mouseX, mouseY)) {
					token.setPos(mouseX, mouseY);
					if (token instanceof Pen) {
						Pen pen = (Pen) token;
						if (pen.hit(penCase) && !penCounted) {
							mergeSound.rewind();
							mergeSound.play();
							pen.setPos(penCase.getXPos(), penCase.getYPos());

							penCase.setActive(true);
							penCase.showInstruction();
							currentInstruction = penCase.getInstruction();
							if (!penCounted) {
								unlockedToken++;
								penCounted = true;
							}
						}
					}

					else if (token instanceof Pillow) {
						Pillow pillow = (Pillow) token;
						if (pillow.hit(bed) && !pillowCounted) {
							mergeSound.rewind();
							mergeSound.play();
							pillow.setPos(bed.getXPos(), bed.getYPos());
							bed.setActive(true);
							bed.showInstruction();
							currentInstruction = bed.getInstruction();
							if (!pillowCounted) {
								unlockedToken++;
								pillowCounted = true;
							}
						}
					}

					else if (token instanceof Painting) {
						Painting painting = (Painting) token;
						if (painting.hit(pFrame) && !paintingCounted) {
							mergeSound.rewind();
							mergeSound.play();
							painting.setPos(pFrame.getXPos(), pFrame.getYPos());

							pFrame.setActive(true);
							pFrame.showInstruction();
							currentInstruction = pFrame.getInstruction();
							if (!paintingCounted) {
								unlockedToken++;
								paintingCounted = true;
							}
						}
					}

					else if (token instanceof Key) {
						Key key = (Key) token;
						if (key.hit(drawer) && !keyCounted) {
							mergeSound.rewind();
							mergeSound.play();
							key.setPos(drawer.getXPos(), drawer.getYPos());

							drawer.setActive(true);
							drawer.showInstruction();
							currentInstruction = drawer.getInstruction();
							if (!keyCounted) {
								unlockedToken++;
								keyCounted = true;
								triangleState = true;
							}
						}
					}
				}
				repaint();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (state == 0) {
			introScreen.draw(g2d);
			startButton.draw(g2d);
			introButton.draw(g2d);
		} else if (state == 4) {
			howToPlayScreen.draw(g2d);
			backButton.draw(g2d);
		}
		else if (state == 1) {
			component.draw(g2d);
			sketch.draw(g2d);
			drawInstruction(g2d, "Some items are in the wrong places, find them and return them to their places.");

			if (cloudState == 1) {
				cloud.draw(g2d);
			}

			if (triangleState) {
				triangle.draw(g2d, 5);
			}

			if (unlockedToken == 4) {
				state = 2;
			}

			if (currentInstruction != null) {
				drawInstruction(g2d, currentInstruction);
			}
			for (Spots spot : spotsList) {
				for (Tokens token : tokensList) {
					Spots linkedSpot = token.getSpot(spot);
					if (linkedSpot != null && !linkedSpot.isActive()) {
						spot.draw(g2d);
						token.draw(g2d);
					}

					else if (spot.isActive()) {
						spot.setImg(1);
						spot.draw(g2d);
					}
				}
			}
		}

		else if (state == 2) {
			component.draw(g2d);
			sketch.draw(g2d);
			drawInstruction(g2d,"Voilaa! Did you get all the digits for the password? Click on the diary and test your knowledge :))");
			diary.draw(g2d);
			if (diary.getWinningState()) {
				endScreen.draw(g2d);
				restartButton.draw(g2d);
				state = 3;
			}
			if (triangleState) {
				triangle.draw(g2d, 5);
			}

			if (currentInstruction != null) {
				drawInstruction(g2d, currentInstruction);
			}
			for (Spots spot : spotsList) {
				spot.setImg(1);
				spot.draw(g2d);
			}
		}

		if (diary.getWinningState()) {
			endScreen.draw(g2d);
			restartButton.draw(g2d);
			state = 3;
		}
	}

	private void drawInstruction(Graphics2D g2, String text) {
		AffineTransform old = g2.getTransform();

		Color backgroundColor = new Color(239, 211, 209, 255);
		g2.setColor(backgroundColor);
		g2.fillRect(0, GameApp.HEIGHT - 100, GameApp.WIDTH, 100);

		Font f = new Font("Courier", Font.PLAIN, 17);
		g2.setFont(f);
		Color textColor = new Color(98, 52, 62);
		g2.setColor(textColor);

		FontMetrics metrics = g2.getFontMetrics(f);
		int textWidth = 1000;

		int x = 40;
		int y = GameApp.HEIGHT - 100 + 20;

		String[] words = text.split(" ");
		StringBuilder currentLine = new StringBuilder();
		int lineHeight = metrics.getHeight();

		for (String word : words) {
			if (metrics.stringWidth(currentLine + word) < textWidth) {
				currentLine.append(word).append(" ");
			} else {
				g2.drawString(currentLine.toString(), x, y);
				y += lineHeight;
				currentLine = new StringBuilder(word + " ");
			}
		}
		g2.drawString(currentLine.toString(), x, y);
		g2.setTransform(old);
	}
	
	public int getUnlockedToken() {
		return unlockedToken;
	}
}
