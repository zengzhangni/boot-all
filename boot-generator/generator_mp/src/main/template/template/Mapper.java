#set ($domain = $!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * @author $!{author}
 * @date $!{date}
 */
public interface $!{domainName}Mapper extends BaseMapper<$!{domainName}>{

}
