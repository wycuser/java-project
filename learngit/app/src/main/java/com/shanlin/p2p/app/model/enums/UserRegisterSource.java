package com.shanlin.p2p.app.model.enums;



/** 
 * 注册来源
 */
public enum UserRegisterSource{


    /** 
     * 注册
     */
    ZC("注册"),

    /** 
     * 后台添加
     */
    HTTJ("后台添加");

    protected final String chineseName;

    private UserRegisterSource(String chineseName){
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
     * @return {@link USER_REGISTERED_SOURCE}
     */
//    public static final USER_REGISTERED_SOURCE parse(String value) {
//        if(StringHelper.isEmpty(value)){
//            return null;
//        }
//        try{
//            return USER_REGISTERED_SOURCE.valueOf(value);
//        }catch(Throwable t){
//            return null;
//        }
//    }
}
