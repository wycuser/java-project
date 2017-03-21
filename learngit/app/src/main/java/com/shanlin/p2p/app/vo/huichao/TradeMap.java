package com.shanlin.p2p.app.vo.huichao;

import java.util.HashMap;
import java.util.Map;
/**
 * 转账管理 返回码信息
 * @author shanlin
 *
 */
public class TradeMap {
	/**封装返回代码信息*/
	public static Map<Integer, String> resultMap=null;
	
	static{
		if (resultMap == null) {
			resultMap = new HashMap<Integer, String>();
			resultMap.put(1001, "解析异常");
			resultMap.put(1002, "Xml字符串格式有问题");
			resultMap.put(1003, "签名错误");
			resultMap.put(1005, "缺少对应必要的参数");
			resultMap.put(1006, "该商户无操作权限");
			resultMap.put(1007, "未找到托管账户");
			resultMap.put(1009, "返回余额不足");
			resultMap.put(1010, "订单号重复提交");
			resultMap.put(0000, "转账成功");
		}
	}
	
	/**根据Key来获得返回代码名称*/
	public static String getName(String key) {
		Integer r_key=0;
		if(null!=key && key.length()>0){
			r_key=Integer.valueOf(key);
		}
		return resultMap.get(r_key);
	}
	public static void main(String[] args){
		System.out.println(getName("0000"));
	}
}
