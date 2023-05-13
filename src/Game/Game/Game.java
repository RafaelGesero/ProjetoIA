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



    public Game(){
        nn = new FeedForwardNN(Commons.STATE_SIZE, Commons.NUMBER_OF_ALIENS_TO_DESTROY, Commons.NUM_ACTIONS);
        g = new NnController(nn);
        board= new Board(g);
        seed = new Random().nextInt(1000);
        board.setSeed(seed);
        board.run();
    }

    public Game(double[] values){
        nn = new FeedForwardNN(Commons.STATE_SIZE, Commons.NUMBER_OF_ALIENS_TO_DESTROY, Commons.NUM_ACTIONS,values);
        g = new NnController(nn);
        board = new Board(g);
        seed = new Random().nextInt(1000);
        board.setSeed(seed);
        board.run();

    }



public Board getBoard(){
        return board;
}

public FeedForwardNN getNn(){
        return nn;
}

public Double getFitness(){
        return board.getFitness();
    }

    public GameController getController(){
        return g;
    }

public long getSeed(){return seed;}

}
