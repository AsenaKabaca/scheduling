package geneticAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Timetable is the main evaluation class for the class scheduler GA.
 * 
 * A timetable represents a potential solution in human-readable form, unlike an
 * Individual or a chromosome. This timetable class, then, can read a chromosome
 * and develop a timetable from it, and ultimately can evaluate the timetable
 * for its fitness and number of scheduling clashes.
 * 
 * The most important methods in this class are createClasses and calcClashes.
 * 
 * The createClasses method accepts an Individual (really, a chromosome),
 * unpacks its chromosome, and creates Class objects from the genetic
 * information. Class objects are lightweight; they're just containers for
 * information with getters and setters, but it's more convenient to work with
 * them than with the chromosome directly.
 * 
 * The calcClashes method is used by GeneticAlgorithm.calcFitness, and requires
 * that createClasses has been run first. calcClashes looks at the Class objects
 * created by createClasses, and figures out how many hard constraints have been
 * violated.
 * 
 */
public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.professors = new HashMap<Integer, Professor>();
		this.modules = new HashMap<Integer, Module>();
		this.groups = new HashMap<Integer, Group>();
	}

	/**
	 * "Clone" a timetable. We use this before evaluating a timetable so we have
	 * a unique container for each set of classes created by "createClasses".
	 * Truthfully, that's not entirely necessary (no big deal if we wipe out and
	 * reuse the .classes property here), but Chapter 6 discusses
	 * multi-threading for fitness calculations, and in order to do that we need
	 * separate objects so that one thread doesn't step on another thread's
	 * toes. So this constructor isn't _entirely_ necessary for Chapter 5, but
	 * you'll see it in action in Chapter 6.
	 * 
	 * @param cloneable
	 */
	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
	}

	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	private HashMap<Integer, Professor> getProfessors() {
		return this.professors;
	}

	
	 // Add new room
	public void addRoom(int roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	
	 // Add new professor
	public void addProfessor(int professorId, String professorName) {
		this.professors.put(professorId, new Professor(professorId, professorName));
	}

	// Add new module
	public void addModule(int moduleId, String moduleCode, String module, int professorId) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, module, professorId));
	}

	
	//Add new group
	public void addGroup(int groupId, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, groupSize, moduleIds));
		this.numClasses = 0;
	}


	//Create classes using individual's chromosome
	 
	public void createClasses(Individual individual) {
		// Init classes
		Class classes[] = new Class[this.getNumClasses()];

		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);

				// Add timeslot
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;

				// Add room
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				// Add professor
				classes[classIndex].addProfessor(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}
		this.classes = classes;
	}

	// Get room from roomId
	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	//Get random room
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	//Get professor from professorId
	public Professor getProfessor(int professorId) {
		return (Professor) this.professors.get(professorId);
	}

	//Get random timeslotId
	 
	public Timeslot getRandomTimeslot(int professorID) {
		Object[] timeslotArray = this.professors.get(professorID).getTimeslots().values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}
	
	//Get module from moduleId
	 
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}
	
	// Check if module exists.
	 
	public boolean moduleExist(int moduleId) {
		for(Map.Entry<Integer, Module> entry: this.modules.entrySet()) {
			if(entry.getValue().getModuleId() == moduleId)
				return true;
		}
		return false;
	}

	//Get moduleIds of student group
	 
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	// Get group from groupId
	 
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	//Get all student groups
	 
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	// Get classes
	 
	public Class[] getClasses() {
		return this.classes;
	}

	// Get number of classes that need scheduling
	 
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	
	 //Calculate the number of clashes between Classes generated by a
	 // chromosome.
	 
	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
			
			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
			// Check if professor is available
			for (Class classB : this.classes) {
				if (classA.getProfessorId() == classB.getProfessorId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}			
			// Check if group is available
			for(Class classB: this.classes) {
				if (classA.getGroupId() == classB.getGroupId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
			// Check if group is available
			for(Class classB: this.classes) {
				if (((classA.getGroupId()-classB.getGroupId() == 10) || (classA.getGroupId()-classB.getGroupId() == -10)) && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}
		return clashes;
	}
	
	//deletes professors who has not free time
	 
	public void delNonFreeProfs() {
		for(Map.Entry<Integer, Professor> entry: this.professors.entrySet()) {
			if(entry.getValue().getTimeslots().isEmpty()) {
				for(Map.Entry<Integer, Module> module: this.modules.entrySet()){
					if(module.getValue().getRandomProfessorId() == entry.getKey())
						this.modules.remove(module.getKey());
				}
				this.professors.remove(entry.getKey());
			}
		}
	}
}