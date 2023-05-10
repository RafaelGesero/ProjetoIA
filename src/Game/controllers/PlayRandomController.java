package Game.controllers;

import java.util.Random;

import Game.space.SpaceInvaders;

public class PlayRandomController {
	public static void main(String[] args) {
		GameController c = new RandomController(new Random());
		SpaceInvaders.showControllerPlaying(c,20);
	}
}
