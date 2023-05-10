package Game.controllers;

import java.util.Random;

import Game.nn.FeedForwardNN;
import Game.space.Commons;
import Game.space.SpaceInvaders;

public class PlayRandomController {
	public static void main(String[] args) {
		FeedForwardNN nn = new FeedForwardNN(Commons.STATE_SIZE, (Commons.STATE_SIZE + Commons.NUM_ACTIONS)/2, Commons.NUM_ACTIONS);
		GameController c = new NnController(nn);
		SpaceInvaders.showControllerPlaying(c,20);
	}
}
