package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.RedPacket;

public interface RedPacketDao extends BaseDao<RedPacket, Long>{

	@Query("select c from RedPacket c where c.source=?1 and c.type=?2 and c.state=1")
	RedPacket[] getList(int source,int type);
}
