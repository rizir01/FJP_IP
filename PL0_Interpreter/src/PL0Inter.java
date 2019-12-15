import java.util.*;
/**
 * Write a description of class PL0Inter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PL0Inter
{
    private Stack<Integer> stack;
    private Instruction [] instructions;
    private int base;
    private int head;
    private int [] calValues;
    private Scanner in = new Scanner(System.in);
    
    public boolean debbug;
    
    public PL0Inter(Instruction [] inst)
    {
        stack = new Stack<Integer>();
        base = 0;
        head = -1;
        debbug = false;
        calValues = new int[3];
        instructions = new Instruction[inst.length];
        for(int i = 0; i < inst.length; i++)
        {
        	instructions[i] = inst[i];
		}
    }
    
    public void simulatePL0()
    {
    	int index = 0;
    	int prev = 0;
    	calValues = new int[] {0,0,-1};
    	do
    	{
    		int tmp = inputInstruction(instructions[index]);
    		prev = index; 
    		if(tmp != -1)
    		{
    			index = tmp;
    		}
    		else
    		{
    			index++;
    		}
    		if(debbug)
    		{
    			System.out.println("----------------------------");
    			System.out.println("CURR INSTRUCTION: " + instructions[prev].toString());
    			System.out.println("NEXT INSTRUCTION: " + instructions[index].toString());
    			System.out.println("INST: " + prev);
    			System.out.println("BASE: " + base);
    			System.out.println("HEAD: " + head);
    			System.out.println(stack.toString());
    			System.out.println("----------------------------");
    		}
    		in.nextLine();
    	}while(index != instructions.length - 1);
    	System.out.println("------------DONE------------");
    }
    
    public int inputInstruction(Instruction ins)
    {
        switch(ins.getName())
        {
            case "JMP":return JMP(ins.getImmer(), ins.getValue());
            case "INT":INT(ins.getImmer(), ins.getValue());
                       break;
            case "LOD":LOD(ins.getImmer(), ins.getValue());
                       break;
            case "LIT":LIT(ins.getImmer(), ins.getValue());
                       break;
            case "OPR":OPR(ins.getImmer(), ins.getValue());
                       break;
            case "STO":STO(ins.getImmer(), ins.getValue());
                       break;
            case "JMC":return JMC(ins.getImmer(), ins.getValue());
            case "RET":return RET(ins.getImmer(), ins.getValue());
            case "CAL":return CAL(ins.getInsNumber(), ins.getImmer(), ins.getValue());
            default:System.err.println("PL0Inter - inputInstruction - wrong" +
            "type of instruction " + ins.getName());
                    break;
        }
        return -1;
    }
    
    public void LOD(int immer, int value)
    {
        int tmp = 0;
        if(immer > 0)
        {
            int locBase = this.base;
            for(int i = 0;i < immer;i++)
            {
                locBase = stack.get(locBase);
            }
            tmp = stack.get(locBase + value);
        }
        else
        {
            tmp = stack.get(this.base + value);
        }
        stack.push(tmp);
        this.head++;
    }
    
    public void INT(int immer, int value)
    {
        stack.push(calValues[0]);
        stack.push(calValues[1]);
        stack.push(calValues[2]);
        for(int i = 3; i < value;i++)
        {
            stack.push(0);
        }
        this.base = this.head + 1;
        this.head += value;
    }
    
    public int JMC(int immer, int value)
    {
        int tmp = stack.pop();
        this.head--;
        if(tmp == 0)
        {
        	return value;
        }
        else
        {
        	return -1;
        }
    }
    
    public int JMP(int immer, int value)
    {
    	return value;
    }
    
    public int RET(int immer, int value)
    {
    	int b = stack.get(this.base);
    	int lb = stack.get(this.base+1);
    	int in = stack.get(this.base+2);
    	int remCount = this.head - this.base;
    	for(int i = 0; i < remCount + 1; i++)
    	{
    		stack.pop();
    	}
    	this.head -= remCount + 1;
    	if(b != lb)
    	{
    		this.base = lb;
    	}
    	else
    	{
    		this.base = b;
    	}
    	return in;
    }
    
    public int CAL(int insNum, int immer, int value)
    {
    	int stepBack = this.base;
    	for(int i = 0; i < immer; i++)
    	{
    		stepBack = stack.get(stepBack);
    	}
    	calValues[0] = stepBack;
    	calValues[1] = this.base;
    	calValues[2] = insNum + 1;
    	return value;
    }
    
    public void OPR(int immer, int value)
    {
        int tmp1, tmp2;
        switch(value)
        {
            case 0://Kdysi pro operaci return
                   break;
            case 1://Negace hodnoty(prevracena hodnota)
                   tmp1 = stack.pop();
                   stack.push(Operations.NEG(tmp1));
                   break;
            case 2://Secteni dvou hodnot
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   stack.push((tmp1+tmp2)& 255);
                   this.head--;
                   break;
            case 3://Odecteni dvou hodnot
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   stack.push((tmp1-tmp2)& 255);
                   this.head--;
                   break;
            case 4://Nasobeni dvou hodnot
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   stack.push((tmp1*tmp2)& 255);
                   this.head--;
                   break;
            case 5://Deleni dvou cisel
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   stack.push((tmp1/tmp2)& 255);
                   this.head--;
                   break;
            case 6://Liche cislo(delitelne dvema)?
                   tmp1 = stack.pop();
                   if(tmp1%2 == 1)
                   {
                	   stack.push(1);
                   }
                   else
                   {
                	   stack.push(0);   
                   }
                   this.head--;
                   break;
            case 7://Modulo dvou cisel
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   stack.push((tmp1%tmp2)& 255);
                   this.head--;
                   break;
            case 8://==
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   if(tmp1 == tmp2)
                   {
                	   stack.push(1);                	   
                   }
                   else
                   {
                	   stack.push(0);
                   }
                   this.head--;
                   break;
            case 9://!=
                   tmp1 = stack.pop();
                   tmp2 = stack.pop();
                   if(tmp1 == tmp2)
                   {
                	   stack.push(0);                	   
                   }
                   else
                   {
                	   stack.push(1);
                   }
                   this.head--;
                   break;
            case 10://<
            		tmp1 = stack.pop();
            		tmp2 = stack.pop();
            		if(tmp1 < tmp2)
            		{
            			stack.push(1);                	   
            		}
            		else
            		{
            			stack.push(0);
            		}
            		this.head--;
                    break;
            case 11://<=
	            	tmp1 = stack.pop();
	        		tmp2 = stack.pop();
	        		if(tmp1 <= tmp2)
	        		{
	        			stack.push(1);                	   
	        		}
	        		else
	        		{
	        			stack.push(0);
	        		}
	        		this.head--;
                    break;
            case 12://>
	            	tmp1 = stack.pop();
	        		tmp2 = stack.pop();
	        		if(tmp1 > tmp2)
	        		{
	        			stack.push(1);                	   
	        		}
	        		else
	        		{
	        			stack.push(0);
	        		}
	        		this.head--;
	        		break;
            case 13://>=
	            	tmp1 = stack.pop();
	        		tmp2 = stack.pop();
	        		if(tmp1 >= tmp2)
	        		{
	        			stack.push(1);                	   
	        		}
	        		else
	        		{
	        			stack.push(0);
	        		}
	        		this.head--;
	        		break;
            default:System.out.println("PL0Inter - OPR - unkown instruction " + value);
                    break;
        }
    }
    
    public void LIT(int immer, int value)
    {
        stack.push(value);
        this.head++;
    }
    
    public void STO(int immer, int value)
    {
        int tmp = (int)stack.pop();
        if(immer > 0)
        {
            int locBase = this.base;
            for(int i = 0;i < immer;i++)
            {
                locBase = stack.get(locBase);
            }
            stack.set(locBase + value, tmp);
        }
        else
        {
            stack.set(this.base + value, tmp);
        }   
        this.head--;
    }
}
