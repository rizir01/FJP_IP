import java.io.*; 

/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
	
	/**
	 * Funkce, ktera rozdeli String na tolik casti, kolik je tam
	 * znaku, ktere se zadavaji jako parametr a preda vysledne rozdeleni
	 * jako pole <String>
	 * 
	 * @param		str		vstupni retezec
	 * @param 		znak	oddelovaci znak
	 * @return				pole retezcu
	 */
	public static String [] separeter(String str, char znak)
	{
		int l = 0;
		boolean nenasel = true;//Nenasel znak v celem retezci
		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == znak)
			{
				nenasel = false;
				l++;
			}
		}
		if(nenasel)
		{
			l = 1;
		}
		boolean neniPosledniZnak = false;//Posledni znak retezce neni hledany znak
		if(str.charAt(str.length() - 1) != znak)
		{
			if(!nenasel)
			{
				neniPosledniZnak = true;				
				l++;
			}
		}
		String [] result = new String[l];
		int indexPole = 0;
		String tmp = "";
		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == znak)
			{
				result[indexPole++] = tmp;
				tmp = "";
			}
			else
			{
				tmp += str.charAt(i);
			}
		}
		if(neniPosledniZnak || nenasel)
		{
			result[indexPole] = tmp;
		}
		return result;
	}
	
	public static Instruction [] loadInstructions()
    {
    	Instruction [] ins = new Instruction[1];
    	try
    	{
			BufferedReader bf = new BufferedReader(new FileReader("pl_code.txt"));
			String line = "";
			int sum = 0;
			while((line = bf.readLine()) != null)
			{
				sum++;
			}
			ins = new Instruction[sum];
			sum = 0;
			bf.close();
			bf = new BufferedReader(new FileReader("pl_code.txt"));
			while((line = bf.readLine()) != null)
			{
				String [] tmpS = separeter(line.trim(), '	');
				ins[sum++] = new Instruction(tmpS[1], Integer.parseInt(tmpS[0]), Integer.parseInt(tmpS[2]), Integer.parseInt(tmpS[3]));
			}
			bf.close();
		}
    	catch(FileNotFoundException e)
    	{
			e.printStackTrace();
		}
    	catch(IOException e)
    	{
			e.printStackTrace();
		}
    	return ins;
    }
	
	public static void main(String [] args)
    {
        Instruction [] ins = loadInstructions();
    	PL0Inter interpreter = new PL0Inter(ins);
    	interpreter.debbug = true;
    	interpreter.simulatePL0();
    }    
}
