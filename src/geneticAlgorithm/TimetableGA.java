package geneticAlgorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


/**
 * Don't be daunted by the number of classes in this chapter -- most of them are
 * just simple containers for information, and only have a handful of properties
 * with setters and getters.
 * 
 * The real stuff happens in the GeneticAlgorithm class and the Timetable class.
 * 
 * The Timetable class is what the genetic algorithm is expected to create a
 * valid version of -- meaning, after all is said and done, a chromosome is read
 * into a Timetable class, and the Timetable class creates a nicer, neater
 * representation of the chromosome by turning it into a proper list of Classes
 * with rooms and professors and whatnot.
 * 
 * The Timetable class also understands the problem's Hard Constraints (ie, a
 * professor can't be in two places simultaneously, or a room can't be used by
 * two classes simultaneously), and so is used by the GeneticAlgorithm's
 * calcFitness class as well.
 * 
 * Finally, we overload the Timetable class by entrusting it with the
 * "database information" generated here in initializeTimetable. Normally, that
 * information about what professors are employed and which classrooms the
 * university has would come from a database, but this isn't a book about
 * databases so we hardcode it.
 * 
 * @author bkanber
 *
 */
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

        // Print fitness
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

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
        
        // Print classes
        System.out.println();
        Class classes[] = timetable.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + 
                    timetable.getModule(bestClass.getModuleId()).getModuleName());
            System.out.println("Group: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Room: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Professor: " + 
                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
            System.out.println("Time: " + 
                    timetable.getProfessor(bestClass.getProfessorId()).getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            //timeslot
            System.out.println("-----");
            
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

    /**
     * Creates a Timetable with all the necessary course information.
     * 
     * Normally you'd get this info from a database.
     * 
     * @return
     */
	private static Timetable initializeTimetable() {
		// Create timetable
		Timetable timetable = new Timetable();
	        
		Connection connection;
	    PreparedStatement ps;
	        
		// Set up rooms
		/*timetable.addRoom(1, "A1", 15);
		timetable.addRoom(2, "B1", 30);
		timetable.addRoom(4, "D1", 20);
		timetable.addRoom(5, "F1", 25);*/
	    
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

		// Set up timeslotssss
		/*timetable.addTimeslot(1, "Mon 9:00 - 11:00");
		timetable.addTimeslot(2, "Mon 11:00 - 13:00");
		timetable.addTimeslot(3, "Mon 13:00 - 15:00");
		timetable.addTimeslot(4, "Tue 9:00 - 11:00");
		timetable.addTimeslot(5, "Tue 11:00 - 13:00");
		timetable.addTimeslot(6, "Tue 13:00 - 15:00");
		timetable.addTimeslot(7, "Wed 9:00 - 11:00");
		timetable.addTimeslot(8, "Wed 11:00 - 13:00");
		timetable.addTimeslot(9, "Wed 13:00 - 15:00");
		timetable.addTimeslot(10, "Thu 9:00 - 11:00");
		timetable.addTimeslot(11, "Thu 11:00 - 13:00");
		timetable.addTimeslot(12, "Thu 13:00 - 15:00");
		timetable.addTimeslot(13, "Fri 9:00 - 11:00");
		timetable.addTimeslot(14, "Fri 11:00 - 13:00");
		timetable.addTimeslot(15, "Fri 13:00 - 15:00");*/

		// Set up professors
		/*timetable.addProfessor(1, "Dr P Smith");
		timetable.addProfessor(2, "Mrs E Mitchell");
		timetable.addProfessor(3, "Dr R Williams");
		timetable.addProfessor(4, "Mr A Thompson");*/
	    
	    
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
    	//System.out.println(timetable.getProfessor(1).getRandomTimeslot().toString());

		// Set up modules and define the professors that teach them
		/*timetable.addModule(1, "cs1", "Computer Science", new int[] { 1 });
		timetable.addModule(2, "en1", "English", new int[] { 3 });
		timetable.addModule(3, "ma1", "Maths", new int[] { 2 });
		timetable.addModule(4, "ph1", "Physics", new int[] { 3 });
		timetable.addModule(5, "hi1", "History", new int[] { 4 });
		timetable.addModule(6, "dr1", "Drama", new int[] { 1 });*/

		
		//set up modules
	    try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        
            ps = connection.prepareStatement("SELECT * FROM `module`");
	        ResultSet result1 = ps.executeQuery();
	        while(result1.next()){
	        	timetable.addModule(result1.getInt("moduleId"), result1.getString("moduleCode"), result1.getString("moduleName"), result1.getInt("professorId"));
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	    	ex.printStackTrace();
	    }
	    
		//we defined it and to use it we have changed module structure to one professor can give it
	    //this deletes a prof who has no free time and his module
		timetable.delNonFreeProfs();

	    
		// Set up student groups and the modules they take.
		/*timetable.addGroup(1, 10, new int[] { 1, 3, 4 });
		timetable.addGroup(2, 30, new int[] { 15 });
		timetable.addGroup(3, 18, new int[] { 5 });
		timetable.addGroup(4, 25, new int[] { 8 });*/

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
