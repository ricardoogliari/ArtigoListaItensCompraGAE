package com.artigo.android.gae;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class InsertItem extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		String nome = req.getParameter("nome");
		String quantidade = req.getParameter("quantidade");
        
        PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        Key k = KeyFactory.createKey(ItemCompra.class.getSimpleName(), nome);
        
		PersistenceManager pm = PMF.getPersistenceManager();
		ItemCompra item = new ItemCompra();
        item.setNome(nome);
        item.setQuantidade(Float.parseFloat(quantidade));
        item.setKey(k);
        item.setComprado(false);
        pm.makePersistent(item);
	    
        resp.sendRedirect("listItens");
	}
}
