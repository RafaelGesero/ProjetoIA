package Game.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Game.Game.Game;
import Game.space.SpaceInvaders;

import static java.lang.System.exit;

public class PlayRandomController {

	private static final int BESTS = 10;
	private static final double PROB_BEST_GAMES = 0.2;
	private static final double PROB_MUTATION = 10;
	private static final int POPULATION = 1000;

	private static final int MAX_MUTATION = 10;

	private static final int CROMOSSOME_SIZE =2110;

	public static void main(String[] args) {

		List<Game> list = new ArrayList<>();

		for(int i = 0; i< POPULATION; i++){
			list.add(new Game());
		}

		int count =0;
		while (true) {
			count++;
			System.out.println("Geração " + count + " ->Melhor Fitness: " + bestFitness(list));

			System.out.println(list.get(0).getNn().getChromossomeSize());

			List<Game> best = tournament(list);
			//List<Game> best = confront(list);
			//List<Game> best = rankSelection(list);
			list.clear();
			list = crossover(best);
		}
	}

	private static double bestFitness(List<Game> game){
		double a =0;
		for(Game g: game){
			if(g.getFitness() > a ){
				a = g.getFitness();
			}
		}
		return a;
	}

	private static List<Game> tournament(List<Game> game){
		List<Game> selected = new ArrayList<>();

		while (selected.size() < BESTS) {
			List<Game> candidates = new ArrayList<>();

			// Seleciona aleatoriamente TOURNAMENT_SIZE indivíduos da população
			for (int i = 0; i < 200; i++) {
				int index = (int) (Math.random() * game.size());
				candidates.add(game.get(index));
			}

			// Ordena os indivíduos pelo fitness
			candidates.sort(Comparator.comparing(Game::getFitness).reversed());

			// Seleciona o indivíduo mais apto dos candidatos e o adiciona à lista de selecionados
			selected.add(candidates.get(0));
		}

		return selected;
	}


	private static List<Game> rankSelection(List<Game> game) {
		game.sort(Comparator.comparing(Game::getFitness).reversed());

		double totalRank = (game.size() * (game.size() + 1)) / 2.0; // soma dos ranks de 1 a N

		List<Game> selected = new ArrayList<>();

		for (int i = 0; i < BESTS; i++) {
			double rand = Math.random();
			double sum = 0;
			for (int j = 0; j < game.size(); j++) {
				double prob = (game.size() - j) / totalRank;
				sum += prob;
				if (sum > rand) {
					selected.add(game.get(j));
					break;
				}
			}
		}

		return selected;
	}

	private static List<Game> confront(List<Game> game) {
		List<Game> bestGames = new ArrayList<>();
		int numBestGames = (int) (POPULATION * PROB_BEST_GAMES);

		while (bestGames.size() < numBestGames) {
			ListIterator<Game> it = game.listIterator();
			Game bestGame = it.next();

			while (it.hasNext()) {
				Game currentGame = it.next();
				if (currentGame.getFitness() > bestGame.getFitness()) {
					bestGame = currentGame;
				}
			}

			bestGames.add(bestGame);
			game.remove(bestGame);
		}

		return bestGames;
	}



	private static void printList(List<Game> game){
		System.out.println("___________________________________________________________");
		int count =0;
		for(Game g: game){
			System.out.println("O fitness deste elemento é : " + g.getFitness() );
			count++;
		}
		System.out.println("O size é de " + count);

	}


	private static List<Game> crossover(List<Game> game){

		List<Game> aux = new ArrayList<>();

		while(aux.size()<POPULATION){
			Random r = new Random();
			Game parent1 = game.get(r.nextInt(game.size()));
			Game parent2 = game.get(r.nextInt(game.size()));
			double[] childChromossome = new double[CROMOSSOME_SIZE];

			// Define um ponto de corte aleatório
			int cutoff = r.nextInt(CROMOSSOME_SIZE);

			// Realiza o crossover uniforme
			for (int i = 0; i < CROMOSSOME_SIZE; i++) {
				if (i < cutoff) {
					childChromossome[i] = parent1.getNn().getChromossome()[i];
				} else {
					childChromossome[i] = parent2.getNn().getChromossome()[i];
				}
			}

			// Realiza a mutação
			double[] mutatedChildChromossome = mutation(childChromossome);

			// Cria o filho e adiciona na nova população
			Game child = new Game(mutatedChildChromossome);
			getWinner(child);
			aux.add(child);
		}

		return aux;
	}



	private static double[] mutation(double[] values) {
		Random r = new Random();
		if (r.nextInt(100) <= PROB_MUTATION) {
			int numMutations = r.nextInt(MAX_MUTATION) + 1;
			for (int i = 0; i < numMutations; i++) {
				int index = r.nextInt(CROMOSSOME_SIZE);
				double newCromossome = r.nextDouble();
				if (r.nextBoolean()) {
					newCromossome = -newCromossome;
				}
				values[index] = newCromossome;
			}
		}
		return values;
	}



	private static void getWinner(Game g){
			if(g.getBoard().getMessage().equals("Game won!")){
				System.out.println("VENCEDOR!!!!!!!!");
				System.out.println("O Fitness do vencedor foi de " + g.getFitness());
			}

			}



	private static int getBest(List<Game> game){
		int a = 0;
		double b =0;

		for(Game g: game){
			if(g.getFitness() > b){
				a = game.indexOf(g);
				b = g.getFitness();
			}
		}
		return a;
	}







}
