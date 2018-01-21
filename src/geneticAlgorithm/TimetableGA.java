package geneticAlgorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
 
public class TimetableGA {
	public static void executeGA() {

    	// Get a Timetable object with all the available information.
        Timetable timetable = initializeTimetable();
        
        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(1000, 0.01, 0.9, 2, 100);
        
        // Initialize population
        Population population = ga.initPopulation(timetable);
        
        // Evaluate population
        ga.evalPopulation(population, timetable);
        
        // Keep track of current generation
        int generation = 1;
        
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, 1000) == false
            && ga.isTerminationConditionMet(population) == false) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

       
        timetable.createClasses(population.getFittest(0));
        //clear schedule table
        Connection connection;
        PreparedStatement ps;
        try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement("TRUNCATE TABLE `schedule`");
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
        
        
        Class classes[] = timetable.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            
            //to fill schedule table in database
            try {
    			java.lang.Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
                ps = connection.prepareStatement("INSERT INTO `schedule` VALUES (?, ?, ?, ?)");
                ps.setInt(1, bestClass.getProfessorId());
                ps.setInt(2, bestClass.getModuleId());
                ps.setInt(3, bestClass.getRoomId());
                ps.setInt(4, bestClass.getTimeslotId());
                ps.executeUpdate();
                
            } catch (SQLException | ClassNotFoundException ex) {
            	ex.printStackTrace();
            }
            
            classIndex++;
        }	
	}

    public static void main(String[] args) {
    	executeGA();
    }


	private static Timetable initializeTimetable() {
		// Create timetable
		Timetable timetable = new Timetable();
	        
		Connection connection;
	    PreparedStatement ps;
	    
	    //set up rooms
	    try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        
	        ps = connection.prepareStatement("SELECT * FROM `room`");
	        ResultSet result1 = ps.executeQuery();
	        while(result1.next()){
	        	timetable.addRoom(result1.getInt("roomId"), result1.getString("roomNumber"), result1.getInt("roomCapacity"));
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	    	ex.printStackTrace();
	    }

		//set up professors and their free time
	    try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        
	        ps = connection.prepareStatement("SELECT * FROM `available`");
	        ResultSet result1 = ps.executeQuery();
	        while(result1.next()){
	        	timetable.addProfessor(result1.getInt("id"), result1.getString("professorName"));
	        
	            ps = connection.prepareStatement("SELECT * FROM `timeslot`");
	            ResultSet result2 = ps.executeQuery();
	            while(result2.next()) {
		            	if(result1.getInt(result2.getString("timeslot")) == 1) {
			            	timetable.getProfessor(result1.getInt("id")).addTimeslot(
			            			result2.getInt("timeslotId"), result2.getString("timeslot"));
		            	}
		            	//System.out.println(result2.getString("timeslotId"));
	            }
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	    	ex.printStackTrace();
	    }
   
		//set up modules
	    try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        
            ps = connection.prepareStatement("SELECT * FROM `module`");
	        ResultSet result1 = ps.executeQuery();
	        while(result1.next()){
	        	timetable.addModule(result1.getInt("moduleId"), result1.getString("moduleCode"), 
	        			result1.getString("moduleName"), result1.getInt("professorId"));
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	    	ex.printStackTrace();
	    }
	    
		//we defined it and to use it we have changed module structure to one professor can give it
	    //this deletes a prof who has no free time and his module
	    
		timetable.delNonFreeProfs();

		//set up groups and modules they take
	    try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        
            ps = connection.prepareStatement("SELECT * FROM `groups`");
	        ResultSet result1 = ps.executeQuery();
	        while(result1.next()){
	        	//removes unnecessary modules.
	        	int[] moduleArray = Arrays.stream(result1.getString("moduleIds").split(","))
	        		    .mapToInt(Integer::parseInt)
	        		    .toArray();
	        	for(int module: moduleArray) {
	        		if(!timetable.moduleExist(module))
	        			moduleArray = removeElements(moduleArray, module);
	        	}
	        	
	        	timetable.addGroup(result1.getInt("groupId"), result1.getInt("groupSize"), moduleArray);
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	    	ex.printStackTrace();
	    }		
		return timetable;
	}
	
	//declared to delete a module which does not exist in timetable
	public static int[] removeElements(int[] input, int deleteMe) {
	    int[] result = null;
	    int counter = 0;
	    for(int item : input) {
	        if(deleteMe != item) {
	            result[counter] = item;
	            counter++;
	        }
	    }
	    return result;
	}
}
