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
public class ListItensJson extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
        
		PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        PersistenceManager pm = PMF.getPersistenceManager();
		Query q = pm.newQuery(ItemCompra.class);
		q.setFilter("comprado == false");
        
        try {
          List<ItemCompra> results = (List<ItemCompra>) q.execute();
          if (!results.isEmpty()) {
        	resp.getWriter().print("{\"itens\":[");
            for (int i = 0; i < results.size(); i++) {
            	ItemCompra item = results.get(i);
	    		String nome = item.getNome();
	    		float qtd = item.getQuantidade();
	    		boolean comprado = item.isComprado();
	    		resp.getWriter().print(
    				"{"+
			        "\"nome\":\""+nome+"\","+
			        "\"quantidade\":"+qtd+","+
			        "\"comprado\":"+comprado+
			        "}"
    			);
	    		
	    		if (i < results.size() - 1){
	    			resp.getWriter().print(
	    				","
	    			);
	    		}
            }
            resp.getWriter().print("]}");
          }
        } finally {
          q.closeAll();
        }
	}
}
