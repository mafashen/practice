package com.mafashen.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

public class ExcelTest {
	protected static String url = "http://gw.api.taobao.com/router/rest";
    protected static String appkey = "24743611";
    protected static String appSecret = "22ecaece778b768a56ec32d0a54cec48";
    protected static String sessionKey = "6100c23454ec00df42870f387c60163184cbda39d1ed2793587405934";
	 public static void main(String[] args) throws Exception {
	        //发送 POST 请求
	        String sr=sendPost("https://restapi.amap.com/v3/geocode/geo", "address=滨江区茂源大厦&key=dfe6d1f5f0885861b4c75ccecfa627e6&output=json");
	        System.out.println(sr);
	    }

	    public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }

	        return result;
	    }

	public static int insertExcel(InputStream inputStream){
		int result=0;
		XSSFWorkbook rwb = null;
		//创建输入流
		InputStream is;
		try {
			rwb =new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//得到工作簿
		XSSFSheet sheet = rwb.getSheetAt(0);
		System.out.println("sheet.getLastRowNum()=="+sheet.getLastRowNum());
		int rsRows = sheet.getLastRowNum()+1;//获取总行数
		String simNumber = ""; //每个单元格中的数据
		String str="";//拼接要插入的列
		XSSFRow row = sheet.getRow(0); //获取第一行
		int rsColumns = row.getPhysicalNumberOfCells();//列数
		for (short j = 0; j <rsColumns; j++) {
			XSSFCell cell = row.getCell(j);
			simNumber = cell.getRichStringCellValue().toString();
			if(j==rsColumns-1){//最后一列不用加逗号
				str += simNumber;
			}else{
				str += simNumber+",";
			}
		}
		SimpleDateFormat sf= new SimpleDateFormat("YYYYMMddHHmmssSSSS");
		Long l=Long.valueOf(sf.format(new Date(System.currentTimeMillis())));
		for (short i = 2; i < rsRows; i++) { //从第三行开始
			XSSFRow row1 = sheet.getRow(i); //获取行
			l+=i;
			for (short j = 0; j < rsColumns; j++) {
				XSSFCell cell = row1.getCell(j);
				SimpleDateFormat sdf = null;
				if(cell!=null){
					short format = cell.getCellStyle().getDataFormat();
					if(cell.getCellType()== 1){//字符类型
						if(row.getCell(j).getRichStringCellValue().toString().equals("ADDRESS")){//当是地址时，调用高德sdk返回标准的省市区
							String url="https://restapi.amap.com/v3/geocode/geo";// 腾讯 https://apis.map.qq.com/ws/geocoder/v1/
							String param="address="+cell.toString()+"&key=dfe6d1f5f0885861b4c75ccecfa627e6&output=json";
							System.out.println("最后的url："+url+"?"+param);
							String returnBody=sendPost(url, param);
							System.out.println("返回的"+returnBody.toString());
							//http://api.map.baidu.com/place/v2/search?query=附近&region=滨江区茂源大厦&output=json&ak=OyggxDhPALRV5cnxE1Q55rgx6TtGRESg
						}
					}
				}
			}
		}

		return result;
	}

	@Test
	public void test(){
//		insertExcel(new File("/Users/mafashen/Downloads/test.xlsx"));
	}

}
