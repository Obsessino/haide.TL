import java.io.File;


public class TestMD5 {

	public static void main(String[] args) throws Exception {
		String password="1";
		//md5���Ǽ����㷨����ժҪ�㷨�����ɽ���
		//ע���ʱ�򷢸����������������md5,���ݿ���Ҳ��md5
		//��¼�û��ڿͻ����������룬�������md5,�������������ڷ������Ƚ�md5
		String stringMd5=MD5Util.getStringMD5(password);

		//c4ca4238a0b923820dcc509a6f75849b
		System.out.println(stringMd5);
		
		String fileMd5=MD5Util.getFileMd5(new File("e:/1702/1702.txt"));
		//5e54cb5029496843f29e6a486bb37279
		//eea355a0fa5bce67e6006cca8a5e746d
		System.out.println("fileMd5:"+fileMd5);

	}

}
