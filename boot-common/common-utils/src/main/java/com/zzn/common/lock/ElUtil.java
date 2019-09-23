package com.zzn.common.lock;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author zengzhangni
 * @date 2019/9/17
 */
public class ElUtil {

    /**
     * 用于SpEL表达式解析.
     */
    private static SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private static DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    public static String generateKeyBySpEL(String elstr, Method method, Object[] args) {
        if (!elstr.contains("#")) {
            return elstr;
        }
        // 使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        // 解析过后的Spring表达式对象
        Expression expression = parser.parseExpression(elstr);
        // spring的表达式上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        // 获取被注解方法的形参 给上下文赋值
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        if (expression.getValue(context).toString().trim().length() == 0) {
            throw new NullPointerException("lock key is null");
        }
        // 表达式从上下文中计算出实际参数值
        return expression.getValue(context).toString();
    }

}
