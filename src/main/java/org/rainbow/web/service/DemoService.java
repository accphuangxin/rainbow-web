package org.rainbow.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hx.rainbow.common.context.RainbowContext;
import org.hx.rainbow.common.util.ObjectId;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class DemoService{

	public RainbowContext show(RainbowContext context){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < 10; i++){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("guid", new ObjectId().toString());
			data.put("userName", "张三"+i);
			data.put("userNo", 9527);
			data.put("createTime", new Date());
			data.put("creater", "系统管理员");
			data.put("modifyTime", new Date());
			data.put("modifyer", "系统管理员");
			dataList.add(data);
		}
		context.setRows(dataList);
		context.setMsg("获取数据完成!");
		return context;
 	}
}
