package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.FriendDAO;
import dao.MessageDAO;
import dao.UserDAO;
import model.Friend;
import model.Message;
import model.User;
import model.Friend.Status;
import serializer.FriendSerializer;
import serializer.UserSerializer;
import util.HibernateUtil;
@WebServlet(urlPatterns = "/api/friend/*")
public class apiFriend extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		try {
			url = request.getRequestURL().toString();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(url.contains("sendNewRequest")) {
			sendNewRequest(request, response);
		}
		else if(url.contains("selectHistoryFriend")) {
			selectHistoryFriend(request, response);
		}
		else if(url.contains("updateStatusFriend")) {
			updateStatusFriend(request, response);
		}
		else if(url.contains("selectFriend")) {
			selectFriend(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	protected void sendNewRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
		Friend f = new Friend(id1, id2, Status.CHOXACNHAN);
		Session session = HibernateUtil.getSessionFactory().openSession();
		FriendDAO.getInstance().insert(f, session);
	}
	
	protected void selectHistoryFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id  = request.getParameter("id");
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Friend> li = FriendDAO.getInstance().selectAllFriend(id, session);
		GsonBuilder gb = new GsonBuilder();
    	gb.registerTypeAdapter(Friend.class, new FriendSerializer());
    	Gson gson = gb.create();
    	String json = gson.toJson(li);
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();	
        session.close();
	}
	
	protected void updateStatusFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
		String status  = request.getParameter("status");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Status st;
		if(status.equals("confirm")) {
			st = Status.DAXACNHAN;
		}
		else {
			st = Status.DAHUY;
		}
		FriendDAO.getInstance().updateStatus(id1, id2, st, session);
	}
	
	protected void selectFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Friend> res = FriendDAO.getInstance().selectFriend(id, session,Status.DAXACNHAN);
		ArrayList<User> li = new ArrayList<User>();
		for(Friend f : res) {
			if(!f.getSender().getId().equals(id)) {
				li.add(UserDAO.getInstance().selectById(f.getSender().getId(), session2));
			}
			else {
				li.add(UserDAO.getInstance().selectById(f.getReceiver().getId(), session2));
			}
		}
		for(User u : li) {
			Message lastmsg = MessageDAO.getInstance().selectLastMessage(id, u.getId(), session);
			if(lastmsg != null) {
				if(lastmsg.getSender().equals(id)) {
					if(lastmsg.getType().equals("text")) {
						u.setLastmessage("You: " + lastmsg.getContent());
					}
					else if(lastmsg.getType().equals("video")) {
						u.setLastmessage("You sent a video ");
					}
					else if(lastmsg.getType().equals("audio")) {
						u.setLastmessage("You sent an audio ");
					}
					else if(lastmsg.getType().equals("image")) {
						u.setLastmessage("You sent an image");
					}
					else if(lastmsg.getType().equals("doc")) {
						u.setLastmessage("You sent a file");
					}
				}
				else {
					if(lastmsg.getType().equals("text")) {
						u.setLastmessage(lastmsg.getContent());
					}
					else if(lastmsg.getType().equals("video")) {
						u.setLastmessage("User sent a video ");
					}
					else if(lastmsg.getType().equals("audio")) {
						u.setLastmessage("User sent an audio ");
					}
					else if(lastmsg.getType().equals("image")) {
						u.setLastmessage("User sent an image");
					}
					else if(lastmsg.getType().equals("doc")) {
						u.setLastmessage("User sent a file");
					}
				}
			}
		}
		
		
 		GsonBuilder gb = new GsonBuilder();
    	gb.registerTypeAdapter(User.class, new UserSerializer());
    	Gson gson = gb.create();
    	String json = gson.toJson(li);
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();	
        session.close();
        session2.close();
		
	}
}
