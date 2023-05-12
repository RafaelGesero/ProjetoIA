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


    public Game(){
        nn = new FeedForwardNN(Commons.STATE_SIZE, (Commons.STATE_SIZE + Commons.NUM_ACTIONS)/2, Commons.NUM_ACTIONS);
        g = new NnController(nn);
        board= new Board(g);
        board.setSeed(5);
        board.run();
    }

    public Game(double[] values){
        nn = new FeedForwardNN(Commons.STATE_SIZE, (Commons.STATE_SIZE + Commons.NUM_ACTIONS)/2, Commons.NUM_ACTIONS,values);
        g = new NnController(nn);
        board = new Board(g);
        board.setSeed(5);
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



}
