package com.marondal.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.memo.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {//api를 위한 컨트롤러

	@Autowired
	private PostBO postBO;
	
	@PostMapping("/create") //메모입력 당연히 포스트 패키지내
	public Map<String, String> postCreate(//userId 어떻게 처리할건가?
			
			@RequestParam("title") String title
			, @RequestParam("content") String content
			, @RequestParam("file") MultipartFile file//실제파일과 파일 관련정보저장
			, HttpSession session) {// session을 통해서 값을 얻어오기
		
		int userId = (Integer)session.getAttribute("userId");//값구해오기
						//object 라서 빨간줄이뜬다. //session ctrl+클릭해보기 //오브젝트클래스는 모든클래스의 부모클래스 
						//원래 형태인 Integer 형태로 캐스팅해서 저장
						
		int count = postBO.addPost(userId, title, content, file);
		
		Map<String, String> resultMap = new HashMap<>();//제이슨 형태인 맵
		
		if(count == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;	
	
	}
}
