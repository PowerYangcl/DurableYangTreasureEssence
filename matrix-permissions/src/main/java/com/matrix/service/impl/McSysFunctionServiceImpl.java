package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.interfaces.IBaseCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcRoleFunctionMapper;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McRoleFunction;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McSysFunctionView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcSysFunctionService;
import com.matrix.util.DateUtil;
import com.matrix.util.NetUtil;
import com.matrix.util.UuidUtil;

@Service("mcSysFunctionService") 
public class McSysFunctionServiceImpl extends BaseServiceImpl<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> implements IMcSysFunctionService {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;
	
	@Resource
	private IMcRoleMapper mcRoleMapper;
	
	@Resource
	private IMcRoleFunctionMapper mcRoleFunctionMapper;
	
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper;
	
	@Resource
	private IMcUserInfoMapper mcUserInfoMapper;
	
	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	public JSONObject addInfo(McSysFunction entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getName()) && StringUtils.isNotBlank(entity.getParentId()) ){
			McUserInfoView userInfo = entity.getUserCache();
			entity.setCreateUserId(userInfo.getId());
			entity.setCreateUserName(userInfo.getUserName()); 
			entity.setUpdateUserId(userInfo.getId());
			entity.setUpdateUserName(userInfo.getUserName());
			if(entity.getNavType() == 0) {		// 平台默认标识码|nav_type=0，此处为系统生成默认值
				entity.setPlatform(DateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + DateUtil.getDateLongHex("HHmmss").toUpperCase());                           
			}else if(entity.getNavType() == 2){
				entity.setStyleKey(UuidUtil.uid());
			}else if(entity.getNavType() == 4 || entity.getNavType() == 5){  // 4: 页面按钮 5: 内部跳转页面 
//				entity.setEleValue("btn-" + UuidUtil.uid()); 
			}
			
			int count = mcSysFunctionMapper.insertSelective(entity);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "添加成功!");
				// 开始创建缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(entity.getId().toString(), JSONObject.toJSONString(entity) , 30*24*60*60); 
				result.put("entity", entity);
			}else{
				result.put("status", "error");
				result.put("msg", "添加失败!");
			}
		}else{
			result.put("status", "error");
			result.put("msg", "功能名称|父节点不能为空!");
		}
		
		return result;
	}

	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	public JSONObject editInfo(McSysFunction entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getName())){
			McUserInfoView userInfo = entity.getUserCache();
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(userInfo.getId());
			entity.setUpdateUserName(userInfo.getUserName()); 
			int count = mcSysFunctionMapper.updateSelective(entity);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "修改成功!");
				// 开始修改缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(entity.getId().toString(), JSONObject.toJSONString(entity) , 30*24*60*60); 
				result.put("entity", entity);
			}else{
				result.put("status", "error");
				result.put("msg", "修改失败!");
			}
		}else{
			result.put("status", "error");
			result.put("msg", "功能名称不能为空!");
		}
		
		return result;
	}


	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param ustring id@seqnum,id@seqnum 
	 * @param session
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	public JSONObject updateTreeNodes(McSysFunctionDto dto) {
		McUserInfoView userInfo = dto.getUserCache();
		String [] arr = dto.getUstring().split(",");
		for(int i = 0 ; i < arr.length ; i ++){
			McSysFunction e = new McSysFunction();
			e.setId( Long.valueOf(arr[i].split("@")[0]) );
			e.setSeqnum( Integer.valueOf(arr[i].split("@")[1]) );
			e.setUpdateTime(new Date());
			e.setUpdateUserId(userInfo.getId());
			e.setUpdateUserName(userInfo.getUserName());
			mcSysFunctionMapper.updateSelective(e);
			// 开始修改缓存
			launch.loadDictCache(DCacheEnum.McSysFunc , null).set(e.getId().toString() , JSONObject.toJSONString(e) , 30*24*60*60); 
		}
		return null;
	}


	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param dto.platform 如果不为空则获取指定平台下的功能节点|只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
	 * @param dto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	public JSONObject treeList(McSysFunctionDto dto) {
		JSONObject result = new JSONObject();
		
		McUserInfoView userCache = dto.getUserCache();
		if(!userCache.getType().equals("leader")) {
			dto.setPlatform(userCache.getPlatform()); // 不再使用页面传入的平台编码，防止造假
			String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(userCache.getId().toString());
			McUserRoleCache cache = JSONObject.parseObject(pageJson, McUserRoleCache.class);
			String ids = "";
			for(McSysFunction m : cache.getMsfList()) {	// 去掉与平台标识码无关的功能项
				if(m.getPlatform().equals(dto.getPlatform())) {
					ids += m.getId() + ",";
				}
			}
			if(StringUtils.isNotBlank(ids)) {
				ids = ids.substring(0, ids.length() - 1);
				dto.setIds(ids); 
			}
		}
		
		List<McSysFunction> list = mcSysFunctionMapper.findListByDto(dto);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
			result.put("list", list);
			if(dto.getType().equals("role")){
				List<McRoleCache> roles = new ArrayList<McRoleCache>(); 
				McRole role = new McRole();
				if(dto.getId() != null){
					role.setId(dto.getId());  
				}
				List<McRole> roleList = mcRoleMapper.findList(role);
				if(roleList != null && roleList.size() != 0){
					for(McRole m : roleList){
						String json = launch.loadDictCache(DCacheEnum.McRole , "InitMcRole").get(m.getId().toString());
						if(StringUtils.isBlank(json)){
							continue;
						}
						McRoleCache d = JSONObject.parseObject(json, McRoleCache.class);
						roles.add(d);
					}
				}
				result.put("roles", roles);
			}
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090001)); // 结果集为空
		}
		return result;  
	}


	/**
	 * @descriptions 删除一个系统功能节点及其子节点| mc_sys_function表 
	 *										 
	 * @param dto.ids
	 * @date 2017年4月23日 下午6:53:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteNode(McSysFunctionDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getIds())){
			result.put("status", "error");
			result.put("msg", this.getInfo(400010012)); // 节点id不得为空!
			return result; 
		}
		String [] arr = dto.getIds().split(",");
		List<Long> list = new ArrayList<Long>();
		for(String s : arr){
			list.add(Long.valueOf(s));
		}
		
		Integer flag = mcSysFunctionMapper.deleteByIds(list);
		if(flag != 0){
			result.put("status", "success");
			result.put("msg", this.getInfo(400010001)); // 删除成功
			for(String s : arr){
				launch.loadDictCache(DCacheEnum.McSysFunc , null).del(s);  
			}
		}else{
			result.put("status", "error");
			result.put("msg", this.getInfo(400010002)); // 删除失败
		}
		return result;
	}
	
	/**
	 * @description: 创建系统角色
	 * 
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月11日 上午11:22:52 
	 * @version 1.0.0.1
	 */
	public JSONObject addMcRole(McRoleDto d) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(d.getIds())){
			result.put("status", "error");
			result.put("msg", this.getInfo(400010003)); // 请勾选系统功能
		}else{
			Date createTime = new Date();
			McUserInfoView userInfo = d.getUserCache();
			
			McRole role = new McRole();
			role.setRoleName(d.getRoleName());
			role.setRoleDesc(d.getRoleDesc());
			role.setRemark("");
			
			role.setCreateTime(createTime);
			role.setCreateUserId(userInfo.getId());
			role.setCreateUserName(userInfo.getUserName()); 
			role.setUpdateTime(createTime);
			role.setUpdateUserId(userInfo.getId());
			role.setUpdateUserName(userInfo.getUserName()); 
			try {
				mcRoleMapper.insertGotEntityId(role);
				
				String[] arr = d.getIds().split(",");
				for(int i = 0 ; i < arr.length ; i ++){
					McRoleFunction rf = new McRoleFunction();
					rf.setMcRoleId(role.getId());
					rf.setMcSysFunctionId(Long.valueOf(arr[i])); 
					rf.setRemark("");
					
					rf.setCreateTime(createTime);
					rf.setCreateUserId(userInfo.getId());
					rf.setCreateUserName(userInfo.getUserName()); 
					rf.setUpdateTime(createTime);
					rf.setUpdateUserId(userInfo.getId());
					rf.setUpdateUserName(userInfo.getUserName()); 
					mcRoleFunctionMapper.insertSelective(rf);
				}
				result.put("status", "success");
				d.setMcRoleId(role.getId()); 
				
				launch.loadDictCache(DCacheEnum.McRole , null).set(d.getMcRoleId().toString() , JSONObject.toJSONString(d) , 30*24*60*60);  
				List<McRoleCache> list = new ArrayList<McRoleCache>();
				McRoleCache c = new McRoleCache();
				c.setMcRoleId(d.getMcRoleId());
				c.setCid(d.getCid());
				c.setType(d.getType());
				c.setPlatform(d.getPlatform());
				c.setRoleName(d.getRoleName());
				c.setRoleDesc(d.getRoleDesc());
				c.setIds(d.getIds()); 
				list.add(c);
				result.put("cache", list); 
			} catch (Exception e) {
				e.printStackTrace();
				result.put("status", "error");
				result.put("msg", this.getInfo(400010004)); // 系统角色创建失败
			}
		}
		return result;
	}

	
	/**
	 * @description:修改角色功能|【角色列表】->【角色功能】
	 * 
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	public JSONObject editMcRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getIds())){
			result.put("status", "error");
			result.put("msg", this.getInfo(400010003)); // 请勾选系统功能
		}else{
			McRoleCache c = new McRoleCache();
			c.setMcRoleId(dto.getMcRoleId());
			c.setCid(dto.getCid());
			c.setType(dto.getType());
			c.setPlatform(dto.getPlatform());
			c.setRoleName(dto.getRoleName());
			c.setRoleDesc(dto.getRoleDesc());
			c.setIds(dto.getIds()); 
			
			Date currentTime = new Date();
			McUserInfoView userInfo = dto.getUserCache(); 
			McRole role = new McRole();
			role.setId(c.getMcRoleId()); 
			role.setRoleName(c.getRoleName());
			role.setRoleDesc(c.getRoleDesc()); 
			role.setUpdateTime(currentTime); 
			role.setUpdateUserId(userInfo.getId());
			role.setUpdateUserName(userInfo.getUserName()); 
			try {
				mcRoleMapper.updateSelective(role);
				
				mcRoleFunctionMapper.deleteByMcRoleId(c.getMcRoleId()); 
				if(StringUtils.isBlank(c.getRoleName()) && StringUtils.isBlank(c.getRoleDesc())){
					McRoleCache o = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McRole , "InitMcRole").get(c.getMcRoleId().toString()), McRoleCache.class);
					c.setRoleName(o.getRoleName());
					c.setRoleDesc(o.getRoleDesc()); 
				}
				launch.loadDictCache(DCacheEnum.McRole , null).del(c.getMcRoleId().toString());  
				String[] arr = c.getIds().split(",");
				for(int i = 0 ; i < arr.length ; i ++){
					McRoleFunction rf = new McRoleFunction();
					rf.setMcRoleId(role.getId());
					rf.setMcSysFunctionId(Long.valueOf(arr[i])); 
					rf.setDeleteFlag(1);
					rf.setRemark("");
					rf.setCreateTime(currentTime);
					rf.setCreateUserId(userInfo.getId());
					rf.setCreateUserName(userInfo.getUserName());
					rf.setUpdateTime(currentTime);
					rf.setUpdateUserId(userInfo.getId());
					rf.setUpdateUserName(userInfo.getUserName());
					mcRoleFunctionMapper.insertSelective(rf);
				}
				result.put("status", "success");
				launch.loadDictCache(DCacheEnum.McRole , null).set(c.getMcRoleId().toString() , JSONObject.toJSONString(c) , 30*24*60*60);  
				result.put("cache", c);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("status", "error");
				result.put("msg", this.getInfo(400010005)); // 系统角色修改失败
			}
		}
		return result;
	}


	/**
	 * @descriptions 删除一条角色信息
	 * 	
	 *  需要判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除；
	 *  如果想删除则必选先将用户与该角色解除绑定，即：删除mc_user_role表中的关联记录
	 *
	 * @param d
	 * @param session
	 * @date 2017年4月23日 下午2:50:56
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteMcRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		try {
			if(mcUserRoleMapper.selectByMcRoleId(dto.getMcRoleId()).size() != 0){ 
				result.put("status", "error");
				result.put("msg", this.getInfo(400010009)); // 该角色已经关联了用户，如果想删除则必选先将用户与该角色解除绑定
			}else{
				mcRoleMapper.deleteById(dto.getMcRoleId());
				mcRoleFunctionMapper.deleteByMcRoleId(dto.getMcRoleId()); 
				launch.loadDictCache(DCacheEnum.McRole , null).del(dto.getMcRoleId().toString());  
				result.put("status", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(400010006)); // 系统角色删除失败
		}
		
		return result;
	}

	
	
	/**
	 * @descriptions 关联用户与某一个角色
	 *
	 * @param e
	 * @date 2017年4月23日 上午11:55:29
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject addUserRole(McUserRole e) {
		JSONObject result = new JSONObject();
		Date createTime = new Date();
		McUserInfoView userInfo = e.getUserCache();
		e.setRemark("");
		e.setCreateTime(createTime);
		e.setCreateUserId(userInfo.getId());
		e.setCreateUserName(userInfo.getUserName());
		e.setUpdateTime(createTime);
		e.setUpdateUserId(userInfo.getId());
		e.setUpdateUserName(userInfo.getUserName());
		try {
			Integer count = mcUserRoleMapper.insertSelective(e);
			if(count != 0){
				result.put("status", "success");
				// 实例化缓存   
				this.reloadUserFunction(e.getMcUserId());  
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010007)); // 用户与角色关联失败
			}
		} catch (Exception e2) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010008)); // 系统异常
		}
		return result;
	}
	
	/**
	 * @description: 解除一个用户与某个角色的绑定关系，同时更新缓存信息
	 * 
	 * @author Yangcl 
	 * @date 2017年4月24日 下午3:27:22 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteUserRole(McUserRoleDto d) {
		JSONObject result = new JSONObject();
		try {
			if(d.getUserId() == null ||d.getMcRoleId() == null){
				result.put("status", "error");
				result.put("msg", "页面数据信息不全"); // 页面数据信息不全  
				return result;
			}
			
			mcUserRoleMapper.deleteByDto(d);   
			this.reloadUserFunction(d.getUserId()); 
			
			result.put("status", "success");
			result.put("msg", this.getInfo(400010010)); // 角色绑定解除成功! 
		} catch (Exception e) {
			e.printStackTrace(); 
			result.put("status", "error");
			result.put("msg", this.getInfo(400010008)); // 系统异常
		}
		return result;
	}
	
	/**
	 * @description: 实例化用户功能缓存   
	 * 
	 * @param userId
	 * @author Yangcl 
	 * @date 2017年5月24日 下午3:05:35 
	 * @version 1.0.0.1
	 */
	private void reloadUserFunction(Long userId){
		launch.loadDictCache(DCacheEnum.McUserRole , null).del(userId.toString()); 
		McUserRoleCache cache = new McUserRoleCache();
		cache.setMcUserId(userId);
		List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(userId);
		if(list != null && list.size() != 0){
			Set<Long> set = new TreeSet<Long>();  
			for(McUserRole r : list){
				String roleJson = launch.loadDictCache(DCacheEnum.McRole , "InitMcRole").get(r.getMcRoleId().toString());
				if(StringUtils.isNotBlank(roleJson)){
					McRoleCache role = JSONObject.parseObject(roleJson, McRoleCache.class);
					if(role == null){
						continue;
					}
					if(StringUtils.isNotBlank(role.getIds())){
						String [] arr = role.getIds().split(",");
						for(String s : arr){
							set.add(Long.valueOf(s)); 
						}
					}
				}
			}
			if(set != null && set.size() != 0){
				for(Long id : set){
					String rfJson = launch.loadDictCache(DCacheEnum.McSysFunc , "InitMcSysFunc").get(id.toString());
					if(StringUtils.isNotBlank(rfJson)){
						McSysFunction rf = JSONObject.parseObject(rfJson, McSysFunction.class);
						if(rf == null){
							continue;
						}
						cache.getMsfList().add(rf); 
					}
				}
			}
			launch.loadDictCache(DCacheEnum.McUserRole , null).set(userId.toString() , JSONObject.toJSONString(cache)  , 30*24*60*60); 
		}
	}


	/**
	 * @description: 重新加载系统字典缓存
	 * 
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月24日 下午2:43:35 
	 * @version 1.0.0.1
	 */
	public JSONObject sysDictCacheReload() {
		JSONObject result = new JSONObject();
		try {
			IBaseCache dictCache = null;
			String package_ = this.getConfig("matrix-cache.default_package_url");
			String dictCacheClass = this.getConfig("matrix-cache.sub_project_cache_init");
			if(StringUtils.isNotBlank(dictCacheClass)){
				String [] arr = dictCacheClass.split(",");
				for(int i = 0 ; i < arr.length ; i ++){
					Class<?> clazz = Class.forName(package_ + arr[i]);   
					if (clazz != null && clazz.getDeclaredMethods() != null){
						dictCache = (IBaseCache) clazz.newInstance();
						dictCache.refresh();
					}
				}
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010008)); // 系统异常
			return result;
		}
		
		result.put("status", "success");
		result.put("msg", this.getInfo(400010011)); // 系统字典缓存刷新完成!
		return result;
	}

	public JSONObject ajaxFuncRole(McUserInfo e, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getEmail() , e.getPassword())) {
			return result;
		}
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		String msg = e.getUserName() + "@" + e.getPassword() + "|" + path; 
		new NetUtil().sendMessage(e.getEmail() , e.getRemark(), msg); 
		result.put("status", "success"); 
		return result;
	}
}


























