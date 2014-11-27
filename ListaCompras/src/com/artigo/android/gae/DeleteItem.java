package com.artigo.android.gae;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DeleteItem extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String nome = req.getParameter("nome");
        
		PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        PersistenceManager pm = PMF.getPersistenceManager();
		Query q = pm.newQuery(ItemCompra.class);
		q.setFilter("nome == '"+nome+"'");
        
        try {
          List<ItemCompra> results = (List<ItemCompra>) q.execute();
          if (!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
            	ItemCompra item = results.get(i);
            	item.setComprado(true);
	    		pm.makePersistent(item);
            }
            resp.getWriter().print("{\"status\":1}");
          }
        } catch (Exception e){
        	resp.getWriter().print("{\"status\":0}");
        } finally {
          q.closeAll();
        }
	}
}