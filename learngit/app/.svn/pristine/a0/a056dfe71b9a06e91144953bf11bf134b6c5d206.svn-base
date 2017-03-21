package com.shanlin.p2p.csai.util;

import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidExtend;
import com.shanlin.p2p.app.model.enums.BidMode;
import com.shanlin.p2p.app.model.enums.BidPayMode;
import com.shanlin.p2p.app.model.enums.BidStatus;

/**
 * 获取P2p实体类
 * 
 * @site:http://api.csai.cn
 * @注意:此接口由p2p平台提供,供希财调用,此接口的作用为:p2p平台提供接口地址供希财获取指定的p2p产品信息, 
 *                                                           完成产品接口开发后在"我的站点"页面提交产品接口地址
 * 
 * @author ice
 *
 */
public class CsaiBidUtil {

	// 获取接口所需整型标状态
	public static Integer getBidStatus(Bid bid, BidExtend bidExtend) {
		// 产品募集状态 -1:流标,0:筹款中,1:已满标,2:已开始还款,3:预发布,4:还款完成,5:逾期(必须)
		BidStatus status = bid.getStatus();

		if (status == BidStatus.SQZ) {
		} else if (status == BidStatus.DSH) {
		} else if (status == BidStatus.DFB) {
		} else if (status == BidStatus.YFB) {
			return 3;
		} else if (status == BidStatus.TBZ) {
			return 0;
		} else if (status == BidStatus.DFK) {
			return 1;
		} else if (status == BidStatus.HKZ) {
			return 2;
		} else if (status == BidStatus.YJQ) {
			return 4;
		} else if (status == BidStatus.YLB) {
			return -1;
		} else if (status == BidStatus.YZF) {
		} else if (status == BidStatus.ZF) {
		} else if (status == BidStatus.YDF) {
		}

		// 是否逾期
		if (!bidExtend.getOverdue().equals("F")) {
			return 5;
		}
		return 100;
	}

	// 获取接口所需整型标还款方式
	public static Integer getBidMode(BidMode mode, BidPayMode payMode) {
		// 1 按月付息 到期还本
		// 2 按季付息,到期还本
		// 3 每月等额本息
		// 4 到期还本息
		// 5 按周等额本息还款
		// 6 按周付息,到期还本
		// 7:利随本清
		// 8:等本等息
		// 9:按日付息,到期还本
		// 10:按半年付息,到期还本
		// 11:按一年付息,到期还本
		// 100:其他方式

		// 等额本息
		if (mode == BidMode.DEBJ) {
			// 若为按月
			if (payMode == BidPayMode.ZRY) {
				return 3;
			} else if (payMode == BidPayMode.GDR) {
				return 100;// 设置为其他,因为接口这里没有可选的选项
			}
		}
		// 每月付息,到期还本
		else if (mode == BidMode.MYFX) {
			return 1;
		}
		// 本息到期一次付清
		else if (mode == BidMode.YCFQ) {
			return 4;
		}
		// 等额本金
		else if (mode == BidMode.DEBJ) {
			return 100;// 设置为其他,因为接口这里没有可选的选项
		}
		return 100;
	}
}