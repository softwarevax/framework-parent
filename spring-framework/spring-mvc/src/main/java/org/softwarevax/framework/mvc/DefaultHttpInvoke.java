package org.softwarevax.framework.mvc;

import com.alibaba.fastjson.JSON;
import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.rpc.entity.MethodEntity;
import org.softwarevax.framework.rpc.entity.ServiceConfig;
import org.softwarevax.framework.rpc.entity.ServiceEntity;
import org.softwarevax.framework.rpc.protocol.http.Constants;
import org.softwarevax.framework.rpc.protocol.http.HttpInvoke;
import org.softwarevax.framework.rpc.protocol.http.RequestEntity;
import org.softwarevax.framework.utils.ClassUtils;
import org.softwarevax.framework.utils.ObjectUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultHttpInvoke implements HttpInvoke {

    private List<MethodHandler> handlers;

    private ApplicationContext ctx;

    public DefaultHttpInvoke(List<MethodHandler> handlers, ApplicationContext ctx) {
        this.handlers = handlers;
        this.ctx = ctx;
    }

    /**
     * @param entity 接口传进的参数
     * @return
     */
    @Override
    public Object invoke(RequestEntity entity) {
        if(StringUtils.equalsIgnore(entity.getUri(), Constants.RPC_INVOKE)) {
            try {
                ServiceConfig config = JSON.parseObject(entity.getBody().toString(), ServiceConfig.class);
                ServiceEntity serviceEntity = config.getServices().get(0);
                MethodEntity methodEntity = serviceEntity.getMethods().get(0);
                Object bean = ctx.getBean(ClassUtils.loadClass(serviceEntity.getInterfaceClazz().get(0)));
                Object[] args = methodEntity.getParameterVal() == null ? new Object[] {} : methodEntity.getParameterVal().toArray(new String[0]);
                Object[] types = methodEntity.getParameterType() == null ? new Object[] {} : methodEntity.getParameterType().toArray(new String[0]);
                return ObjectUtils.invoke(bean, ObjectUtils.getMethod(bean, methodEntity.getMethodName(), types), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Iterator<MethodHandler> iterator = handlers.iterator();
        while (iterator.hasNext()) {
            // 接口方法
            MethodHandler handler = iterator.next();
            if(StringUtils.equals(handler.getUrl(), entity.getUri())) {
                // controller实例
                Object bean = ctx.getBean(handler.getClazz());
                Method method = handler.getMethod();
                List<String> parameters = handler.getParameters();
                List<Parameter> parameterType = Arrays.asList(method.getParameters());
                Object[] parameterVal = new Object[parameters.size()];
                for (int i = 0; i < parameters.size(); i++) {
                    // 1、queryString
                    Map<String, String> queryString = entity.getQueryString();
                    if(queryString != null && queryString.size() > 0) {
                        parameterVal[i] = ObjectUtils.firstNotNull(queryString.get(parameters.get(i)));
                    }
                    // 2、body-json/form-data
                    Map<String, String> body = (Map<String, String>) entity.getBody();
                    if(body != null && body.size() > 0) {
                        parameterVal[i] = ObjectUtils.firstNotNull(body.get(parameters.get(i)));
                    }
                    if(!ObjectUtils.isSimpleType(parameterType.get(i).getType()) && StringUtils.equalsIgnore(entity.getContentType(), Constants.JSON_CONTENT_TYPE_VAL)) {
                        parameterVal[i] = JSON.parseObject(entity.getBody().toString(), parameterType.get(i).getType());
                    }
                }
                return ObjectUtils.invoke(bean, method, parameterVal);
            }
        }
        return null;
    }
}
