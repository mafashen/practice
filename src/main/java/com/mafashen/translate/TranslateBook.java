package com.mafashen.translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author mafashen
 * created on 2019/5/20.
 */
public class TranslateBook {

	YouDaoTranslate translate = new YouDaoTranslate();

	public void translate(String dir, String fileName, String format){
		File file = new File(dir + File.separator + fileName + "." + format);
		if (!file.exists()){
			throw new RuntimeException("file not exist " + fileName);
		}

		try{
			PDDocument document = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			File newFile = new File(dir, fileName+"2");
			if (!newFile.exists()){
				newFile.createNewFile();
			}

			File translateFile = new File(dir, fileName+"_translate");
			if (!newFile.exists()){
				translateFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(newFile);
			stripper.writeText(document, fileWriter);

			FileWriter translateWriter = new FileWriter(translateFile);

			FileReader fileReader = new FileReader(newFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null){
				System.out.println(line);
				if (!line.contains(".") || line.contains("i.e.")){
					stringBuilder.append(" ").append(line);
				}else{
					String[] split = line.split("\\.");
					if (split.length > 0){
						stringBuilder.append(split[0]);
						String translateStr = translate.translate(stringBuilder.toString());
						if (StringUtils.isNotBlank(translateStr)){
							System.out.println("-------translateStr-----" + translateStr);
							translateWriter.append(translateStr + "\n");
						}
						stringBuilder.delete(0, stringBuilder.length());
						if (split.length > 1)
							stringBuilder.append(split[1]);
					}
				}
			}
		}catch (IOException e){

		}
	}

	public static void main(String[] args) {
		new TranslateBook().translate("/Users/mafashen/Downloads", "intro","pdf");
	}
}
