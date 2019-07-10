import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
	
	
	
	//The TSP_FILE variable must be set depending on your directory before running this application
	//For ceratin cities you can set the TSP_OPT_FILE in order to show the optimal route 
	//note that both files must correspond to the same number, e.g. if choosing "TSP_48.txt" then you must also choose "TSP_48_OPT.txt"	
	//You can also set the number of cities being worked on

	static String TSP_FILE = "C:\\Eclipse-workspace\\Travelling_Salesperson_Problem\\TSP Data\\TSP_48.txt";
	static String TSP_OPT_FILE = "C:\\Eclipse-workspace\\Travelling_Salesperson_Problem\\TSP Data\\TSP_48_OPT.txt";
	
	
	
	public static void main(String args[]) {
		
		
		
		
		double T0 = 2*averageOfDistances();//SEND DATA TO THIS METHOD
	
		System.out.println("Simulated Annealing"); System.out.println("");
	
		for(int i=0;i<10;i++) {
	simulatedAnnealing(T0,1000000); //PARAMETERS: (Starting Temperature, No. of iterations)
		//SEND DATA TO THIS METHOD FOR CODREUNNER
		//SEND CITIES N TO EACH METHOD
		}
		
		//COMMENT THIS OUT IF OPTIMAL TOUR LENGTH NOT NEEDED
		optimalTour();
		
		
	}
	
	
	
	
	public static void simulatedAnnealing(double T, double iter) {
		
		
		
		double[][] tsp;
		tsp =  ReadArrayFile(TSP_FILE, " ");
		//PrintArray(tsp);
	
		int n=tsp.length;
		ArrayList<Integer> x = RandPerm(n);
		
		double f=0; double f1=0; ArrayList<Integer> x1 = new ArrayList<Integer>(); double p=0; double T0=T;
		
		double T_iter = 0.001;
		
		double Y = Math.pow(T_iter/T0, 1/iter);
		
		//System.out.print(" Lambda: " + Y);
		
		for(int i=0;i<iter-1;i++) {
			
			f = TSPfitness(n, x, tsp);
			//System.out.println("Tour length : " + f + " [iteration " + i + "]");
			x1 = SmallChange(x);
			
			f1 = TSPfitness(n, x1, tsp);
			
			if(f1>f) {
				p = PR(f1,f,T); 
				if(p < UR(0, 1)) {
					//Reject change
				}
				else {
					//accept change
					x=(ArrayList<Integer>) x1.clone();
					f=f1;
				}
			}
			else {
				x=(ArrayList<Integer>) x1.clone();
				f=f1;
			}
			
			T = T*Y;
			//System.out.println("T: " + T);
			
			
		}
		System.out.println(" Tour length : " + f);
		//System.out.println("Lambda: " + Y);
		
		
	}
	
	public static ArrayList<Integer> RandPerm(int n)
	{
	ArrayList<Integer> T = new ArrayList<Integer>();
	ArrayList<Integer> P = new ArrayList<Integer>();
	
	
	int i=0;
	for(i=1;i<=n;i++)
	{
	P.add(i-1);
	}
	/*
	System.out.print("set of cities:      ");
	for (Integer num : P) {
		System. out. print(num + ", ");
		}
	System.out.print(" | ");
	*/
	
	
	while(P.size()>0){
		i = UI(0, P.size()-1);
		T.add(P.get(i));
		P.remove(i);
	}
	/*
	System.out.print("Random permutation: ");
	for (Integer num : T) {
		System. out. print(num + ", ");
		}
	System.out.print(" | ");
	*/
	
	return T;
		
	}
	
	public static double TSPfitness(int n, ArrayList<Integer> T, double[][] D) {
		double s=0;
		int i=0;
		int a=0, b=0;
		
		
		for(i=1;i<n;i++)
		{
		a = T.get(i-1);
		b = T.get(i);
		s = s + D[a][b];
		}
		
		int end_city = T.get(n-1);
		int start_city = T.get(0);
		s = s + D[end_city][start_city];
		
		
		
		return s;
	}

	public static ArrayList<Integer> SmallChange(ArrayList<Integer> array1)
	{	
		
		//System.out.print("Small change method  ");
		
		ArrayList<Integer> array2 = (ArrayList<Integer>) array1.clone();
		int i1=0, i2=0;
		int t1=0, t2=0;
		
		i1 = UI(0, array1.size()-1);
		i2 = UI(0, array1.size()-1);
		
		t1 = array1.get(i1);
		t2 = array1.get(i2);
		
		array2.set(i2, t1);
		array2.set(i1, t2);
		/*
		System.out.print("array1: ");
		for (Integer num : array1) {
			System. out. print(num + ", ");
			}
		
		System.out.print(" | ");
		
		
		System.out.print("array2: ");
		for (Integer num : array2) {
			System. out. print(num + ", ");
			}
		
		*/
		return array2;
	}

	public static double PR(double f1,double f,double T) {
		double returnvalue=0;
		
		
		returnvalue= Math.exp(((-Math.abs(f1-f))/T));
		
		return returnvalue;
	}

	//TSP Methods  here:
	
	//Print a 2D double array to the console Window
		static public void PrintArray(double x[][])
		{
			for(int i=0;i<x.length;++i)
			{
				for(int j=0;j<x[i].length;++j)
				{
					System.out.print(x[i][j]);
					System.out.print(" ");
				}
				System.out.println();
			}
		}
		//This method reads in a text file and parses all of the numbers in it. This method is for reading in a square 2D numeric array from a text file
		//'sep' is the separator between columns
		static public double[][] ReadArrayFile(String filename,String sep)
		{
			double res[][] = null;
			try
			{
				BufferedReader input = null;
				input = new BufferedReader(new FileReader(filename));
				String line = null;
				int ncol = 0;
				int nrow = 0;
				
				while ((line = input.readLine()) != null) 
				{
					++nrow;
					String[] columns = line.split(sep);
					ncol = Math.max(ncol,columns.length);
				}
				res = new double[nrow][ncol];
				input = new BufferedReader(new FileReader(filename));
				int i=0,j=0;
				while ((line = input.readLine()) != null) 
				{
					
					String[] columns = line.split(sep);
					for(j=0;j<columns.length;++j)
					{
						res[i][j] = Double.parseDouble(columns[j]);
					}
					++i;
				}
			}
			catch(Exception E)
			{
				System.out.println("+++ReadArrayFile: "+E.getMessage());
			}
		    return(res);
		}
		//This method reads in a text file and parses all of the numbers in it
		
		//It takes in as input a string filename and returns an array list of Integers
		static public ArrayList<Integer> ReadIntegerFile(String filename)
		{
			ArrayList<Integer> res = new ArrayList<Integer>();
			Reader r;
			try
			{
				r = new BufferedReader(new FileReader(filename));
				StreamTokenizer stok = new StreamTokenizer(r);
				stok.parseNumbers();
				stok.nextToken();
				while (stok.ttype != StreamTokenizer.TT_EOF) 
				{
					if (stok.ttype == StreamTokenizer.TT_NUMBER)
					{
						res.add((int)(stok.nval));
					}
					stok.nextToken();
				}
			}
			catch(Exception E)
			{
				System.out.println("+++ReadIntegerFile: "+E.getMessage());
			}
		    return(res);
		}


	//CS2004 methods Here:
		
		//Shared random object
		static private Random rand;
		//Create a uniformly distributed random integer between aa and bb inclusive
		static public int UI(int aa,int bb)
		{
			int a = Math.min(aa,bb);
			int b = Math.max(aa,bb);
			if (rand == null) 
			{
				rand = new Random();
				rand.setSeed(System.nanoTime());
			}
			int d = b - a + 1;
			int x = rand.nextInt(d) + a;
			return(x);
		}
		//Create a uniformly distributed random double between a and b inclusive
		static public double UR(double a,double b)
		{
			if (rand == null) 
			{
				rand = new Random();
				rand.setSeed(System.nanoTime());
			}
			return((b-a)*rand.nextDouble()+a);
		}
		//This method reads in a text file and parses all of the numbers in it
		//It takes in as input a string filename and returns an array list of Doubles
		static public ArrayList<Double> ReadNumberFile(String filename)
		{
			ArrayList<Double> res = new ArrayList<Double>();
			Reader r;
			try
			{
				r = new BufferedReader(new FileReader(filename));
				StreamTokenizer stok = new StreamTokenizer(r);
				stok.parseNumbers();
				stok.nextToken();
				while (stok.ttype != StreamTokenizer.TT_EOF) 
				{
					if (stok.ttype == StreamTokenizer.TT_NUMBER)
					{
						res.add(stok.nval);
					}
					stok.nextToken();
				}
			}
			catch(Exception E)
			{
				System.out.println("+++ReadFile: "+E.getMessage());
			}
		    return(res);
		}

		public static void optimalTour() {
			
			ArrayList<Integer> tsp_opt = new ArrayList<Integer>();
			tsp_opt = ReadIntegerFile(TSP_OPT_FILE);
			double[][] tsp;
			tsp =  ReadArrayFile(TSP_FILE, " ");
			
			
			double f= TSPfitness(tsp.length,tsp_opt,tsp);
			System.out.println("");
			System.out.println("Optimal tour length via tsp_opt : " + f);
			
		}

		public static double averageOfDistances() {
			
			double[][] tsp;
			tsp =  ReadArrayFile(TSP_FILE, " ");
			double count=0;
			
			for(int i=0;i<tsp.length;++i)
			{
				for(int j=0;j<tsp[i].length;++j)
				{
				count = count + (tsp[i][j]);
					
				}
				count = count / (tsp.length);
			}
			//System.out.println("Average distances : " + count);
			
			return count;
			
		}
}

