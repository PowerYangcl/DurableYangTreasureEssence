package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.request.UpdateTreeNodesRequest;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 系统功能同层节点拖拽更新
 * @tag MANAGER-API-128
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.UpdateTreeNodesRequest.class)
public class ManagerApi128Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		UpdateTreeNodesRequest dto = JSONObject.parseObject(param.getString("data"), UpdateTreeNodesRequest.class);
		return mcOrganizationService.updateTreeNodes(dto);
	}

}






























