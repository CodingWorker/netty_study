package com.test.netty_study.filestat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileStatusStat {
	private String filePath;
	private int count1=0;//��������
	private int count2=0;//��ע������
	private int count3=0;//�м�ע�ͣ��������ڿ鼶ע���ڵ�
	private int count4=0;//��������

	public FileStatusStat(String filePath) {
		this.filePath=filePath;
	}
	
	public void stat() throws IOException,FileNotFoundException{
		File file=new File(this.filePath);
		if(!file.exists()) throw new FileNotFoundException("resource: "+filePath+" not exist");
		BufferedReader bfReader=null;
		try {
		//stat
		bfReader=new BufferedReader(new FileReader(file));
		String line=null;
		while((line=bfReader.readLine())!=null) {
			line=line.trim();
			if(line.length()==0) {
				count4++;
			}else if(line.indexOf("//")!=-1) {
		    	count3++;
		    }else if(line.indexOf("/*")!=-1 
		    		&& line.indexOf("*/")!=-1 
		    		&& line.indexOf("/*")<line.indexOf("*/")){
		    	count2++;
		    }else if(line.indexOf("/*")!=-1 && line.indexOf("*/")==-1) {
		    	count2++;
		    	for(;(line=bfReader.readLine())!=null && line.indexOf("*/")==-1; count2++);
		    	count2++;
		    }else {
		    	count1++;
		    }
		}
		}finally {
			if(bfReader!=null) {
				try {
					bfReader.close();
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public int getCount1() {
		return this.count1;
	}
	
	public int getCount2() {
		return this.count2;
	}
	
	public int getCount3() {
		return this.count3;
	}
	
	public int getCount4() {
		return this.count4;
	}
	
	@Override
	public String toString() {
		return "file: "+this.filePath+", ���д��룺"+this.count1
				+" ��, �м�ע��: "+this.count3+" �У��鼶ע�ͣ�"+this.count2
				+" �У����У�"+this.count4;
	}
}


class Test{
	public static void main(String[] args) throws Exception{
		String filePath="F:\\Html\\mannual_files_all\\netty_study\\netty-mvn-app\\src\\main\\java\\com\\test\\netty_study\\timedemo\\TimeClient.java";
		FileStatusStat fileStatusStat=new FileStatusStat(filePath);
		fileStatusStat.stat();
		System.out.println(fileStatusStat);
	}
}






