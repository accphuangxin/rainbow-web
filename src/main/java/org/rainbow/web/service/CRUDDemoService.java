package org.rainbow.web.service;

import java.util.Map;

import org.hx.rainbow.common.context.RainbowContext;
import org.hx.rainbow.common.core.service.BaseService;
import org.hx.rainbow.common.core.service.SoaManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service("crudService")
public class CRUDDemoService extends BaseService{
	private static final String NAMESPACE = "DEMO";//mybatis sqlMapper文件 namespace="demo"
	
    public RainbowContext query(RainbowContext context) {
        super.query(context, NAMESPACE);
        return context;
    }

    public RainbowContext insert(RainbowContext context) {
        try {
        	context = SoaManager.getInstance().invokeNoTx( new RainbowContext("demoService", "show"));
        	for(Map<String, Object> data : context.getRows()){
        		super.getDao().insert(NAMESPACE, "insert", data);
        	}
        	context.clearRows();
        	context.setMsg("插入成功!");
        } catch (Exception e) {
        	e.printStackTrace();
            context.setSuccess(false);
            context.setMsg(e.getMessage());
        }
        return context;
    }
    
    public RainbowContext insertBatch(RainbowContext context) {
    	try {
    		context = SoaManager.getInstance().invokeNoTx( new RainbowContext("demoService", "show"));
    		super.getDao().insertBatch(NAMESPACE, "insertBatch", context.getRows());
    		context.clearRows();
    		context.setMsg("插入成功!");
    	} catch (Exception e) {
    		e.printStackTrace();
    		context.setSuccess(false);
    		context.setMsg(e.getMessage());
    	}
    	return context;
    }

    public RainbowContext update(RainbowContext context) {
        super.update(context, NAMESPACE);
        return context;
    }

    public RainbowContext delete(RainbowContext context) {
        super.getDao().delete(NAMESPACE, "delete", context.getAttr()); //delete mybatis sqlMapper中的<delete id="delete">
        context.getAttr().clear();
        return context;
    }
}
