#set ($domain = $!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.service.impl;


import $!{packageName}.$!{entityPackageName}.User;
import $!{packageName}.mapper.UserMapper;
import $!{packageName}.service.UserService;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author $!{author}
 * @date $!{date}
 */
@Service
public class $!{domainName}ServiceImpl extends ServiceImpl<$!{domainName}Mapper, $!{domainName}> implements $!{domainName}Service{
	@Resource
	private $!{domainName}Mapper $!{domain}Mapper;


}

