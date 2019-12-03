package genetic.algorithms;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int POPULATION_SIZE = 8;
    private ArrayList<City> initialRoute = null;
    public static final int NUMB_OF_ELITE_ROUTES = 1;
    public static final int NUMB_OF_GENERATIONS = 30;
    public GeneticAlgorithm(ArrayList<City> initialRoute) {this.initialRoute = initialRoute; }
    public Population evolve(Population population) { return mutatePopulation(crossoverPopulation(population)); }
    Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getRoutes().size(), this);
        IntStream.range(0, NUMB_OF_ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
        IntStream.range(NUMB_OF_ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Route route1 = selectTournamentPopulation(population).getRoutes().get(0);
            Route route2 = selectTournamentPopulation(population).getRoutes().get(0);
            crossoverPopulation.getRoutes().set(x, crossoverRoute(route1, route2));});
        return crossoverPopulation;
    }
    public ArrayList<City> getInitialRoute() {
        return initialRoute;
    }
    

    Population mutatePopulation(Population population) {
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= NUMB_OF_ELITE_ROUTES).forEach(x -> mutateRoute(x));
        return population;
    }

    Route crossoverRoute(Route route1, Route route2) {
        Route crossoverRoute = new Route(this);
        Route tempRoute1 = route1;
        Route tempRoute2 = route2;
        if(Math.random() < .5) {
            tempRoute1 = route2;
            tempRoute2 = route1;
        }
        return fillNullsInCrossoverRoute(crossoverRoute, tempRoute2);
    }

    private Route fillNullsInCrossoverRoute(Route crossoverRoute, Route route) {
        route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> {
            for (int y = 0; y < route.getCities().size(); y++) {
                if (crossoverRoute.getCities().get(y) == null) {
                    crossoverRoute.getCities().set(y, cityX);
                    break;
                }
            }
        });
        return crossoverRoute;
    }
    
    
    Population selectTournamentPopulation(Population population) {
        Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
        IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes().set(
        x,population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
        return tournamentPopulation;
    }
    // örnek yol mutasyonu
    // orjinal güzergah: [Boston, Denver, Los Angeles, Seattle, Chicago, San Francisco, Dallas, Houston]
    // değişim güzergah: [Boston, Denver, New York, Austin, Los Angeles, Seattle, San Francisco, Chicago, Dallas, Houston]
    Route mutateRoute(Route route) {
        route.getCities().stream().filter( x-> Math.random() < MUTATION_RATE).forEach(cityX -> {
            int y = (int) (route.getCities().size() * Math.random());
            City cityY = route.getCities().get(y);
            route.getCities().set(route.getCities().indexOf(cityX), cityY);
            route.getCities().set(y, cityX);
        }); 
        return route;
    }
    
}
