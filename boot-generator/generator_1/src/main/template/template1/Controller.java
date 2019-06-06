#set ($domain = $!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.controller;

import $!{packageName}.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author $!{author}
 * @date $!{date}
 */
@RestController
@RequestMapping(value = "$!{domain}")
public class $!{domainName}Controller {

    @Resource
    private $!{domainName}Service $!{domain}Service;



}
