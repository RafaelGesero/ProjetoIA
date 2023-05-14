package Game.Game;

import Game.controllers.GameController;
import Game.controllers.NnController;
import Game.nn.FeedForwardNN;
import Game.space.Board;
import Game.space.Commons;
import Game.space.SpaceInvaders;

import java.util.Random;

public class Game{
    private FeedForwardNN nn;
    private GameController g;

    private Board board;

    private long seed;

    private double sumFitness = 0;



    public Game(){
        nn = new FeedForwardNN(Commons.STATE_SIZE, Commons.NUMBER_OF_ALIENS_TO_DESTROY, Commons.NUM_ACTIONS);
        g = new NnController(nn);
        board= new Board(g);
        for(int i = 0; i < 3; i++){
            seed = new Random().nextInt(100);
            board.setSeed(seed);
            initGame();
        }


    }

    public Game(double[] values){
        nn = new FeedForwardNN(Commons.STATE_SIZE, Commons.NUMBER_OF_ALIENS_TO_DESTROY, Commons.NUM_ACTIONS,values);
        g = new NnController(nn);
        board = new Board(g);
        for(int i = 0; i < 3; i++){
            seed = new Random().nextInt(100);
            board.setSeed(seed);
            initGame();
        }
    }



public Board getBoard(){
        return board;
}

public void initGame(){
        board.run();
        totalFitness(board.getFitness());
}

private void totalFitness(double fitness){
        sumFitness += fitness;
}



public FeedForwardNN getNn(){
        return nn;
}

public Double getFitness(){
        return sumFitness/3;
    }

    public GameController getController(){
        return g;
    }

public long getSeed(){return seed;}

}
