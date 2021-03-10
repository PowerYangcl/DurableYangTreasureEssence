package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.entity.McRole;
import com.matrix.service.IMcRoleService;

/**
 * @description: 角色详情|唯一参数：info.id mc_role表自增id
 * @tag MANAGER-API-113
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体   McSysFunctionDto
 * {
	 "type": "role",
	 "id": "62"
   }
   response返回类型
   {McSysFunction}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.entity.McRole.class)
public class ManagerApi113Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRole info = JSONObject.parseObject(param.getString("data"), McRole.class);
		return mcRoleService.ajaxFindRoleInfo(info);
	}

}








































