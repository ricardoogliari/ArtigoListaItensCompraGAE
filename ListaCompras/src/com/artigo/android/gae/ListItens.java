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
public class ListItens extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
        
		PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        PersistenceManager pm = PMF.getPersistenceManager();
		Query q = pm.newQuery(ItemCompra.class);
		q.setOrdering("quantidade desc");
        
        resp.getWriter().println("<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\">"
        		+ "<title>Insert title here</title>"
        		+ "</head>"
        		+ "<body>"
        		+ "	<form><table border=\"1\" cellspacing=\"2\">");
        
        try {
          List<ItemCompra> results = (List<ItemCompra>) q.execute();
          if (!results.isEmpty()) {
            for (ItemCompra item : results) {
	    		String nome = item.getNome();
	    		float qtd = item.getQuantidade();
	    		boolean comprado = item.isComprado();
	    		resp.getWriter().println(
	    				"<tr><td>"+nome+"</td><td>"+qtd+"</td><td>"+(comprado?"Compra Finalizada":"Pendente")+"</td></tr>"
	    				);
            }
          } else {
            // Handle "no results" case
          }
        } finally {
          q.closeAll();
        }
	    
        resp.getWriter().println("</table></form></body></html>");
	}
}
