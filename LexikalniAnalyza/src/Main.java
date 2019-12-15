
public class Main
{  	
	public static void main(String[] args)
	{
		IPTableSystem sys = IPTableSystem.getInstance();
		
		//Nacteni hash tabulky lexikalniho prepisu IPC
		sys.loadLexTable();
		//sys.printTableData();
		
		//Lexikalni analyza kodu
		Long time1 = System.currentTimeMillis();
		if(sys.lexicalAnalysis("test.ipc") == 0)
		{
			Long time2 = System.currentTimeMillis();
			System.out.println("Lex time in ms: " + (time2 - time1));	
		}
	}
	/**
	 *IPTableSystem sys = IPTableSystem.getInstance();
		
		//Nacteni hash tabulky lexikalniho prepisu IPC
		sys.loadLexTable();
		//sys.printTableData();
		
		//Lexikalni analyza kodu
		Long time1 = System.currentTimeMillis();
		if(sys.lexicalAnalysis("test.ipc") == 0)
		{
			Long time2 = System.currentTimeMillis();
			System.out.println("Lex time in ms: " + (time2 - time1));	
		}
	 */
	
	/**
	 * int value = 127;
		String k = Integer.toBinaryString(value);
		System.out.println(k);
		byte [] IParr = new byte[8];
		int j = IParr.length - 1;
		for(int i = k.length() - 1; i >= 0; i--, j--)
		{
			if(k.charAt(i) == '1')
			{
				IParr[j] = 1;
			}
			else
			{
				IParr[j] = 0;				
			}
		}
		System.out.println(Arrays.toString(IParr));
	 */
}
