package gitsimulator;

import java.io.*;
import java.security.MessageDigest;

public class KeyValueStorage {

	protected String path = "D:\\Java\\homework1105";  //�ļ��洢·��
	protected String savepath = "D:\\Java\\managebase\\files";	
	

	
	//����hashֵ���㺯����ʵ���ļ����ļ��е�hashֵ����
	public String FileSHA1Checksum(InputStream is) throws Exception {
			byte[] buffer = new byte[1024];// ���ڼ���hashֵ���ļ�������
			MessageDigest complete = MessageDigest.getInstance("SHA-1");// ʹ��SHA1��ϣ/ժҪ�㷨
	        int numRead = 0;
	        do {
	            numRead = is.read(buffer);// ����numRead�ֽڵ�buffer��
	            if (numRead > 0) {
	                complete.update(buffer, 0, numRead);// ����buffer[0:numRead]�����ݸ���hashֵ
	            }
	        } while (numRead != -1);// �ļ��Ѷ��꣬�˳�ѭ��
	        is.close();// �ر�������
	        return getSha1(complete.digest());// ����SHA1��ϣֵ
	    }
	
	
    public String StringSHA1Checksum(String value) throws Exception{
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(value.getBytes());
        return getSha1(complete.digest());
    }
	
	
	public String getSha1(byte[] temp) {
		String sha1 = "";
		for(int i=0;i<temp.length;i++) {
			sha1 += Integer.toString(temp[i] & 0xFF, 16);
		}
		return sha1;
	}
	
	
    public String GetKey(InputStream file) throws Exception{
    	return FileSHA1Checksum(file);
    }
    
    
    public String GetKey(String value) throws Exception{
    	return StringSHA1Checksum(value);
    }
    
    
	public String GetValue(String key) throws Exception{
		File file = new File(savepath + File.separator + key + ".txt");
		if(!file.exists()) 
			return null;
		else {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(file)));
	        StringBuffer value = new StringBuffer();
			String line; 
	        while((line = in.readLine())!=null) {
	        	value.append(line);
	        }
	        in.close();
	        return value.toString();    	        
	     }
	}
    

	//pathӦ����Ҫ�洢���ļ���λ��
	public void WriteToFile(String path) throws Exception{
		String key = FileSHA1Checksum(new FileInputStream(path));
		File file = new File(savepath +File.separator+ key + ".txt");
		file.createNewFile();
        FileInputStream input = new FileInputStream(path);
        FileOutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        do {
            numRead = input.read(buffer);
            if(numRead > 0){
                output.write(buffer,0,numRead);
            }
        }while(numRead!=-1);
        input.close();
        output.close();
    };
}