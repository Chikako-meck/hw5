import java.io.*;
import java.util.*;
import java.lang.*;

public class Common{

    static ArrayList<float[]> read_input(String filename){
	ArrayList<float[]> cities = new ArrayList<float[]>();
	try{
	    File f = new File(filename);
	    BufferedReader br = new BufferedReader(new FileReader(f));

	    String line = br.readLine();
	    line = br.readLine();
	    while(line != null){
		String[] xy = line.split(",", 0);
		float[] float_xy = {Float.valueOf(xy[0]), Float.valueOf(xy[1])};
		cities.add(float_xy);
		line = br.readLine();
	    }
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
	return cities;
    }
    
    static void print_solution(ArrayList<Integer> solution){
	System.out.println("index");
	for(int i=0; i<solution.size(); i++){
	    System.out.println(solution.get(i));
	}
    }			      

    static void overwrite_solution(ArrayList<Integer> solution,
				   String filename){
	try{
	    File output_file = new File(filename);
	    FileWriter fw = new FileWriter(output_file);

	    fw.write("index\n");
	    for(int i=0; i<solution.size(); i++){
		fw.write(solution.get(i) + "\n");
	    }
	    fw.close();
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
    }
}
