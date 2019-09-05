package com.zzn.usercenter.utils.generatedcodeutils;

import java.util.Random;
import java.util.UUID;

/**
 * @author liyang
 * @version 1.0 2019年3月7日
 */
public class CreateNo {

	private SnowflakeIdWorkerTwo idWorker = null;
    /**
     * 私有构造器
     */
    private CreateNo(){
    	idWorker = new SnowflakeIdWorkerTwo(new Random().nextInt(1023) + 1);
    };

    private static CreateNo order=null;

    /**
     * 单例模式--懒汉模式
     * @return
     */
    public static synchronized CreateNo getInstance() {
        if (order == null) {
            order = new CreateNo();
        }
        return order;
    }


   /**
    * 
    * @param pre 前缀
    * @param size 生成位数(除前缀)，1~50之间
    * @return
    */
    public String GenerateNo(String pre,int size) {
    	
    	if(size > 50 || size < 1)
    		return pre;
        StringBuilder sb=new StringBuilder();
        if(size > 32)
        	sb.append(idWorker.nextId());
        sb.append(UUID.randomUUID().toString().replace("-", ""));
        return pre + sb.toString().toUpperCase().substring(sb.toString().length()-size);
    }

    /**
     * 编号转换成生成时间
     * @param no
     * @return
     */
    public String convert(String no)
    {
    	long id = Long.parseLong(no.substring(no.length() -17));
    	return idWorker.transTimeToFormat(idWorker.expId(id)[2]);
    }

//    public static void main(String[] args) {
//    	for (int i = 0; i < 1; i++) {
//    		System.out.println(CreateNo.getInstance().GenerateNo("1",0));
//		}
////    	System.out.println(CreateNo.getInstance().convert("72942599898820608"));
//    }

}
