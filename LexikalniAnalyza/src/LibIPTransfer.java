
public class LibIPTransfer
{
	public static boolean compare2IPs(String IP, int [] ipNum)
	{
		if(ipNum.length != 4)
		{
			System.out.println("Array with IP numbers is incorecct size! - " + ipNum.length);
			return false;
		}
		String tmp = ipNum[0] + "." + ipNum[1] + "." + ipNum[2] + "." + ipNum[3];
		if(IP.equals(tmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static String getValueFromIP(int [] IPnums)
	{
		String v = "";
		switch(IPnums[0])
		{
		case 0:v = "" + IPv4toInt(new int[] {IPnums[1],IPnums[2],IPnums[3]});
			   break;
		case 1:v = "" + IPv4toChar(IPnums);
			   break;
		case 2:v= "" + IPv4toBool(IPnums);
			   break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:v = "" + IPv4toInt(new int[] {IPnums[1],IPnums[2],IPnums[3]});
			break;
		}
		return v;
	}
	
	public static String getCharsFromIP(int [] IPnums)
	{
		String v = "";
		for(int i = 1; i < IPnums.length; i++)
		{
			if(IPnums[i] == 0)
			{
				continue;
			}
			else
			{
				v += (char)IPnums[i];
			}
		}
		return v;
	}
	
	/**
	 * !!POZOR!! uplnou IP adresu to nedokaze prelozit, jelikoz
	 * funkce Integer.parseInt je jen pro kladne hodnoty!
	 * @param IPnum
	 * @return
	 */
	public static int IPv4toInt(int [] IPnum)
	{
		String binaryString = "";
		for(int i = 0; i < IPnum.length; i++)
		{
			String tmp = Integer.toBinaryString(IPnum[i]);
			switch(tmp.length())
			{
			case 1:tmp = "0000000" + tmp;
				break;
			case 2:tmp = "000000" + tmp;
				break;
			case 3:tmp = "00000" + tmp;
				break;
			case 4:tmp = "0000" + tmp;
				break;
			case 5:tmp = "000" + tmp;
				break;
			case 6:tmp = "00" + tmp;
				break;
			case 7:tmp = "0" + tmp;
				break;
			}
			binaryString = binaryString + tmp;
		}
		return Integer.parseInt(binaryString,2); 
	}
	
	public static char IPv4toChar(int [] IPnum)
	{
		return (char)IPnum[3];
	}
	
	/**
	 * Vsechny hodnoty vetsi nez nula jsou TRUE!!
	 * 
	 * @param IPnum
	 * @return
	 */
	public static boolean IPv4toBool(int [] IPnum)
	{
		if(IPnum[3] == 0)
		{
			return false;	
		}
		else
		{
			return true;
		}
	}
}
