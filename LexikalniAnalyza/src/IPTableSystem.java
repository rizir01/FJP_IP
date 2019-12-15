import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IPTableSystem
{
    private static IPTableSystem instance = null;
    
    /**Hash tabulka se slovnikem lexikalnich symbolu pro kazdou IP adresu*/
    public LexTable table;
    
    /**Pocet IP adres, ktere obsahuji 'x'*/
    private int xxxNum;
    
    /**Pokud se behem Lex. analyzy nacita String*/
    private boolean loadingString;
    
    /**Konkretni hodnota ulozena v IP adrese*/
    private String lexValue; 
  
    private IPTableSystem() 
    { 
        table = new LexTable();
        xxxNum = 0;
        lexValue = "";
        loadingString = false;
    } 
  
    public static IPTableSystem getInstance() 
    { 
        if(instance == null)
        {
        	instance = new IPTableSystem();         	
        }
        return instance; 
    }
    
    public void loadLexTable()
    {
    	File f = new File("table.txt");
    	if(f.exists() != false)
    	{
    		try
    		{
				BufferedReader br = new BufferedReader(new FileReader(f));
				String tmp;
				while((tmp = br.readLine()) != null)
				{
					ArrayList<String> res = parseStringLine(tmp);
					if(res.size() != 0)
					{
						//System.out.println(res.get(0) + " " + res.get(1));
						if(res.get(0).indexOf(120) != -1)//120 = 'x'
						{
							xxxNum++;
						}
						table.addKey(res.get(0), res.get(1));
					}
				}
				br.close();
			}
    		catch(FileNotFoundException e)
    		{
				e.printStackTrace();
			}
    		catch(IOException e)
    		{
				e.printStackTrace();
			}
    	}
    	else
    	{
    		System.err.println("IPTableSystem - 'table.txt' was not found!");
    	}
    }
    
    public int lexicalAnalysis(String pathToFile)
    {
    	File code = new File(pathToFile);
    	if(code.exists() != false)
    	{
    		try
    		{
				BufferedReader br = new BufferedReader(new FileReader(code));
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("code.lex")));
				String tmp;
				while((tmp = br.readLine()) != null)
				{
					ArrayList<String> res = parseStringLine(tmp);
					for(String v : res)
					{						
						CargoIP cargo = isItIP(v);
						if(!cargo.isIP())
						{
							System.err.println("IPTableSystem - lexicalAnalysis() - " + 
							v + " is not an IP address!");
							br.close();
							bw.close();
							return 1;
						}
						
						//Dosazeni konkretni IP do hash tabulky pro nalezeni prislusne
						//fraze
						String [] res2 = identifyIPFromHashTable(cargo, " ");
						if(!loadingString)
						{
							bw.write(res2[0] + res2[1]);
						}
					}
				}
				br.close();
				bw.close();
			}
    		catch(FileNotFoundException e)
    		{
				e.printStackTrace();
			}
    		catch(IOException e)
    		{
				e.printStackTrace();
			}
    	}
    	else
    	{
    		System.out.println("File was not found!");
    	}
		return 0;
    }
    
    public String [] identifyIPFromHashTable(CargoIP cargo, String space)
    {
    	String [] vys = new String[2];
    	vys[1] = space;
    	int [] IPnums = cargo.getNum();
    	int first = cargo.getNum(0);//!!POZOR - zde muze prijit hodnota -1
    	if(loadingString)
    	{
    		if(first == 3)
    		{
    			lexValue += LibIPTransfer.getCharsFromIP(IPnums);
    		}
    		else if(LibIPTransfer.compare2IPs("127.2.0.7", IPnums))
    		{
    			vys[0] = "sz_quo";
    			vys[1] = " " + lexValue + " sz_quo ";
    			lexValue = "";
    			loadingString = false;
    		}
    	}
    	else
    	{
    		if(first <= xxxNum)
    		{
    			vys[0] = table.findValue(first + ".x.x.x");
    			lexValue = LibIPTransfer.getValueFromIP(cargo.getNum());
    			vys[1] = ":" + lexValue + " ";
    			lexValue = "";
    		}
    		else
    		{
    			vys[0] = table.findValue(cargo.returnIP());
    			if(vys[0].equals("sz_com"))
    			{
    				vys[1] = "\n";		
    			}
    			else if(vys[0].equals("sz_quo"))
    			{
    				//Nacitani stringu
    				loadingString = true;
    			}
    		} 		
    	}
    	return vys;
    }
    
    public void printTableData()
    {
    	System.out.println(table.toString());
    }
    
    /**
     * Kontrola retezce, jestli se jedna o validni
     * IP adresu.
     * @param		ip		predany retezec potencialni IP
     * 						adresy
     * @return				je ci neni IP adresa + ulozeni IP
     * 						adresy po cislech
     */
    public CargoIP isItIP(String ip)
    {
    	String [] vys = separeter(ip, '.');
    	int [] num = new int[4];
    	int ind = 0;
    	if(vys.length != 4)
    	{
    		return new CargoIP(new int[4], false);
    	}
    	for(String n: vys)
    	{
    		try
    		{
    			int c = Integer.parseInt(n);
    			if(c < 0 || c > 255)
    			{
    				return new CargoIP(new int[4], false);
    			}
    			num[ind++] = c;
    		}
    		catch(NumberFormatException nfe)
    		{
    			return new CargoIP(new int[4], false);
    		}
    	}
    	return new CargoIP(num, true);
    }
    
    public ArrayList<String> parseStringLine(String line)
    {
    	ArrayList<String> result = new ArrayList<String>();
    	int start = 0;
    	int end = 0;
    	for(int i = 0; i < line.length(); i++)
    	{
			if(line.charAt(i) == '#')
			{
				if(end - start >= 3)
		    	{
		    		result.add(line.substring(start, end));
		    	}
				return result;
			}
			else if(line.charAt(i) == ' ')
			{
				end++;
				result.add(line.substring(start, end - 1));
				start = end;
			}
			else if(line.charAt(i) == ';')
			{
				result.add(line.substring(start, end));
				return result;
			}
			else
			{
				end++;
			}
		}
    	if(start != end)
    	{
    		result.add(line.substring(start, end));
    	}
    	return result;
    }
    
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

}
