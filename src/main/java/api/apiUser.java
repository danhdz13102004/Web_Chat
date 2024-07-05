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
import dao.GroupDAO;
import dao.GroupUsersDAO;
import dao.HQLutil;
import dao.MessageDAO;
import dao.UserDAO;
import model.Friend;
import model.Group_Users;
import model.Message;
import model.User;
import model.Friend.Status;
import serializer.GroupUsersSerializer;
import serializer.UserSerializer;
import util.HibernateUtil;
@WebServlet(urlPatterns = "/api/user/*")
public class apiUser extends HttpServlet {
	@Override
	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		try {
			url = request.getRequestURL().toString();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(url.contains("selectAll")) {
			selectAll(request, response);
		}
		else if(url.contains("selectConversation")) {
			selectConversation(request, response);
		}
		else if(url.contains("selectGroupChatCoversation")) {
			selectGroupChatCoversation(request, response);
		}
		else if(url.contains("selectGroupOfUser")) {
			selectGroupOfUser(request, response);
		}
		else if(url.contains("selectUserToAdd")) {
			selectUserToAdd(request, response);
		}
		else if(url.contains("selectUserToSendRequest")) {
			selectUserToSendRequest(request, response);
		}
		else if(url.contains("selectFriend")) {
			selectFriend(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id == null) id = "";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<User> li = UserDAO.getInstance().selectAll(session);
		if(id != "") {
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
	}
	
	protected void selectConversation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer size = Integer.parseInt(request.getParameter("size"));
		ArrayList<Message> li = MessageDAO.getInstance().selectConversation(id1, id2,page,size, session);
		Message m = li.get(li.size() - 1);

		Gson gson = new Gson();
		String json = gson.toJson(li);
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        session.close();
	}
	
	protected void selectGroupOfUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String id = request.getParameter("id");
		ArrayList<Group_Users> li = GroupUsersDAO.getInstance().selectByIdUser(id, session);
		GsonBuilder gb = new GsonBuilder();
    	gb.registerTypeAdapter(Group_Users.class, new GroupUsersSerializer());
    	Gson gson = gb.create();
    	String json = gson.toJson(li);
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();	
        session.close();
	}
	
	protected void selectGroupChatCoversation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String id = request.getParameter("id");
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer size = Integer.parseInt(request.getParameter("size"));
		ArrayList<Message> li = MessageDAO.getInstance().selectGroupChat(id,page,size, session);
		Gson gson = new Gson();
		String json = gson.toJson(li);
    	response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();	
        session.close();
	}
	
	protected void selectUserToAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<User> li = UserDAO.getInstance().selectUserToAdd(id, session);
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
	}
	
	protected void selectUserToSendRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<User> li = UserDAO.getInstance().selectUserToSendRequest(id, name, session);
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
	}
	
	protected void selectFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Friend> res = FriendDAO.getInstance().selectFriend(id, session,Status.DAXACNHAN);
		ArrayList<User> li = new ArrayList<User>();
		for(Friend f : res) {
			User u;
			if(!f.getSender().getId().equals(id)) {
//				li.add(f.getSender());
				u = UserDAO.getInstance().selectById(f.getSender().getId(), session);
			}
			else {
//				li.add(f.getReceiver());
				u = UserDAO.getInstance().selectById(f.getReceiver().getId(), session);
			}
			li.add(u);
				
		}
		for(User u : li) System.out.println(u.getId());
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
		
	}
}
