package com.gec.hrm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.hrm.entity.Document;
import com.gec.hrm.entity.User;
import com.gec.hrm.service.IDocumentService;
import com.gec.hrm.service.impl.DocumentServiceImpl;

@WebServlet("/documentServlet")
public class DocumentServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IDocumentService documentService = new DocumentServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		Document document = new Document();
		document.setTitle(title);
		List<Document> list = documentService.selectAll(document);
		request.setAttribute("lists", list);
		request.setAttribute("document", document);
		request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
	}

//	public void list(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String title = request.getParameter("title");
//		Document document = new Document();
//		document.setTitle(title);
//		String dq = request.getParameter("dq");
//		int dangQianPage;
//		if(dq==null||dq=="")
//			dangQianPage = 1;
//		else
//			dangQianPage = new Integer(dq);
//		PageBean<Document> page = documentService.getOnePage(dangQianPage, 10, document);
//		request.setAttribute("page", page);
//		request.setAttribute("document", document);
//		request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
//	}
	
	public void toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
	}
	
	public void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document document = new Document();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
		if(isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					boolean isFormField = fileItem.isFormField();
					if (isFormField) {
						String inputName= fileItem.getFieldName();
						if(inputName.equals("title")) {
							document.setTitle(fileItem.getString("utf-8"));
						}else if(inputName.equals("remark")) {
							document.setRemark(fileItem.getString("utf-8"));
						}else if(inputName.equals("userId")) {
							document.setUser(new User());
							document.getUser().setId(new Integer(fileItem.getString("utf-8")));
						}
					}else {
						String fileName= fileItem.getName();
						String extexdsName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
						String uuid = UUID.randomUUID().toString();
						//img = uuid+extexdsName;
						document.setFilename(fileName.substring(0,fileName.lastIndexOf(".")));
						document.setFiletype(extexdsName);
						document.setFilebytes(uuid+extexdsName);
						
						String uploadPath = this.getServletContext().getRealPath("upload");
						File file = new File(uploadPath, document.getFilebytes());
						try {
							fileItem.write(file);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(documentService.add(document)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/documentServlet?action=list").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameter("ids").split(",");
		int[] ids = new int[strIds.length];
		
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}

		System.out.println(request.getParameter("ids"));
		String uploadPath = this.getServletContext().getRealPath("upload");
		File file;
		for(int id : ids) {
			file = new File(uploadPath, documentService.selectId(id).getFilebytes());
			file.delete();
		}
		documentService.del(ids);

		request.getRequestDispatcher("/documentServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void toEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		Document document = documentService.selectId(id);
		request.setAttribute("edocument", document);
		request.setAttribute("dq", request.getParameter("dq"));
		request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
	}
	
	public void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document document = new Document();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
		if(isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					boolean isFormField = fileItem.isFormField();
					if (isFormField) {
						String inputName= fileItem.getFieldName();
						if(inputName.equals("title")) {
							document.setTitle(fileItem.getString("utf-8"));
						}else if(inputName.equals("remark")) {
							document.setRemark(fileItem.getString("utf-8"));
						}else if(inputName.equals("userId")) {
							document.setUser(new User());
							document.getUser().setId(new Integer(fileItem.getString("utf-8")));
						}else if(inputName.equals("id")) {
							document.setId(new Integer(fileItem.getString("utf-8")));
						}
					}else {
						String fileName= fileItem.getName();
						System.out.println("在改了");
						if(fileName!=""&&fileName!=null) {
							String extexdsName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
							String uuid = UUID.randomUUID().toString();
							document.setFilename(fileName.substring(0,fileName.lastIndexOf(".")));
							document.setFiletype(extexdsName);
							document.setFilebytes(uuid+extexdsName);
							
							String uploadPath = this.getServletContext().getRealPath("upload");
							File file = new File(uploadPath, document.getFilebytes());
							
							File del = new File(uploadPath, documentService.selectId(document.getId()).getFilebytes());
							del.delete();
							
							try {
								fileItem.write(file);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(document.getUser().getId());
		if(documentService.edit(document)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/documentServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   Document document = documentService.selectId(new Integer(request.getParameter("id")));	
	       String filename = URLEncoder.encode(document.getFilename(), "UTF-8");  
	       response.setContentType(getServletContext().getMimeType(document.getFiletype()));  
	       response.setHeader("Content-Disposition", "attachment;filename="+filename); 
	       String fullFileName = getServletContext().getRealPath("/upload/" + document.getFiletype()); 
	       InputStream in = new FileInputStream(fullFileName);  
	       OutputStream out = response.getOutputStream();  
	       int b;  
	       while((b=in.read())!= -1)  
	       {  
	           out.write(b);  
	       }  
	         
	       in.close();  
	       out.close();  
	}
	
}
