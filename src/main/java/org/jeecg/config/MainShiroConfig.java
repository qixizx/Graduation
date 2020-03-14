package org.jeecg.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.jeecg.modules.shiro.authc.aop.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Scott
 * @date: 2018/2/7
 * @description: shiro 配置类
 */


@Configuration
public class MainShiroConfig extends ShiroConfig {

	/**
	 * Filter Chain定义说明
	 *
	 * 1、一个URL可以配置多个Filter，使用逗号分隔
	 * 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		Map<String, String> filterChainDefinitionMap= super.mainShiroFilter();

		filterChainDefinitionMap.put("/aquarius/userInfo/register", "anon");
//		filterChainDefinitionMap.put("/aquarius/label/click", "anon");//标签点击量增加
//		filterChainDefinitionMap.put("/aquarius/label/allTreeList", "anon");//父子标签

//		filterChainDefinitionMap.put("/aquarius/activityInfo/labelActive", "anon");//标签点击

		filterChainDefinitionMap.put("/aquarius/activityInfo/addressList", "anon");//地址查询
		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
		filterMap.put("jwt", new JwtFilter());
		shiroFilterFactoryBean.setFilters(filterMap);
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		filterChainDefinitionMap.put("/**", "jwt");

		// 未授权界面返回JSON
		shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
		shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}



}
