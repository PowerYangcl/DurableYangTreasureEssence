package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 删除一个系统功能节点及其子节点
 * @tag MANAGER-API-118
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McSysFunctionDto.class)
public class ManagerApi118Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;  
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McSysFunctionDto dto = JSONObject.parseObject(param.getString("data"), McSysFunctionDto.class);
		return mcSysFunctionService.deleteNode(dto);
	}

}









































