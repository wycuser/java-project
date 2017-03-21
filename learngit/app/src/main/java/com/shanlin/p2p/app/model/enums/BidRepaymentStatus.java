package com.shanlin.p2p.app.model.enums;

/** 
 * 标的还款状态
 */
public enum BidRepaymentStatus{


    /** 
     * 未还
     */
    WH("未还"),

    /** 
     * 还款中
     */
    HKZ("还款中"),

    /** 
     * 已还
     */
    YH("已还");

    protected final String chineseName;

    private BidRepaymentStatus(String chineseName){
        this.chineseName = chineseName;
    }
    /**
     * 获取中文名称.
     * 
     * @return {@link String}
     */
    public String getChineseName() {
        return chineseName;
    }
    /**
     * 解析字符串.
     * 
     * @return {@link BID_REPAYMENT_STATUS}
     */
//    public static final BID_REPAYMENT_STATUS parse(String value) {
//        if(StringHelper.isEmpty(value)){
//            return null;
//        }
//        try{
//            return BID_REPAYMENT_STATUS.valueOf(value);
//        }catch(Throwable t){
//            return null;
//        }
//    }
}
