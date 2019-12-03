/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Barış Şenyerli
 */
public class Route {
    private ArrayList<City> cities = new ArrayList<City>();
    private boolean isFitnessChanged = true;
    private double fitness;
    public ArrayList<City> getCities() {
        return cities;
    }
    public Route( GeneticAlgorithm geneticAlgorithm ) {
        geneticAlgorithm.getInitialRoute().forEach( x -> cities.add(null));
    }
    public Route( ArrayList<City> cities ) {
        this.cities.addAll( cities );
        Collections.shuffle( this.cities );
        
    }
    public double getFitness() {
        if( isFitnessChanged == true) {
            fitness = ( 1 / calculateTotalDistance() ) * 10000;
            isFitnessChanged = true;
        }
        return fitness;
    }
    public double calculateTotalDistance() {
        int citiesSize = this.cities.size();
        return ( int ) ( this.cities.stream().mapToDouble( x -> {
            int cityIndex = this.cities.indexOf( x );
            double returnValue = 0;
            if( cityIndex < citiesSize - 1 ) returnValue = x.measureDistance( 
            this.cities.get(cityIndex + 1) );
            return returnValue;
        }).sum() + this.cities.get(0).measureDistance( this.cities.get(citiesSize - 1)
        ));
    }
    public String toString() { return Arrays.toString(cities.toArray()); }
}