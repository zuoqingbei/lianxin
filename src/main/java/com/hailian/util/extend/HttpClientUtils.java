/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.hailian.util.extend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * TODO 这个有待完善
 * 
 * 2015年5月30日 下午11:16:22
 * flyfox 330627517@qq.com
 */
public class HttpClientUtils {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			System.out.println(conn.getContent());
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
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
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
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
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

    public static void main(String args[]) {
        //HttpClientUtils.sendPost("http://localhost:8400/api/addOrder","companyID=LX&randomCode=1111&timestamp=0000000000&data=pGzNOXrAd5XLDp8RjAEHt3DQXb%2BUs4SR4fVFnRCbaDZzN6AqmxFoQFlC6htjSRNcwt13dIYx786nHQ8eArL%2FB9QzGvekFyGb%2B6DESqF36jeBetormSk7xwvm0kRQECtFZA1n4DkeEY1eX8R43bhzGeWZTWWHgEbAWHFlVBDPS9wwj2sCLADSfh4wkNw1aP%2B4UD%2FkRQ67B1UZtzMKP3VDoahcThFhb6imAOCjx%2B%2FVyibKFOtF5vpt6%2BF3POh%2BH711VEM6%2F2hFVYUo%2FuGiZDNp%2BWf4pGq96mGo6HoBYk0ULieCBouR1vxBbirpbZ4D12AUSJAt7JKGHO0GLtdFPkJM1w%3D%3D");
        String key = "companyID=LX&randomCode=1111&timestamp=0000000000";
        //客户
        //String data = "e1q0TQBlonJivoZP34fSywtFLdPvyx972XJl%2Ff3gaAd7PPMYlsFg%2BqmnXJtFLf7garGUx0UVS7aIqyagIufbO0x1AedX1%2Bkt9ADhGJb9nEvsqkFUAYBsqodAFAnrdL6Rm3BpcbwG%2Basouol2qOgv15OVbQpbxdkUv4pcly%2B7s%2FeK%2Fent001MkD9UaQOkXsFdX10OA%2FzBf4piwAqJac4VZEm4y4K9S9B52uaocNlCPUcz4u2QmIEDot2srFejNY%2FOGYLwtEkYJnupNvRwg16Hx5xPBRM%2FYLLqtumkXuzLhgPqMBwVLBy9VOp%2B4UPJwW9epLxJ9W2tLc5N233n1%2BuMQbl9cH80F7yuMkmfa3XOJVCpbrwO6AvCfVbGEUt7187j8T%2FHNSp9OCGx%2FSn7ObiJ5w%3D%3D";
        //String result = HttpClientUtils.sendPost("http://192.168.1.152:8080/api/addCustomer", key + "&" + "data=" + data);

        //充值
        //String data = "%2Bg7zIa2OqjCjMkqRg2vKQxLQvvuITXUkdRq7r%2BixOw079C21pRS2UPl9HuE9%2FJRdhe%2F6WhT9vfljd8zhoGCv4KZ7XvHYBGPoATOyzjvqhpmT8WnQfh6O1%2Bj%2B6DoAjbHb";
        //String result = HttpClientUtils.sendPost("http://192.168.1.152:8080/api/paySave", key + "&" + "data=" + data);

        //扣款
        String data = "%2Bg7zIa2OqjCjMkqRg2vKQxLQvvuITXUkdRq7r%2BixOw079C21pRS2UPl9HuE9%2FJRdjKHmh4B%2BoXfpN9%2Bu6HoVlHOnY2ccgSPqre8x9q8w01Q1mrWIWgOkV5pkJTL9g29i";
        String result = HttpClientUtils.sendPost("http://192.168.1.152:8080/api/chargeSave", key + "&" + "data=" + data);


        System.out.println(result);
    }
}
