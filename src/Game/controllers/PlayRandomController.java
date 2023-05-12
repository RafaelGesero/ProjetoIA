package Game.controllers;

import java.util.*;

import Game.Game.Game;
import Game.nn.FeedForwardNN;
import Game.space.Commons;
import Game.space.SpaceInvaders;

import static java.lang.System.exit;


public class PlayRandomController {
	private static final int NUMBER_GAMES = 100;
	private static final double PROB = 0.2;

	public static void main(String[] args) {
		List<Game> list = new ArrayList<>();
		for (int i = 0; i < NUMBER_GAMES; i++) {
			list.add(new Game());
		}
		int count = 0;
		Game bestGame = null;
		double bestFitness = 0;

		while (true) {
			count++;
			list.sort(Comparator.comparing(Game::getFitness));
			List<Game> aux = select(list, count);
			list.clear();

			Random r = new Random();
			for (int p = 0; p < NUMBER_GAMES; p++) {
				int a = r.nextInt(20);
				int b = r.nextInt(20);
				int c = r.nextInt(6790);

				Game g1 = aux.get(a);
				Game g2 = aux.get(b);
				double[] values = new double[6790];
				for (int y = 0; y < 6790; y++) {
					if (y < c) {
						values[y] = g1.getNn().getChromossome()[y];
					} else {
						values[y] = g2.getNn().getChromossome()[y];
					}
				}
				list.add(new Game(values));
			}

			System.out.println("Valor do count: " + count);
			bestGame = getBestGame(list, bestGame, bestFitness);
			bestFitness = bestGame.getFitness();

			


		}
	}

	private static List<Game> select(List<Game> list, int count) {
		List<Game> aux = new ArrayList<>();
		int j = 0;
		for (Game g : list) {
			if (g.getFitness() >= 103857) {
				System.out.println("Geração número: " + count);
				System.out.println("Fitness da solução: " + g.getFitness());
				System.out.println("Cromossoma: " + Arrays.toString(g.getNn().getChromossome()));
				System.exit(0);
			}
			if (j == NUMBER_GAMES * PROB) {
				break;
			} else {
				aux.add(g);
			}
			j++;
		}
		return aux;
	}

	private static Game getBestGame(List<Game> list, Game currentBest, double currentBestFitness) {
		for (Game game : list) {
			double fitness = game.getFitness();
			if (fitness > currentBestFitness) {
				currentBest = game;
				currentBestFitness = fitness;
			}
		}
		System.out.println("Melhor fitness: " + currentBestFitness);
		return currentBest;
	}
}


