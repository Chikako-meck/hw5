import java.lang.*;
import java.io.*;
import java.util.*;

public class MySolver_all{

    static float distance(float city1[], float city2[]){
	float x = Math.abs(city1[0] - city2[0]);
	float y = Math.abs(city1[1] - city2[1]);
	float distance = (float)Math.sqrt(x*x + y*y);
	return distance;
    }

    static float distance_from_current(float[][] dist, int current_city,
				       ArrayList<Integer> unvisited_cities){
	if(unvisited_cities.isEmpty()){
	    return dist[0][current_city];
	}else{
	    ArrayList<Integer> buffer_cities = new ArrayList<Integer>(unvisited_cities);
	    int next_city = buffer_cities.get(0);
	    buffer_cities.remove(0);
	    float distance = dist[current_city][next_city];
	    return distance + distance_from_current(dist, next_city,
						    buffer_cities);
	}
    }

    public static ArrayList<ArrayList<Integer>>
	make_permutation(ArrayList<Integer> data) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> candidate = new ArrayList<>(data);
        ArrayList<Integer> perm = new ArrayList<>();
        return permutation(result, candidate, perm);
    }

    private static ArrayList<ArrayList<Integer>>
	permutation(ArrayList<ArrayList<Integer>> result,
		    ArrayList<Integer> candidate, ArrayList<Integer> perm) {
        if (candidate.size() == 0) {
            result.add(perm);
        }
        else {
            for (int i = 0; i < candidate.size(); i++) {
                ArrayList<Integer> p = new ArrayList<>(perm);
                ArrayList<Integer> c = new ArrayList<>(candidate);
                p.add(c.get(i));
                c.remove(i);
                permutation(result, c, p);
            }
        }

        return result;
    }
	
    static ArrayList<Integer> solve(ArrayList<float[]> cities){

	int cities_n = cities.size();
	float[][] dist = new float[cities_n][cities_n];
	for(int i=0; i<cities_n; i++){
	    for(int j=0; j<cities_n; j++){
		dist[i][j] = dist[j][i] = distance(cities.get(i), cities.get(j));
	    }
	}

	int current_city = 0;
	ArrayList<Integer> unvisited_cities = new ArrayList<Integer>();
	for(int i=1; i<cities_n; i++){
	    unvisited_cities.add(i);
	}

	ArrayList<Float> distances = new ArrayList<Float>();
	ArrayList<ArrayList<Integer>> permutated_list
	    = make_permutation(unvisited_cities);
	//	ArrayList<ArrayList<Integer>> buffer_list
	//  = make_permutatio
	int list_n = permutated_list.size();

	for(int i=0; i<list_n; i++){
	    float distance =
		distance_from_current(dist, current_city, permutated_list.get(i));
	    distances.add(distance);
	}

	float min_distance = distances.get(0);
	for(int i=1; i<list_n; i++){
	    float new_distance = distances.get(i);
	    if(new_distance < min_distance){
		min_distance = new_distance;
	    }
	}
	int min_index = distances.indexOf(min_distance);
	System.out.println("distance : " + min_distance);
	ArrayList<Integer> solution = permutated_list.get(min_index);
	solution.add(0, current_city);
	return solution;
    }
	
       

    public static void main(String[] args){
	assert args.length > 1;
	ArrayList<Integer> solution = solve(Common.read_input(args[0]));
	Common.print_solution(solution);
	Common.overwrite_solution(solution, args[1]);
    }
}


