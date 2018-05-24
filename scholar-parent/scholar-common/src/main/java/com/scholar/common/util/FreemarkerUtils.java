package com.scholar.common.util;

import java.io.StringWriter;
import java.util.Map;

import com.scholar.common.comstant.CharsetConstant;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class FreemarkerUtils {
    /**freemarker版本*/
    private static final Version FREEMARKER_VERSION = Configuration.VERSION_2_3_26;
    
    /**
     * 获取freemarker模板
     * @param templateName 模板名称
     * @param templateContent 模板内容
     * @return
     */
    public static Template getTemplate(String templateName, String templateContent){
        Configuration configuration = new Configuration(FREEMARKER_VERSION);
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(templateName, templateContent);
        configuration.setTemplateLoader(loader);
        Template template = null;
        try {
            template = configuration.getTemplate(templateName, CharsetConstant.UTF_8);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }
    
    /**
     * 解析freemarker模板
     * @param template 要解析的freemarker模板
     * @param valueMap 用于解析模板的参数
     * @return
     */
    public static String processTemplate(Template template,Map<Object, Object> valueMap){
         StringWriter writer = new StringWriter();    
         try {  
             template.process(valueMap, writer);  
             System.out.println(writer.toString());    
         } catch (Exception e) {  
             // TODO Auto-generated catch block  
             e.printStackTrace();  
         }
         return writer.toString();
    }
}
