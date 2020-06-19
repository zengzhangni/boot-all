#set($domain=$!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author $!{author}
 * @date $!{date}
 */
public interface $!{domainName}Service extends IService<$!{domainName}>{
        }
