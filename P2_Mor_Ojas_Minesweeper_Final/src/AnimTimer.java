import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class AnimTimer extends AnimationTimer{

	boolean isOn = false;
	//MinesweeperGUIMain model;
	Label label;
	long previousTime = 0;
	int time = 0;
	
	public AnimTimer(Label l) {
		label = l;
	}
	
	@Override
	public void handle(long now) {
		//now = System.currentTimeMillis();
		
		if (now - previousTime >= Math.pow(10, 9)) {
			time++;
			label.setText("Time Elapsed: " + time);
			previousTime = now;
		}
	}
}
