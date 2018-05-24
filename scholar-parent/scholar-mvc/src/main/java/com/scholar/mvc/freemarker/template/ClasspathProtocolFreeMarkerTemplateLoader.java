package com.scholar.mvc.freemarker.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import com.scholar.common.comstant.CommonConstant;
import com.scholar.mvc.properties.GlobalProperties;

import freemarker.cache.TemplateLoader;

/**
 * classpath模板加载器
 * 
 * @author yiealen
 *
 */
public class ClasspathProtocolFreeMarkerTemplateLoader implements TemplateLoader {
    public static final String PROTOCOL = CommonConstant.CLASS_PATH_PREFIX;
    //是否关闭自动国际化
    private final boolean INTERNATION = GlobalProperties.INTERNATION;
    private final Map<String, StringTemplateSource> templates;
    private boolean cache;

    public ClasspathProtocolFreeMarkerTemplateLoader() {
        this.templates = new HashMap<String, StringTemplateSource>();

        this.cache = false;
    }

    public boolean isCache() {
        return this.cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public void closeTemplateSource(Object templateSource) throws IOException {}

    public Object findTemplateSource(String name) throws IOException {
        int stub = name.indexOf("_");
        if(INTERNATION && stub > -1){
            name = name.substring(0, stub) + GlobalProperties.FREEMARKER_SUFFIX;
        }
        StringTemplateSource tpl = (StringTemplateSource) this.templates.get(name);
        String oldName = name;
        if ((tpl != null) && (this.cache)) {
            return tpl;
        }
        name = name.trim();
        if (!name.startsWith(PROTOCOL) && !name.contains(PROTOCOL)) {
            return null;
        }
        int indexOfPROTOCOL = name.indexOf(PROTOCOL);
        if (!name.startsWith(PROTOCOL) && name.charAt(indexOfPROTOCOL - 1) == '/') {//只要包含claspath: 就是项目路径的模板
            name = StringUtils.substring(name, indexOfPROTOCOL);
        }
        if (name.charAt(PROTOCOL.length()) == '/') {
            name = StringUtils.replaceOnce(name, "/", "");
        }
        URL url = ResourceUtils.getURL(name);
        if (url != null) {
            String content = IOUtils.toString(url.openStream(), "utf-8");
            tpl = new StringTemplateSource(name, content, System.currentTimeMillis());
        }
        this.templates.put(oldName, tpl);
        return tpl;
    }

    public long getLastModified(Object templateSource) {
        StringTemplateSource tps = (StringTemplateSource) templateSource;
        if (!this.cache) {
            return tps.getLastModified();
        }
        return Long.MAX_VALUE;
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader(((StringTemplateSource) templateSource).source);
    }

    private static class StringTemplateSource {
        private final String name;
        private final String source;
        private final long lastModified;

        public long getLastModified() {
            return this.lastModified;
        }

        StringTemplateSource(String name, String source, long lastModified) {
            if (name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            this.lastModified = lastModified;
            this.name = name;
            this.source = source;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof StringTemplateSource)) {
                return this.name.equals(((StringTemplateSource) obj).name);
            }
            return false;
        }

        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
