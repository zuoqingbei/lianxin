package com.hailian.modules.credit.utils;

import java.util.UUID;

/**
 * @author zuoqb
 * @since on 2018/5/8.
 */
public class GenerationSequenceUtil {

    
    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
}
