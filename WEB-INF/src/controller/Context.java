package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.context.interfaces.IHtmlContext;

public class Context implements IContext, IHtmlContext {
	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String actionClass;
	private final List<String> jsLinks = new ArrayList<>();
	private final List<String> cssLinks = new ArrayList<>();
	private String pageTitle;
	private String pageDescription;
	private final List<String> keywords = new ArrayList<String>();
	private final Map<String,Object> parameters;
	private final File[] files;
	
	public Context(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		request = httpServletRequest;
		response = httpServletResponse;
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			parameters = new HashMap<String,Object>(request.getParameterMap());
			files = new File[] {};
		}else {
			List<File> toReturn = new ArrayList<>();
			parameters = new HashMap<>();
			try {
				for(FileItem item:getFileItemList(request)) {
					if(!item.isFormField()) {
						//File file = new File("./tmp_" + request.getSession().getId() + item.getName());
						File file = new File("./" + item.getName());
						item.write(file);
						toReturn.add(file);
					}else {
						parameters.put(item.getFieldName(), appendToArray((String[])parameters.get(item.getFieldName()), item.getString()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				files = new File[]{};
				return;
			}
			files = toReturn.toArray(new File[0]);
		}
		
	}
    private String[] appendToArray(String[] tab, String toAdd) {
        
        if(tab == null)
                return new String[] { toAdd };
       
        String[] newtab = new String[tab.length + 1];
        int i = 0;
       
        for(; i < tab.length; i++)
                newtab[i] = tab[i];
        newtab[i] = toAdd;
       
        return newtab;
    }	
	@Override
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	@Override
	public String getActionClass() {
		return actionClass;
	}

	@Override
	public HttpServletRequest _getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse _getResponse() {
		return response;
	}

	@Override
	public Object getParameter(String key) {
		return parameters.get(key);
	}
	public Object setParameter(String key,Object parameter) {
		return parameters.put(key,parameter);
	}

	@Override
	public String[] getParameterKeys() {
		return (String[]) parameters.keySet().toArray();
	}

	@Override
	public void setAttribute(String key, Object o) {
		request.setAttribute(key, o);
	}

	@Override
	public Object getAttribute(String key) {
		return request.getAttribute(key);
	}

	@Override
	public void setSessionAttribute(String key, Object value) {
		request.getSession().setAttribute(key,value);
	}

	@Override
	public String getSessionAttribute(String key) {
		return (String) request.getSession().getAttribute(key);
	}

	@Override
	public boolean resetSession() {
		if(!request.getSession().isNew()) return false;
		request.getSession().invalidate();
		return true;
	}

	@Override
	public IHtmlContext toHtmlContext() {
		return this;
	}

	@Override
	public File[] getUploadedFiles() {
		return files;
	}
	private List<FileItem> getFileItemList(HttpServletRequest request) throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		return upload.parseRequest(request);
	}
	@Override
	public void setPageTitle(String title) {
		pageTitle = title;
	}

	@Override
	public void setPageDescription(String description) {
		pageDescription = description;
	}

	@Override
	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}

	@Override
	public void addCssLink(String url) {
		cssLinks.add(url);
	}

	@Override
	public void addJsLink(String url) {
		jsLinks.add(url);
	}

	@Override
	public String getPageTitle() {
		return pageTitle;
	}

	@Override
	public String getPageDescription() {
		return pageDescription;		
	}

	@Override
	public String[] getKeywords() {
		return (String[]) keywords.toArray();
	}

	@Override
	public String[] getCssLinks() {
		return (String[]) cssLinks.toArray();
	}

	@Override
	public String[] getJsLinks() {
		return (String[]) jsLinks.toArray();
	}
	@Override
	public String[] getUserCredentials() {
		return null;
	}
}
