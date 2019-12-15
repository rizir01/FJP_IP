
public class CargoIP
{
	private int [] num;
	private boolean isIP;
	
	public CargoIP(int [] n, boolean v)
	{
		if(n.length == 4)
		{
			num = new int[4];
			for(int i = 0; i < n.length; i++)
			{
				num[i] = n[i];
			}
			isIP = v;
		}
		else
		{
			System.err.println("CargoIP - constructor - amount of numbers in IP is inccorrect!");
		}
	}
	
	public int[] getNum()
	{
		return num;
	}
	
	public int getNum(int index)
	{
		if(index < 0 || index > 4)
		{
			return -1;
		}
		return num[index];
	}
	
	public void setNum(int[] num)
	{
		if(num.length == 4)
		{
			for(int i = 0; i < num.length; i++)
			{
				this.num[i] = num[i];
			}
		}
		else
		{
			System.err.println("CargoIP - setNum(int[]) - amount of numbers is inccorrect!");
		}
	}
	
	public String returnIP()
	{
		return num[0] + "." + num[1] + "." + num[2] + "." + num[3];
	}
	
	public boolean isIP()
	{
		return isIP;
	}
	
	public void setIP(boolean isIP)
	{
		this.isIP = isIP;
	}
}
