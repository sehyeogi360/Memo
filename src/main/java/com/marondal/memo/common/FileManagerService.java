package com.marondal.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {
	
	
	//멤버변수 형태로 구성
	//
	private static final String FILE_UPLOAD_PATH = "D:\\임세혁\\spring\\springProject\\upload\\Memo\\image"; //이렇게 되면 상수가 된다.
		//member변수 웬만하면 public안쓴다.
	// 파일 저장 -> 경로 생성
	// 객체 생성없이 쓸수 있는 메소드 -> static 객체생성없이 쓸수 있도록 구성
	public static String saveFile(int userId, MultipartFile file) {//접근하기 위한 경로 리턴
		
		// 파일명이 같으면 안되니 그거 생각 
		// 사용자 별로 폴더 구분 
		// 시간을 포함해서 폴더를 구분
		// UNIX TIME : 1970년 1월 1일 부터 흐른 시간을 (milli second 1/1000)
		// 폴더 이름 : userId_time(3_3949828284 15:00:11)		
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis() + "/";//로그인 한정보 필요 파라미터 추가
		
		// 디렉토리 생성
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);//java.io.file 
		if(!directory.mkdir()){//makedirectory
			//디렉토리 생성 실패 
			
			return null;
		}
		
		// 파일 저장
		try {
			byte[] bytes = file.getBytes();
			
			String filePath = directoryPath + file.getOriginalFilename();//파일명 포함한 전체경로
			Path path = Paths.get(filePath);
			
			Files.write(path, bytes); //이타입이 저장된다.
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		// 클라이언트에서 저장된 파일을 접근할 수 있는 경로를 리턴
		// 경로 규칙 /images/2_39i980139/test.png
		// http://localhost:8080//images/2_39i980139/test.png
		
		return "/images" + directoryName + file.getOriginalFilename();//
		
	}
	
	
	
	
	
}