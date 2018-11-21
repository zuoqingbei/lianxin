package com.hailian.modules.credit.usercenter.controller.finance;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TableauServlet
 */
//@WebServlet("/TableauServlet")
public class TableauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String   WGSERVER = "172.16.2.93:80";//TableauSeaver 地址
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TableauServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String workbook = request.getParameter("workbook");
		String view = request.getParameter("view");
		getView(username, workbook, view, response);
		//getTableauTrustedUrl(request, username, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String workbook = request.getParameter("workbook");
		String view = request.getParameter("view");
		getView(username, workbook, view, response);
		//getTableauTrustedUrl(request, username, response);
		doGet(request, response);
	}

	public String getTicket( String user) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		
			// Read the response
			StringBuffer rsp;
			try {
				// Encode the parameters
				StringBuffer data = new StringBuffer();
				data.append(URLEncoder.encode("username", "UTF-8"));
				data.append("=");
				data.append(URLEncoder.encode(user, "UTF-8"));
				//data.append("&");
				//data.append(URLEncoder.encode("client_ip", "UTF-8"));
				//data.append("=");
				//data.append(URLEncoder.encode(remoteAddr, "UTF-8"));
				// Send the request
				URL url = new URL("http://" + WGSERVER + "/trusted");
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				out = new OutputStreamWriter(conn.getOutputStream()); 
				out.write(data.toString());
				out.flush();
 
				rsp = new StringBuffer();
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					rsp.append(line);
				}
				return rsp.toString();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
				try {
					if (in != null)
						in.close();
					if (out != null)
						out.close();
				} catch (IOException e) {
				}
			}
			return null;
	}
	/**
	 * 根据参数跳转到具体视图页
	 * @param user
	 * @param workbook
	 * @param view
	 * @param response
	 */
	public void getView(String user,String workbook,String view,HttpServletResponse response) {
		String ticket = getTicket(user);
		System.out.println("ticket:=================================:"+ticket);
		String dst = "views/" + workbook + "/" + view;
		String pEmParam = ":embed=" + true + "&:toolbar=" + "yes"
				+ "&:tabs=" + "yes";
		String url = "http://" + WGSERVER + "/trusted/" + ticket + "/"
				+ dst + "?" + pEmParam;
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", url);
	}
	

	public void getTableauTrustedUrl(HttpServletRequest request,String username,HttpServletResponse response){
        //tableau服务器地址，例 http://tableau.ceshi.com
        String ticket =  getTicket(request.getParameter("user"));
        if ( !ticket.equals("-1") ) {
            String url = request.getRequestURL().toString();
            url = url.substring(0,url.indexOf(request.getRequestURI()))+"/trusted/" + ticket + "/";
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    		response.setHeader("Location", url);
        }
	}
}


