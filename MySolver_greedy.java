import java.lang.*;
import java.io.*;
import java.util.*;

public class MySolver_greedy{
    
    static float distance(float city1[], float city2[]){
	float x = Math.abs(city1[0] - city2[0]);
	float y = Math.abs(city1[1] - city2[1]);
	float distance = (float)Math.sqrt(x*x + y*y);
	return distance;
    }

    static ArrayList<Integer> solve(ArrayList<float[]> cities){
	int n = cities.size();
	
	float[][] dist = new float[n][n];
	for(int i=0; i<n; i++){
	    for(int j=0; j<n; j++){
		dist[i][j] = dist[j][i] = distance(cities.get(i), cities.get(j));
	    }
	}

	int current_city = 0;
	ArrayList<Integer> unvisited_cities = new ArrayList<Integer>(n-1);
	ArrayList<Float> distance_of_cities = new ArrayList<Float>(n-1);
	ArrayList<Integer> solution = new ArrayList<Integer>();
	solution.add(current_city);
		
	for(int i=1; i<n; i++){
	    float distance_from_current_city = dist[current_city][i];
	    unvisited_cities.add(i);
	    distance_of_cities.add(distance_from_current_city);
	}
	
	int unvisited_n = unvisited_cities.size();
	while(unvisited_n != 0){
	    float min = distance_of_cities.get(0);
	    for(int i=1; i<unvisited_n; i++){
		float distance_of_city = distance_of_cities.get(i);
		if(distance_of_city < min)
		    min = distance_of_city;
	    }
	    int min_distance_of_index = distance_of_cities.indexOf(min);
	    int next_city = unvisited_cities.get(min_distance_of_index);
	    unvisited_cities.remove(min_distance_of_index);
	    distance_of_cities.clear();
	    solution.add(next_city);
	    current_city = next_city;
	    unvisited_n -= 1;
	    for(int i=0; i<unvisited_n; i++){
		int j = unvisited_cities.get(i);
		float distance_from_current_city = dist[current_city][j];
		distance_of_cities.add(distance_from_current_city);
	    }
	}
	return solution;
    }
    
    public static void main(String[] args){
	assert args.length > 1;
	ArrayList<Integer> solution = solve(Common.read_input(args[0]));
	Common.print_solution(solution);
	Common.overwrite_solution(solution, args[1]);
    }
	    
}
