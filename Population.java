/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.algorithms;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Barış Şenyerli
 */
public class Population {
    private ArrayList<Route> routes = new ArrayList<Route> (
            GeneticAlgorithm.POPULATION_SIZE);
    public ArrayList<Route> getRoutes() { return routes; }
    public Population( int populationSize, GeneticAlgorithm geneticAlgorithm ){
        IntStream.range(0, populationSize).forEach( x-> routes.add( new Route(geneticAlgorithm.getInitialRoute())));
    }
    public Population( int populationSize, ArrayList<City> cities ) {
        IntStream.range(0, populationSize).forEach( x -> routes.add( new  Route (cities)));
    }
    
    
    public void sortRoutesByFitness() {
        routes.sort((route1, route2) -> {
            int flag = 0;
            if( route1.getFitness() > route2.getFitness()) flag = -1;
            else if (route1.getFitness() < route2.getFitness()) flag = 1;
            return flag;
        });
    }
}
