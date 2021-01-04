package com.letschat.master.common.dispatcher;

import com.letschat.master.common.dispatcher.Message;
import com.letschat.master.common.dispatcher.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName MessageHandlerContainer.java
 * @createTime 2021年01月04日 14:40:00
 */
@Slf4j
public class MessageHandlerContainer implements InitializingBean {

    private final Map<String, MessageHandler> handlers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> handlers.put(messageHandler.getType(),messageHandler));
        log.info("消息处理器的数量 [{}]",handlers.size());
    }

    /**
     * 获得类型对应的 MessageHandler
     * @param type
     * @return
     */
    MessageHandler getMessageHandler(String type){
        MessageHandler messageHandler = handlers.get(type);
        if(null == messageHandler)
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 MessageHandler 处理器", type));
        return messageHandler;
    }

    static Class<? extends Message> getMessageClass(MessageHandler handler){
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的Type数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superClass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0==interfaces.length) && Objects.nonNull(superClass)){
            // 此处，是以父类的接口为准
            interfaces = superClass.getGenericInterfaces();
            superClass = targetClass.getSuperclass();
        }
        if(Objects.nonNull(interfaces)){
            for (Type type: interfaces) {
                // 要求是泛型参数
                if(type instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是messageHandler接口
                    if(Objects.equals(parameterizedType.getRawType(),MessageHandler.class)){
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if(Objects.nonNull(actualTypeArguments) && actualTypeArguments.length>0){
                            return ((Class<Message>) actualTypeArguments[0]);
                        }else{
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }
}
