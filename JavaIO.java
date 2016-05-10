import java.io.*;

public class JavaIO {
	public static void main(String[] args) {
		BufferedInputStream bs = null;

		try
		{
			bs = new BufferedInputStream(new FileInputStream("hello"));
			byte [] b = new byte [bs.available()]; //�ӽ÷� �дµ� ���� ����
			System.out.println(b.length);
			while( bs.read(b) != -1) {}
			
			System.out.println(new String(b)); //�ʿ信 ���� ��Ʈ����ü�� ��ȯ
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				bs.close(); //�ݵ�� �ݴ´�.
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
