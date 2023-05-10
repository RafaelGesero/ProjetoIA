package Game.controllers;

import Game.nn.FeedForwardNN;

public class NnController implements GameController {

    private  FeedForwardNN nn;

    public NnController(FeedForwardNN nn){
        this.nn = nn;
        nn.initializeWeights();
    }

    @Override
    public double[] nextMove(double[] currentState) {
        double[] mov = new double[currentState.length];
        for(int i= 0; i < currentState.length; i++){
           if(currentState[i] > 0.5)
               mov[i] = 1.0;
           else
               mov[i] = 0.0;
        }
        return mov;
    }
}
