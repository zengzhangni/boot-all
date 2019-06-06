#set ($domain = $!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.service.impl;


import $!{packageName}.base.User;
import $!{packageName}.mapper.UserMapper;
import $!{packageName}.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 * @author $!{author}
 * @date $!{date}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class $!{domainName}ServiceImpl implements $!{domainName}Service{
	@Resource
	private $!{domainName}Mapper $!{domain}Mapper;

	public int save($!{domainName} $!{domain}) {
		return $!{domain}Mapper.save($!{domain});
	}

	public int delete($!{domainName} $!{domain}) {
		return $!{domain}Mapper.delete($!{domain});
	}

	public int update($!{domainName} $!{domain}) {
		return $!{domain}Mapper.update($!{domain});
	}

	public $!{domainName} findById(int id) {
		return $!{domain}Mapper.findById( id);
	}
}

