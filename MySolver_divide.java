import java.lang.*;
import java.io.*;
import java.util.*;

class City{
    public float x;
    public float y;

    public City(float x, float y){
	this.x = x;
	this.y = y;
    }
}

class CityComparator_X implements Comparator<City>{

    @Override
    public int compare(City city1, City city2){
	return city1.x < city2.x ?-1 : 1;
    }
}

class CityComparator_Y implements Comparator<City>{

    @Override
    public int compare(City city1, City city2){
	return city1.y < city2.y ?-1 : 1;
    }
}


public class MySolver_divide{

    static float distance(float city1[], float city2[]){
	float x = Math.abs(city1[0] - city2[0]);
	float y = Math.abs(city1[1] - city2[1]);
	float distance = (float)Math.sqrt(x*x + y*y);
	return distance;
    }


    static ArrayList<float[]> sort(ArrayList<float[]> cities){
	ArrayList<City> buffer_cities = new ArrayList<City>();
	float min_x = (cities.get(0))[0];
	float max_x = (cities.get(0))[0];
	float min_y = (cities.get(0))[1];
	float max_y = (cities.get(0))[1];
	for(int i=0; i<cities.size(); i++){
	    float x = (cities.get(i))[0];
	    float y = (cities.get(i))[1];
	    if(x < min_x)
		min_x = x;
	    if(y < min_y)
		min_y = y;
	    if(max_x < x)
		max_x = x;
	    if(max_y < y)
		max_y = y;
	    City city = new City(x, y);
	    buffer_cities.add(city);
	}
	if((max_x - min_x) > (max_y - min_y)){
	   Collections.sort(buffer_cities, new CityComparator_X());
	}else{
	    Collections.sort(buffer_cities, new CityComparator_Y());
	}
	ArrayList<float[]> sorted_cities = new ArrayList<float[]>(); 
	for(int i=0; i<buffer_cities.size(); i++){
	    float x = buffer_cities.get(i).x;
	    float y = buffer_cities.get(i).y;
	    float[] city = {x, y};
	    sorted_cities.add(city);
	}
	return sorted_cities;
    }
    
    static ArrayList<ArrayList<Integer>>
	divide(ArrayList<float[]> cities,
	       ArrayList<Integer>index_of_cities,
	       ArrayList<ArrayList<Integer>> divided_cities){

	ArrayList<float[]> position_cities = new ArrayList<float[]>();
	for(int i=0; i<index_of_cities.size(); i++){
	    int index = index_of_cities.get(i);
	    float[] city = cities.get(index);
	    position_cities.add(city);
	}

	ArrayList<float[]> sorted_position = sort(position_cities);
	ArrayList<Integer> index_of_sorted_cities = new ArrayList<Integer>();
	ArrayList<Float> x_of_cities = new ArrayList<Float>();

	for(int i=0; i<cities.size(); i++){
	    x_of_cities.add(cities.get(i)[0]);
	}

	for(int i=0; i<sorted_position.size(); i++){
	    int index = x_of_cities.indexOf(sorted_position.get(i)[0]);
	    index_of_sorted_cities.add(index);
	}
	
	
	if(index_of_cities.size() <= 5){
	    divided_cities.add(index_of_cities);
	    System.out.println(index_of_cities);
	}else{
	    int middle = index_of_cities.size()/2 + 1;
	    ArrayList<Integer> left_cities = new ArrayList<Integer>();
	    ArrayList<Integer> right_cities = new ArrayList<Integer>();
	    for(int i=0; i<middle; i++){
		int index = index_of_sorted_cities.get(i);
		left_cities.add(index);
	    }
	    for(int i=middle-1; i<index_of_cities.size(); i++){
		int index = index_of_sorted_cities.get(i);
		right_cities.add(index);
	    }
	    divide(cities, left_cities, divided_cities);
	    divide(cities, right_cities, divided_cities);
    
	}
	return divided_cities;
    }

    static int[] get_merge_cities(ArrayList<float[]> cities,
				  ArrayList<Integer> first,
				  ArrayList<Integer> second,
				  int common_point){
	int common_in_first = first.indexOf(common_point);
	int common_in_second = second.indexOf(common_point);

	int before_common_in_first = common_in_first - 1;
	int after_common_in_first = common_in_first + 1;

	int before_common_in_second = common_in_second - 1;
	int after_common_in_second = common_in_second + 1;
		
	if(common_in_first == (first.size() - 1)){
	    after_common_in_first = 0;
	}
	if(common_in_first == 0){
	    before_common_in_first = first.size() - 1;
	}
	if(common_in_second == (second.size() - 1)){
	    after_common_in_second = 0;
	}
	if(common_in_second == 0){
	    before_common_in_second = second.size() - 1;
	}
	float[] common_city = cities.get(common_point);
	ArrayList<float[]> cities_around_common = new ArrayList<float[]>();
	
	float[] city1 = cities.get(first.get(before_common_in_first));
	float[] city2 = cities.get(first.get(after_common_in_first));
	float[] city3 = cities.get(second.get(before_common_in_second));
	float[] city4 = cities.get(second.get(after_common_in_second));
	
	
	ArrayList<Float> distances = new ArrayList<Float>();
	float distance1 = distance(common_city, city1);
	float distance2 = distance(common_city, city2);
	float distance3 = distance(common_city, city3);
	float distance4 = distance(common_city, city4);
	distances.add(distance1 + distance3);
	distances.add(distance1 + distance4);
	distances.add(distance2 + distance3);
	distances.add(distance2 + distance4);
	
	float max_distance = Collections.max(distances);
	int merge_cities[] = new int[2];
	if(max_distance == (distance1 + distance3)){
	    merge_cities[0] = cities.indexOf(city1);
	    merge_cities[1] = cities.indexOf(city3);
	}
	if(max_distance == (distance1 + distance4)){
	    merge_cities[0] = cities.indexOf(city1);
	    merge_cities[1] = cities.indexOf(city4);
	}
	if(max_distance == (distance2 + distance3)){
	    merge_cities[0] = cities.indexOf(city2);
	    merge_cities[1] = cities.indexOf(city3);
	}
	if(max_distance == (distance2 + distance4)){
	    merge_cities[0] = cities.indexOf(city2);
	    merge_cities[1] = cities.indexOf(city4);
	}
	return merge_cities;
    }

    
    static ArrayList<Integer>
	solve_all_permutation(ArrayList<Integer> index_of_cities,
			      ArrayList<float[]> cities){
	ArrayList<float[]> divided_cities = new ArrayList<float[]>();
	for(int i=0; i<index_of_cities.size(); i++){
	    int index = index_of_cities.get(i);
	    float[] city = cities.get(index);
	    divided_cities.add(city);
	}
	ArrayList<Integer> solution =  MySolver_all.solve(divided_cities);
	ArrayList<Integer> solution_city_numbers = new ArrayList<Integer>();
	for(int i=0; i<index_of_cities.size(); i++){
	    int index = solution.get(i);
	    int city_number = index_of_cities.get(index);
	    solution_city_numbers.add(city_number);
	}
	return solution_city_numbers;
    }

    static ArrayList<ArrayList<Integer>>
	integrate(ArrayList<ArrayList<Integer>> divided_solutions,
		  ArrayList<float[]> cities,
		  ArrayList<Integer> common_points){
	assert divided_solutions.size() > 0;
	if(divided_solutions.size() == 1){
	}else{
	    int common_point = common_points.get(0);
	    ArrayList<Integer> solution_first = divided_solutions.get(0);
	    ArrayList<Integer> solution_second = divided_solutions.get(1);
	    int first_index = get_merge_cities(cities, solution_first,
					       solution_second,
					       common_point)[0];
	    int second_index = get_merge_cities(cities, solution_first,
					       solution_second,
					       common_point)[1];
	    int common_point_in_first = solution_first.indexOf(common_point);
	    int common_point_in_second = solution_second.indexOf(common_point);
	    
	    solution_second.remove(common_point_in_second);
	    solution_first.addAll(common_point_in_first, solution_second);
	    common_points.remove(0);
	    divided_solutions.remove(1);
	    integrate(divided_solutions, cities, common_points);
	}
	return divided_solutions;
    }
    
    static ArrayList<Integer> solve(ArrayList<float[]> cities){

	ArrayList<Integer> index_of_cities = new ArrayList<Integer>();
	for(int i=0; i<cities.size(); i++){
	    index_of_cities.add(i);
	}

	ArrayList<ArrayList<Integer>> divided_cities_index
	    = divide(cities, index_of_cities, new ArrayList<ArrayList<Integer>>());

	ArrayList<Integer> common_points = new ArrayList<Integer>();
	for(int i=1; i<divided_cities_index.size(); i++){
	    common_points.add((divided_cities_index.get(i)).get(0));
	}
		
	ArrayList<ArrayList<Integer>> divided_solutions
	    = new ArrayList<ArrayList<Integer>>();
	
	for(int i=0; i<divided_cities_index.size(); i++){
	    ArrayList<Integer> divided_solution
		= solve_all_permutation(divided_cities_index.get(i), cities);
	    divided_solutions.add(divided_solution);
	}

	ArrayList<Integer> solution
	    = (integrate(divided_solutions, cities, common_points)).get(0);
	return solution;
    }


    public static void main(String[] args){
	assert args.length > 1;
	ArrayList<Integer> solution = solve(Common.read_input(args[0]));
	Common.print_solution(solution);
	Common.overwrite_solution(solution, args[1]);
    }
}
