package geneticAlgorithm;

/**
 * Simple course module abstraction, which defines the Professors teaching the module.
 */
public class Module {
    private final int moduleId;
    private final String moduleCode;
    private final String module;
    //private final int professorIds[];
    private final int professorId;

    /**
     * Initialize new Module
     * 
     * @param moduleId
     * @param moduleCode
     * @param module
     * @param professorIds
     */
    public Module(int moduleId, String moduleCode, String module, int professorId){
        this.moduleId = moduleId;
        this.moduleCode = moduleCode;
        this.module = module;
        //this.professorIds = professorIds;
        this.professorId = professorId;
    }
    
    /**
     * Get moduleId
     * 
     * @return moduleId
     */
    public int getModuleId(){
        return this.moduleId;
    }
    
    /**
     * Get module code
     * 
     * @return moduleCode
     */
    public String getModuleCode(){
        return this.moduleCode;
    }
    
    /**
     * Get module name
     * 
     * @return moduleName
     */
    public String getModuleName(){
        return this.module;
    }
    
    /**
     * Get random professor Id
     * 
     * @return professorId
     */
  /*  public int getRandomProfessorId(){
        int professorId = professorIds[(int) (professorIds.length * Math.random())];
        return professorId;
    }*/
    //tek prof veriyor artik bi dersi
    public int getRandomProfessorId(){
        return professorId;
    }
}
