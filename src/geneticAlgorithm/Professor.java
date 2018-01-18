package geneticAlgorithm;

import java.util.HashMap;

/**
 * Simple Professor abstraction.
 */
public class Professor {
    private final int professorId;
    private final String professorName;
	private final HashMap<Integer, Timeslot> timeslots;


    /**
     * Initalize new Professor
     * 
     * @param professorId The ID for this professor
     * @param professorName The name of this professor
     */
    public Professor(int professorId, String professorName){
        this.professorId = professorId;
        this.professorName = professorName;
		this.timeslots = new HashMap<Integer, Timeslot>();
    }
    
    /**
     * Get professorId
     * 
     * @return professorId
     */
    public int getProfessorId(){
        return this.professorId;
    }
    
    /**
     * Get professor's name
     * 
     * @return professorName
     */
    public String getProfessorName(){
        return this.professorName;
    }

	/**
	 * Add new timeslot
	 * 
	 * @param timeslotId
	 * @param timeslot
	 */
	public void addTimeslot(int timeslotId, String timeslot) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
	}
	
	/**
	 * Get timeslot by timeslotId
	 * 
	 * @param timeslotId
	 * @return timeslot
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Get timeslot by timeslotId
	 * 
	 * @param timeslotId
	 * @return timeslot
	 */
	public HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}
	
	/**
	 * Get random timeslotId
	 * 
	 * @return timeslot
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}
}
