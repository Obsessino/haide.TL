import java.io.File;


public class TestMD5 {

	public static void main(String[] args) throws Exception {
		String password="1";
		//md5不是加密算法，是摘要算法。不可解密
		//注册的时候发给服务器的是密码的md5,数据库存的也是md5
		//登录用户在客户端输入密码，算密码的md5,发给服务器，在服务器比较md5
		String stringMd5=MD5Util.getStringMD5(password);

		//c4ca4238a0b923820dcc509a6f75849b
		System.out.println(stringMd5);
		
		String fileMd5=MD5Util.getFileMd5(new File("e:/1702/1702.txt"));
		//5e54cb5029496843f29e6a486bb37279
		//eea355a0fa5bce67e6006cca8a5e746d
		System.out.println("fileMd5:"+fileMd5);

	}

}
