import java.io.*;

public class JavaIO {
	public static void main(String[] args) {
		BufferedInputStream bs = null;

		try
		{
			bs = new BufferedInputStream(new FileInputStream("hello"));
			byte [] b = new byte [bs.available()]; //임시로 읽는데 쓰는 공간
			System.out.println(b.length);
			while( bs.read(b) != -1) {}
			
			System.out.println(new String(b)); //필요에 따라 스트링객체로 변환
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				bs.close(); //반드시 닫는다.
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
