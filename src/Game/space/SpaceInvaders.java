package Game.space;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Game.controllers.GameController;
import Game.nn.FeedForwardNN;

public class SpaceInvaders extends JFrame {

	private Board board;

	public SpaceInvaders() {

		initUI();
	}

	private void initUI() {
		board = new Board();
		add(board);

		setTitle("Space Invaders");
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

		setResizable(false);
		setLocationRelativeTo(null);
	}


	public static void showControllerPlaying(GameController controller, long seed) {
		EventQueue.invokeLater(() -> {

			var ex = new SpaceInvaders();
			ex.setController(controller);
			ex.setSeed(seed);
			ex.setVisible(true);

		});
	}
	public void setController(GameController controller) {
		board.setController(controller);
	}

	public void setSeed(long seed) {
		board.setSeed(seed);

	}
}
