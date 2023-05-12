package Game.controllers;

import Game.nn.FeedForwardNN;

import java.util.Arrays;


public class NnController implements GameController {

    private  FeedForwardNN nn;

    public NnController(FeedForwardNN nn){
        this.nn = nn;
        nn.initializeWeights();
    }
    @Override
    public double[] nextMove(double[] currentState) {
        double[] forward = nn.forward(currentState);
       double[] mov = new double[forward.length];

        return forward;
    }




}
