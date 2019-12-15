import java.util.Hashtable;
import java.util.Map;

public class LexTable
{
	private Hashtable<String, String> lexTable;
	
	public LexTable()
	{
		lexTable = new Hashtable<String, String>();
	}
	
	public void addKey(String key, String value)
	{
		if(key != null || value != null)
		{
			lexTable.put(key, value);
		}
		else
		{
			System.err.println("LexTable - key or value is NULL!");
		}
	}
	
	public String findValue(String key)
	{
		if(key != null)
		{
			String f;
			f = lexTable.getOrDefault(key, "Not Found"); 
			return f;
		}
		else
		{
			System.err.println("LexTable - key is NULL!");
			return null;
		}
	}
	
	@Override
	public String toString()
	{
		String pr = "----------------------------\nHASH TABLE CONTENT:\n";
		for(Map.Entry<String, String> entry : lexTable.entrySet())
		{
			pr += entry.getKey() + " " + entry.getValue() + "\n";
		}
		pr += "----------------------------";
		return pr;
	}
	
}
