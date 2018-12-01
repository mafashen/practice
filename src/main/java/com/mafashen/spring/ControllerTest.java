package com.mafashen.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.mafashen.java.ExcelTest;

@Controller
public class ControllerTest {

	@RequestMapping("/json")
	public String json(){
		return "json";
	}

	@RequestMapping("/look")
	public String look(String param){
		return param;
	}

	@RequestMapping("/batch/look")
	public String batchLook(List<String> params){
		return params.toString();
	}

	@RequestMapping("/upload")
	public int upload(MultipartFile file) throws IOException {
		return ExcelTest.insertExcel(file.getInputStream());
	}
}
