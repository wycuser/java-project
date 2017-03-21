package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.model.enums.BidAssureMode;
import com.shanlin.p2p.app.model.enums.BidMode;
import com.shanlin.p2p.app.model.enums.BidPayMode;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.SurpriseStatus;

/**
 * 标信息
 * @author zheng xin
 * @createDate 2015年2月3日
 */
@Entity
@Table(name="T6230", schema="S62")
public class Bid implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 自增ID*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 借款用户*/
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F02")
	private UserAccount userAccount;
	
	/** 借款标题*/
	@Column(name="F03")
	private String title;
	
	/** 借款标类型ID,参考T6211.F01*/
	@Column(name="F04")
	private Long type;
	
	/** 借款金额*/
	@Column(name="F05")
	private BigDecimal amount;
	
	/** 年化利率*/
	@Column(name="F06")
	private BigDecimal rate;
	
	/** 可投金额*/
	@Column(name="F07")
	private BigDecimal residueAmount;
	
	/** 筹款期限,单位:天*/
	@Column(name="F08")
	private Integer findPeriod;
	
	/** 借款周期,单位:月*/
	@Column(name="F09")
	private Integer loanPeriod;
	
	/** 还款方式*/
	@Enumerated(EnumType.STRING)
	@Column(name="F10")
	private BidMode mode;
	
	/** 是否有担保*/
	@Enumerated(EnumType.STRING)
	@Column(name="F11")
	private Judge isAssure;
	
	/** 担保方案*/
	@Enumerated(EnumType.STRING)
	@Column(name="F12")
	private BidAssureMode assureMode;
	
	/** 是否有抵押*/
	@Enumerated(EnumType.STRING)
	@Column(name="F13")
	private Judge isPledge;
	
	/** 是否实地认证*/
	@Enumerated(EnumType.STRING)
	@Column(name="F14")
	private Judge isFieldWork;
	
	/** 是否自动放款*/
//	@Enumerated(EnumType.STRING)
//	@Column(name="F15")
//	private JUDGE isAuto;
	
	/** 是否允许流标*/
//	@Enumerated(EnumType.STRING)
//	@Column(name="F16")
//	private JUDGE isAllowFail;
	
	/** 付息方式,ZRY:自然月;GDR:固定日;*/
	@Enumerated(EnumType.STRING)
	@Column(name="F17")
	private BidPayMode payMode;
	
	/** 付息日 自然月在满标后设置为满标日+起息日,固定日则必须小于等于28*/
	@Column(name="F18")
	private Integer payDay;
	
	/** 起息天数,T+N,默认为0*/
	@Column(name="F19")
	private Integer valueDate;
	
	/** 状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F20")
	private BidStatus status;
	
	/** 发布时间,预发布状态有效*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F22")
	private Date publishTime;
	
	/** 申请时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F24")
	private Date createTime;
	
	/** 标编号*/
	@Column(name="F25")
	private String bidNumber;
	
	/** 标鼓励奖*/
	@Column(name="bonus")
	private BigDecimal bonus;
	
	/** 惊喜奖状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="surprise_status")
	private SurpriseStatus surpriseStatus;
	
//	@OneToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="F01")
//	private SimpleBidExtend extend;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F01")
	private BidExtend extend;
	
	@Column(name="investName")
	private String investName;
	
	/** 是否为善行宝*/
	@Enumerated(EnumType.STRING)
	@Column(name="isSxbao")
	private Judge isSxbao;
	
	/** 是否为代付*/
	@Enumerated(EnumType.STRING)
	@Column(name="isDfBid")
	private Judge isDfBid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sxbaoId")
	private SxbaoConfig sxbaoConfig;
	
	@Enumerated(EnumType.STRING)
	@Column(name="isActivity")
	private Judge isActivity;
	
	public Judge getIsDfBid() {
		return isDfBid;
	}

	public void setIsDfBid(Judge isDfBid) {
		this.isDfBid = isDfBid;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="isNovice")
	private Judge isNovice;
	
	public Judge getIsNovice() {
		return isNovice;
	}

	public void setIsNovice(Judge isNovice) {
		this.isNovice = isNovice;
	}

	public Judge getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Judge isActivity) {
		this.isActivity = isActivity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public BidExtend getExtend() {
		return extend;
	}

	public void setExtend(BidExtend extend) {
		this.extend = extend;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getResidueAmount() {
		return residueAmount;
	}

	public void setResidueAmount(BigDecimal residueAmount) {
		this.residueAmount = residueAmount;
	}

	public Integer getFindPeriod() {
		return findPeriod;
	}

	public void setFindPeriod(Integer findPeriod) {
		this.findPeriod = findPeriod;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public BidMode getMode() {
		return mode;
	}

	public void setMode(BidMode mode) {
		this.mode = mode;
	}

	public Judge getIsAssure() {
		return isAssure;
	}

	public void setIsAssure(Judge isAssure) {
		this.isAssure = isAssure;
	}

	public BidAssureMode getAssureMode() {
		return assureMode;
	}

	public void setAssureMode(BidAssureMode assureMode) {
		this.assureMode = assureMode;
	}

	public Judge getIsPledge() {
		return isPledge;
	}

	public void setIsPledge(Judge isPledge) {
		this.isPledge = isPledge;
	}

	public Judge getIsFieldWork() {
		return isFieldWork;
	}

	public void setIsFieldWork(Judge isFieldWork) {
		this.isFieldWork = isFieldWork;
	}

	public BidPayMode getPayMode() {
		return payMode;
	}

	public void setPayMode(BidPayMode payMode) {
		this.payMode = payMode;
	}

	public Integer getPayDay() {
		return payDay;
	}

	public void setPayDay(Integer payDay) {
		this.payDay = payDay;
	}

	public Integer getValueDate() {
		return valueDate;
	}

	public void setValueDate(Integer valueDate) {
		this.valueDate = valueDate;
	}

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBidNumber() {
		return bidNumber;
	}

	public void setBidNumber(String bidNumber) {
		this.bidNumber = bidNumber;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public SurpriseStatus getSurpriseStatus() {
		return surpriseStatus;
	}

	public void setSurpriseStatus(SurpriseStatus surpriseStatus) {
		this.surpriseStatus = surpriseStatus;
	}

//	public SimpleBidExtend getExtend() {
//		return extend;
//	}
//
//	public void setExtend(SimpleBidExtend extend) {
//		this.extend = extend;
//	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
//	private static final SimpleDateFormat format = new SimpleDateFormat("MM月dd日HH:mm");
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("title", this.title);
		map.put("rate", Formater.formatRate(this.rate, false));
		map.put("loanPeriod", this.extend.getIsByDay() == Judge.S?
				this.extend.getLoanPeriod().toString() + "天" : this.loanPeriod.toString() + "个月");
//		if(this.status == BidStatus.YFB)
//			map.put("amount", format.format(this.publishTime));
//		else
			map.put("amount", this.amount.doubleValue() > 10000? this.amount.divide(new BigDecimal(10000)) + "万" 
				: this.amount.setScale(0) + "元");
		map.put("mode", this.mode.getChineseName());
		map.put("safeType", this.isFieldWork == Judge.S? "实地认证":"资金托管");
		String statusTemp = null;
		String statusButtonTemp = null;
		BigDecimal statusNum = null;
		if(this.status == BidStatus.TBZ || this.status == BidStatus.DFK){
			if(new DateTime(this.publishTime).plusDays(this.findPeriod).getMillis() - System.currentTimeMillis() <= 0){
				statusTemp = "完";
				statusButtonTemp = "已完结";
			}else if(this.status == BidStatus.DFK){
				statusTemp = this.status.getSimpleName();
				statusButtonTemp = this.status.getChineseName();
			}else{
				statusNum = this.amount.subtract(this.residueAmount)
						.divide(this.amount, 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
				statusButtonTemp = "立即投资";
			}
		}else{
			statusTemp = this.status.getSimpleName();
			statusButtonTemp = this.status.getChineseName();
		}
		map.put("status", statusNum != null?statusNum:statusTemp);
		map.put("statusButton", statusButtonTemp);
		map.put("type", 0);
		map.put("investName", this.investName);
		return map;
	}
	
	@Override
	public String toString() {
		return "Bid [id=" + id + ", userId="  + ", title=" + title + ", type=" + type + ", amount=" + amount + ", rate=" + rate + ", residueAmount="
				+ residueAmount + ", findPeriod=" + findPeriod + ", loanPeriod=" + loanPeriod + ", mode=" + mode + ", isAssure=" + isAssure + ", assureMode="
				+ assureMode + ", isPledge=" + isPledge + ", isFieldWork=" + isFieldWork + ", payMode=" + payMode + ", payDay=" + payDay + ", valueDate="
				+ valueDate + ", status=" + status + ", publishTime=" + publishTime + ", createTime=" + createTime + ", bidNumber=" + bidNumber + ", bonus="
				+ bonus + ", surpriseStatus=" + surpriseStatus + ", extend=" + extend + "]";
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public Judge getIsSxbao() {
		return isSxbao;
	}

	public void setIsSxbao(Judge isSxbao) {
		this.isSxbao = isSxbao;
	}

	public SxbaoConfig getSxbaoConfig() {
		return sxbaoConfig;
	}

	public void setSxbaoConfig(SxbaoConfig sxbaoConfig) {
		this.sxbaoConfig = sxbaoConfig;
	}
	
}