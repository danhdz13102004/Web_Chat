package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;

import dao.UserDAO;
import model.User;
import util.HibernateUtil;
@WebServlet(urlPatterns = "/user/*")
@MultipartConfig
public class UserController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		try {
			url = request.getRequestURL().toString();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(url.contains("dang-nhap")) {
			dangNhap(request, response);
		}
		else if(url.contains("dang-ki")) {
			dangKi(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void dangNhap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = UserDAO.getInstance().selectUserByUsernameAndPassword(username, password, session);
		
		HttpSession session2 = request.getSession();
		if(user != null) {
			session2.setAttribute("user", user);	
			RequestDispatcher rq = getServletContext().getRequestDispatcher("/index1.jsp");
			rq.forward(request, response);
		}	
	}
	
	protected void dangKi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		
		Part part = request.getPart("avatar");
		String urlImage = "";
		if(part != null) {
			String savePath = getServletContext().getRealPath("/file/avatar/");
			Random rd = new Random();
			String filename = System.currentTimeMillis() + rd.nextInt(1000) + "" + ".jpg";
			
			if(!Files.exists(Path.of(savePath))) {
				Files.createDirectories(Path.of(savePath));
			}
			part.write(savePath + "/" + filename);
			urlImage = "/file/avatar/" + filename;			
		}
		else {
			urlImage = "https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?w=740&t=st=1720188408~exp=1720189008~hmac=39c6bacacb4eba270d4c1e61445e8c3b548cf87ac084b0eb1d550e5ebca8f6be";
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		User u  = new User(username, password, fullname, urlImage, true);
		UserDAO.getInstance().insert(u, session);
		session.close();
		RequestDispatcher rq = getServletContext().getRequestDispatcher("/login1.jsp");
		rq.forward(request, response);
	}
	
}
