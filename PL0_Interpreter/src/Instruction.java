
/**
 * Write a description of class Instruction here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Instruction
{
    private String nameInst;
    private int insNum;
    private int immer;
    private int value;
    
    public Instruction()
    {
        this("Default",0,0,0);
    }
    
    public Instruction(String name, int num, int imm, int val)
    {
        if(name != null)
        {
            this.nameInst = name;
            this.immer = imm;
            this.value = val;
            this.insNum = num;
        }
    }
    
    public String getName()
    {
        return nameInst;
    }    
    
    public void setName(String name)
    {
        if(name != null)
        {
            this.nameInst = name;
        }
    }
    
    public int getInsNumber()
    {
        return insNum;
    }
    
    public void setInsNumber(int num)
    {
        this.insNum = num;
    }
    
    public int getImmer()
    {
        return immer;
    }
    
    public void setImmer(int imm)
    {
        this.immer = imm;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setValue(int val)
    {
        this.value = val;
    }
    
    @Override
    public String toString()
    {
    	return insNum + " " + nameInst + " " + immer + " " + value;
    }
}
