package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.CategoryModel;
import Model.QuestionModel;
import controller.ServiceHelper;

/**
 * Servlet implementation class CateogoryController
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceHelper.getCategories();
		request.setAttribute("alreadyAvailableCategories", CategoryModel.getCategoryList());
		
		try {
			RequestDispatcher reqDis = request.getRequestDispatcher("AddCategoryPage.jsp");
			reqDis.forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL backendURL = new URL("http://51.137.215.185:9000/api/categories");
		HttpURLConnection connection = (HttpURLConnection) backendURL.openConnection();
		if(request.getParameter("submitButton") != null)
		{
			CategoryModel category = new CategoryModel();
			List<CategoryModel> list = new ArrayList<CategoryModel>();
			list = CategoryModel.getCategoryList();
			StringBuilder sb = new StringBuilder();
			boolean filled = false;		
			if (request.getParameter("categoryTitle") != null && 
				request.getParameter("descriptionCategoryTextfield") != null) 
			{
				category.setTitle(request.getParameter("categoryTitle"));
				category.setDescription(request.getParameter("descriptionCategoryTextfield"));
				try {
					category.setHash(ServiceHelper.hashCategory(category));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				filled = true;
			}
			if(filled == true)
			{				
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json; utf-8");
				connection.setDoOutput(true);
				try(OutputStream os = connection.getOutputStream()) 
				{
					byte[] input = ServiceHelper.categoryToJson(category).getBytes("utf-8");
					os.write(input,0,input.length);
				}
				catch(Exception ex)
				{
					connection.disconnect();
					System.out.println("Send request error: \n" + ex.getMessage());
				}
				StringBuilder responseString = new StringBuilder();
				try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {				    
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        responseString.append(responseLine.trim());
				    }
				    response.sendRedirect("/AdminPageLearingApp/AdminPage");
				    System.out.println("Response Stream: " + response.toString());
				}
				catch(Exception ex)
				{
					connection.disconnect();
					System.out.println("Get response error: \n" + ex.getMessage());
				}
				//TODO load indexcontroller
			}
			else
			{
				sb.append("Title: " + request.getParameter("categoryTitle") + "\n");
				sb.append("Description: " + request.getParameter("descriptionCategoryTextfield") + "\n");
				System.out.println(sb.toString() + "\n" + "There is something missing in category!");
				connection.disconnect();
			}
		}
		else
		{
			connection.disconnect();
			response.sendRedirect("/AdminPageLearingApp/AdminPage");
			System.out.println("SubmitButton not found.");
		}
	}
}
