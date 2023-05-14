package Game.controllers;

import java.io.*;
import java.util.*;

import Game.Game.Game;
import Game.nn.FeedForwardNN;
import Game.space.Commons;
import Game.space.SpaceInvaders;

import static java.lang.System.exit;

public class Main {
private static int vencedores =0;
	private static final int BESTS = 50;
	private static final double PROB_BEST_GAMES = 0.2;
	private static final double PROB_MUTATION = 0.3;
	private static final int POPULATION = 1000;

	private static final int MAX_MUTATION = 20;

	private static final int CROMOSSOME_SIZE =2110;

	public static void main(String[] args) {
		//showPlayer();
		List<Game> list = new ArrayList<>();

		for(int i = 0; i< POPULATION; i++){
			list.add(new Game());
		}

		int count =0;
		while (true) {
			count++;
			System.out.println("Geração " + count + " ->Melhor Fitness: " + bestFitness(list));

			//List<Game> best = tournament(list);
			List<Game> best = confront(list);
			//List<Game> best = rankSelection(list);
			list.clear();
			list = crossover(best);
		}
	}

	private static void showPlayer(){
		String fileName = "values.txt";
		double[] values = new double[CROMOSSOME_SIZE];

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String linha = br.readLine();
			String[] tokens = linha.substring(1, linha.length() - 1).split(",");
			for(int i = 0; i < CROMOSSOME_SIZE; i++){
				values[i] = Double.parseDouble(tokens[i].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Game g = new Game(values);
		int seed = new Random().nextInt(100);
		SpaceInvaders.showControllerPlaying(g.getController(), seed);
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
			double[] childChromossome1 = new double[CROMOSSOME_SIZE];
			double[] childChromossome2 = new double[CROMOSSOME_SIZE];

			// Define um ponto de corte aleatório
			int cutoff = r.nextInt(CROMOSSOME_SIZE);

			// Realiza o crossover uniforme
			for (int i = 0; i < CROMOSSOME_SIZE; i++) {
				if (i < cutoff) {
					childChromossome1[i] = parent1.getNn().getChromossome()[i];
					childChromossome2[i] = parent2.getNn().getChromossome()[i];


				} else {
					childChromossome1[i] = parent2.getNn().getChromossome()[i];
					childChromossome2[i] = parent1.getNn().getChromossome()[i];
				}
			}

			// Realiza a mutação
			double[] mutatedChildChromossome1 = mutation(childChromossome1);
			double[] mutatedChildChromossome2 = mutation(childChromossome2);

			// Cria o filho e adiciona na nova população
			Game child1 = new Game(mutatedChildChromossome1);
			getWinner(child1);
			aux.add(child1);

			Game child2 = new Game(mutatedChildChromossome2);
			getWinner(child2);
			aux.add(child2);
		}

		return aux;
	}



	private static double[] mutation(double[] values) {
		double [] valores = values;

		double r = new Random().nextDouble();
		int mutations = new Random().nextInt(20) +1;
		if(r <= PROB_MUTATION){
			for(int i =0; i<mutations; i++){
				valores[new Random().nextInt(values.length)] = new Random().nextDouble() * 2 - 1;
			}
		}
		return valores;
	}

	private static void getWinner(Game g){
		if(g.getBoard().getMessage().equals("Game won!")){
			System.out.println("VENCEDOR!!!!!!!!");
			System.out.println("O Fitness do vencedor foi de " + g.getFitness());
			//System.out.println(Arrays.toString(g.getNn().getChromossome()));
			vencedores++;
			writeFile(g);
			exit(0);
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

	private static void writeFile(Game g){
		FileWriter fw = null;
		try {
			fw = new FileWriter("Vencedor22"+vencedores+".txt" );
			double[] aux = g.getNn().getChromossome();

			for(int i = 0; i < aux.length; i++) {
				fw.write(aux[i] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}





}
