package com.learnmate.lms.orangetvmgt.web;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.collect.Lists;
import com.learnmate.lms.orangetvmgt.entity.OrangeTV;
import com.learnmate.lms.orangetvmgt.entity.OrangetvCatalog;
import com.learnmate.lms.orangetvmgt.service.OrangeTVService;
import com.learnmate.lms.orgmgt.entity.Users;
import com.learnmate.lms.service.ServiceException;
import com.learnmate.lms.utils.ExportExcel;
import com.learnmate.lms.web.CrudActionSupport;


/**
 * 桔子TV的Action
 * @author wyc
 * @createDate 2016年9月7日
 */
@Namespace("/orangetvmgt")
public class OrangetvAction extends CrudActionSupport<OrangeTV>{
	
	@Autowired
	private OrangeTVService orangeTVService;
	
	private OrangeTV orangeTV;

	private OrangetvCatalog orangetvCatalog;
	
	
	
	
	
	



	public OrangetvCatalog getOrangetvCatalog() {
		return orangetvCatalog;
	}

	
	public void setOrangetvCatalog(OrangetvCatalog orangetvCatalog) {
		this.orangetvCatalog = orangetvCatalog;
	}

	public OrangeTV getOrangeTV() {
		return orangeTV;
	}

	public void setOrangeTV(OrangeTV orangeTV) {
		this.orangeTV = orangeTV;
	}

	public OrangeTVService getOrangeTVService() {
		return orangeTVService;
	}
    
	@Autowired
	@Qualifier(value="orangeTVService")
	public void setOrangeTVService(OrangeTVService orangeTVService) {
		this.orangeTVService = orangeTVService;
	}

	@Override
	public OrangeTV getModel() {
		return orangeTV;
	}
	public String index()  throws Exception{
		orangetvCatalog =orangeTVService.getorangetvDescribe();
		System.out.println(orangetvCatalog.getDescription());
		return LIST;
	}

	@Override
	public String list() throws Exception {
		int stype= 0;
		if(getRequest().getParameter("stype")!=null){
		   stype=Integer.parseInt(getRequest().getParameter("stype").toString());
		}
		renderJson(orangeTVService.getOrangeTVList(getPageMap(), this.getKeyCode(),stype));
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public String exportorgetv() throws Exception{
		int stype= 0;
		if(getRequest().getParameter("stype")!=null){
		   stype=Integer.parseInt(getRequest().getParameter("stype").toString());
		}
	    List<Map> list = orangeTVService.getexportOrangeTVList(this.getKeyCode(), stype);
	    
		ExportExcel excel = new ExportExcel("桔子TV", Lists.newArrayList("名称","开始时间","结束时间","状态","更新时间","演讲人","类型"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Row row = null;
		for (Map orangeTV : list) {
			row = excel.addRow();
			excel.addCell(row, 0, orangeTV.get("tvname"));
			
			if(orangeTV.get("begindate") != null)
				excel.addCell(row, 1, dateFormat.format(orangeTV.get("begindate")));
			if(orangeTV.get("enddate") != null)
				excel.addCell(row, 2, dateFormat.format(orangeTV.get("enddate")));
			
			excel.addCell(row, 3, "0".equals(orangeTV.get("status").toString())? "未发布" : "已发布");
			excel.addCell(row, 4, dateFormat.format(orangeTV.get("modifieddate")));
			excel.addCell(row, 5, orangeTV.get("lecturer"));
			excel.addCell(row, 6, "1".equals(orangeTV.get("stype").toString())? "直播" : "点播");
		}
		//输出到浏览器
		excel.write(ServletActionContext.getResponse(), "桔子TV");
		return  null;
	}

	

}
