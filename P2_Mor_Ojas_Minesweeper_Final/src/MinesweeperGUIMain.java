/*
 * Ojas Mor, period 2, APCS, Mr. Ferrante, April 7, 2019.
 * This lab in total took me around 5 hours to complete.
 * Firstly, I want to say that this was a really fun lab to
 * work on since it incorporated so many different skills
 * and concepts, but also because the end result is a classic
 * game. There were some challenges I faced. Some included
 * the recursive reveal method, and creating the mouse handler.
 * Apart from that everything else just took some time to
 * complete. I did not get the recursive method at first but
 * it is much simpler than I had thought once we walked through
 * it in class. Its also interesting to see that such a "simple"
 * game requires quite a substantial amount of coding knowledge,
 * which makes me wonder how pc and console games are created
 * (of course those are created by many thousands of people).
 */
import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MinesweeperGUIMain extends Application {
	MinesweeperModel ms;
	Image empty;
	Image zero;
	Image one;
	Image two;
	Image three;
	Image four;
	Image five;
	Image six;
	Image seven;
	Image eight;
	Image flag;
	Image smile;
	Image ooh;
	Image win;
	Image dead;
	Image bomb;
	Image bombWrong;
	Image bombDeath;
	
	ImageView face;
	
	Label l1;
	Label l2;
	
	Image[] nums = new Image[15];
	
	HBox h2;
	FlowPane fp;
	ImageView[][] grid;
	
	MenuItem newGame;
	MenuItem exit;
	MenuItem numMines;
	MenuItem instruction;
	MenuItem about;
	
	AnimTimer t;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Minesweeper");
		stage.setResizable(false);
		stage.sizeToScene();
		
		ms = new MinesweeperModel();
		ms.initialize(10, 10, 10);
		
		BorderPane root = new BorderPane();
		
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		
		Image image1 = new Image("https://i.pinimg.com/originals/ce/7b/10/ce7b10eee61c1cccfa2a6cdc824a7799.jpg");		
		root.setBackground(new Background(new BackgroundImage(image1,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize)));
		
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Game");
		
		Menu options = new Menu("Options");
		
		Menu help = new Menu("Help");
		
		newGame = new MenuItem("New Beginner Game");
		exit = new MenuItem("Exit");
		numMines = new MenuItem("Set Number of Mines");
		instruction = new MenuItem("How To Play");
		about = new MenuItem("About");
		
		VBox v3 = new VBox();
		v3.getChildren().add(menuBar);
		
		menu.getItems().add(newGame);
		menu.getItems().add(exit);
		options.getItems().add(numMines);
		help.getItems().add(instruction);
		help.getItems().add(about);
		menuBar.getMenus().add(menu);
		menuBar.getMenus().add(help);
		menuBar.getMenus().add(options);
		
		instruction.setOnAction(new buttonHandler());
		about.setOnAction(new buttonHandler());
		numMines.setOnAction(new buttonHandler());
		exit.setOnAction(new buttonHandler());
		newGame.setOnAction(new buttonHandler());
		
		l1 = new Label("Number of Mines Remaining: 10");
	    l1.setTextFill(Color.web("#0076a3"));
	    
	    l2 = new Label("Time Elapsed: 0");
	    l2.setTextFill(Color.web("#0076a3"));
	    
	    v3.getChildren().add(l1);
	    v3.getChildren().add(l2);
	    
	    l2.setTranslateX(400);
	    l2.setTranslateY(-25);
	    
	    l1.setFont(new Font("Arial", 20));
	    l2.setFont(new Font("Arial", 20));
	    
		HBox h1 = new HBox();
		root.setTop(v3);
		//h1.getChildren().add(menuBar);
		//h1.getChildren().add(label1);
		
		fp = new FlowPane();
		root.setCenter(fp);
		fp.setAlignment(Pos.CENTER);
		fp.setMaxSize(320, 320);
		
		fp.setOnMouseClicked(new GridMouseHandler());
		
		empty = new Image("file:minesweeper_images/blank.gif");
		zero = new Image("file:minesweeper_images/num_0.gif");
		one = new Image("file:minesweeper_images/num_1.gif");
		two = new Image("file:minesweeper_images/num_2.gif");
		three = new Image("file:minesweeper_images/num_3.gif");
		four = new Image("file:minesweeper_images/num_4.gif");
		five = new Image("file:minesweeper_images/num_5.gif");
		six = new Image("file:minesweeper_images/num_6.gif");
		seven = new Image("file:minesweeper_images/num_7.gif");
		eight = new Image("file:minesweeper_images/num_8.gif");
		flag = new Image("file:minesweeper_images/bomb_flagged.gif");
		bomb = new Image("file:minesweeper_images/bomb_revealed.gif");
		bombWrong = new Image("file:minesweeper_images/bomb_wrong.gif");
		bombDeath = new Image("file:minesweeper_images/bomb_death.gif");
		
		smile = new Image("file:minesweeper_images/face_smile.gif");
		dead = new Image("file:minesweeper_images/face_dead.gif");
		face = new ImageView(smile);
		
		v3.getChildren().add(face);
		face.setTranslateX(275);
		face.setTranslateY(30);
		
		nums[0] = empty;
		nums[1] = one;
		nums[2] = two;
		nums[3] = three;
		nums[4] = four;
		nums[5] = five;
		nums[6] = six;
		nums[7] = seven;
		nums[8] = eight;
		nums[10] = flag;
		nums[11] = zero;
		nums[12] = bomb;
		
		setBoard();
		stage.show();
		root.requestFocus();
		long start = System.currentTimeMillis();
		t = new AnimTimer(l2);
		//t.setModel(this);
		t.start();
		
	}
	
	public void setBoard() {
		grid = new ImageView[10][10];
		
		Insets ins = new Insets(0, 30, 0, 100);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				ImageView blank = new ImageView(empty);
				grid[i][j] = blank;
				fp.getChildren().add(grid[i][j]);
			}
		}
	}

	void updateView() {
		l1.setText("Number of Mines Remaining: " + (ms.numMines() - ms.numFlags()));
		for (int i = 0; i < ms.getRows(); i++) {
			for (int j = 0; j < ms.getCols(); j++) {
				if (ms.revealGrid[i][j] == false) {
					if (ms.isFlagged(i, j)) {
						grid[i][j].setImage(flag);
					} else {
						grid[i][j].setImage(empty);
					}
				} else if (ms.revealGrid[i][j] == true) {
					int temp = ms.calcValue(i, j);
					if (ms.isFlagged(i, j)) {
						grid[i][j].setImage(flag);
					} else if (ms.isBomb(i, j)) {
						grid[i][j].setImage(bomb);
						face.setImage(dead);
						t.stop();
						
					} else if (nums[temp] == empty) {
						grid[i][j].setImage(nums[11]);
					} else {
						grid[i][j].setImage(nums[temp]);
					}
				}
			}
		}
		if (ms.hasGameEnded && ms.hasGameLost) {
			BorderPane root = new BorderPane();
			Stage popInstr = new Stage();
			Scene popInstr1 = new Scene(root, 200, 50);
			
			popInstr.setScene(popInstr1);
			VBox v = new VBox();
			root.setCenter(v);
			Label q = new Label("You LOST! :(");
			q.setFont(new Font("Arial", 30));
			
			v.getChildren().add(q);
			popInstr.setTitle("You Lost");
			popInstr.show();
		} else if (ms.hasGameEnded && !ms.hasGameLost) {
			BorderPane root = new BorderPane();
			Stage popInstr = new Stage();
			Scene popInstr1 = new Scene(root, 200, 50);
			
			popInstr.setScene(popInstr1);
			VBox v = new VBox();
			root.setCenter(v);
			Label q = new Label("You WIN!! :)");
			t.stop();
			q.setFont(new Font("Arial", 30));
			
			v.getChildren().add(q);
			popInstr.setTitle("You Win!!");
			popInstr.show();
		}
	}
	
	private class buttonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == instruction) {
				WebView w = new WebView();
				WebEngine eng = w.getEngine();
				File f = new File("website.html");
				String absPath = f.getAbsolutePath();
				String url = "";
				url = "File:///" + absPath;
				
				eng.load(url);
				
				Stage popInstr = new Stage();
				Scene popInstr1 = new Scene(w, 500, 500);
				
				popInstr.setScene(popInstr1);
				popInstr.show();
			} else if (e.getSource() == about) {
				BorderPane root = new BorderPane();
				Stage popInstr = new Stage();
				Scene popInstr1 = new Scene(root, 300, 150);
				
				popInstr.setScene(popInstr1);
				VBox v = new VBox();
				root.setCenter(v);
				Label q = new Label("ABOUT \nMinesweeper GUI\nVersion: 1.000\nOjas Mor");
				q.setFont(new Font("Arial", 30));
				
				v.getChildren().add(q);
				popInstr.setTitle("ABOUT");
				popInstr.show();
			} else if (e.getSource() == exit) {
				System.exit(0);
			} else if (e.getSource() == newGame) {
				ms = new MinesweeperModel();
				ms.initialize(10, 10, 10);
				face.setImage(smile);
				t.stop();
				t = new AnimTimer(l2);
				t.start();
				updateView();
				ms.hasGameEnded = false;
				ms.hasGameLost = false;
			} else if (e.getSource() == numMines) {
				Scanner in = new Scanner(System.in);
				System.out.println("Please enter the number of mines: ");
				int numMines = in.nextInt();
				
				if (numMines > 0 && numMines < 100) {
					ms = new MinesweeperModel();
					ms.initialize(10, 10, numMines);
					face.setImage(smile);
					t.stop();
					t = new AnimTimer(l2);
					t.start();
					updateView();
				} else {
					BorderPane root = new BorderPane();
					Stage popInstr = new Stage();
					Scene popInstr1 = new Scene(root, 500, 150);
					
					popInstr.setScene(popInstr1);
					VBox v = new VBox();
					root.setCenter(v);
					Label q = new Label("ERROR: Please enter a valid value \n(greater than 0, less than 100)");
					q.setFont(new Font("Arial", 30));
					
					v.getChildren().add(q);
					popInstr.setTitle("ABOUT");
					popInstr.show();
				}
			}
			
		}

		
	}
	
	private class GridMouseHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			int col = ms.colForXPos(e.getX());
			int row = ms.rowForYPos(e.getY());
			
			if (e.getButton() == MouseButton.PRIMARY && !ms.hasGameEnded) {
				System.out.println(row + "   " + col);
				ms.reveal(row, col);
				updateView();
				if (ms.isBomb(row, col)) {
					grid[row][col].setImage(bombDeath);
				}
			} else if (e.getButton() == MouseButton.SECONDARY && !ms.hasGameEnded) {
				ms.updateFlag(row, col);
				updateView();
			}
		}
	}
	
	public void updateTime(long now) {
		l2.setText("Time Elapsed: " + now);
	}
}
