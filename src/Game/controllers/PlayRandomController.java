package Game.controllers;

import java.util.*;
import java.util.stream.StreamSupport;

import Game.Game.Game;
import Game.nn.FeedForwardNN;
import Game.space.Commons;
import Game.space.SpaceInvaders;

import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.text.html.StyleSheet;

import static java.lang.System.exit;
import static java.lang.System.in;


public class PlayRandomController {
	private static final int NUMBER_GAMES = 100;
	private static final double PROB_BEST_GAMES = 0.2;
	private static final double PROB_MUTATION = 0.1;

	public static void main(String[] args) {

		Game bestGame = null;
		double bestFitness = 0;

		//criação da primeira geração
		int gen  = 1;
		List<Game> currGen = new ArrayList<>();
		for(int i = 0; i < NUMBER_GAMES; i++){
			currGen.add(new Game());
		}

		while(true){
			List<Game> nextGen = new ArrayList<>();
			//Selecionar os melhores individuos
			nextGen = selectFitestIndividuals(currGen);
			currGen.clear();


			//Crossover entre os melhores individuos para gerar o resto da população e possivel mutação
			for(int i =(int) (NUMBER_GAMES * PROB_BEST_GAMES); i < NUMBER_GAMES; i ++){
				int randNumber1 = (int)(Math.random() * (NUMBER_GAMES * PROB_BEST_GAMES + 1));
				int randNumber2 = (int)(Math.random() * (NUMBER_GAMES * PROB_BEST_GAMES + 1));
				Game g = crossoverTwoIndividuals(nextGen.get(randNumber1), nextGen.get(randNumber2));
				mutation(g);
				nextGen.add(g);
			}

			System.out.println("Geração: " + gen);
			bestGame = getBestGame(nextGen, bestGame, bestFitness);
			bestFitness = bestGame.getFitness();
			currGen = nextGen;
			System.out.println(currGen);
			gen++;
		}
	}

	private static List<Game> selectFitestIndividuals(List<Game> list) {
		List<Game> aux = new ArrayList<>();

		for(int i = 0; i < NUMBER_GAMES * PROB_BEST_GAMES; i++){
			int individual1 = (int) (Math.random() * list.size());
			int individual2 = (int) (Math.random() * list.size());
			if(list.get(individual1).getFitness() > list.get(individual2).getFitness())
				aux.add(list.get(individual1));
			else
				aux.add(list.get(individual2));
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

	private static void mutation(Game g){
		double prob = Math.random();
		if(prob <= PROB_MUTATION){
			int pos = (int)(Math.random() * (6790 +1 ) + 0);
			double rand = Math.random();
			g.getNn().getChromossome()[pos] = rand;
		}
	}

	private static Game crossoverTwoIndividuals(Game p1, Game p2){
		double[] child = new double[6790];
		int min = 1;
		int max = 6790;
		int rand = (int)(Math.random() * (max - min +1 ) + min);
		for(int i = 0; i < rand ; i++){
			child[i] = p1.getNn().getChromossome()[i];
		}
		for(int i = rand; i < 6790; i++){
			child[i] = p2.getNn().getChromossome()[i];
		}
		Game c1 = new Game(child);
		return c1;

	}


}


