#set($domain=$!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.service;

import $!{packageName}.base.User;


/**
 * @author $!{author}
 * @date $!{date}
 */
public interface $!{domainName}Service{
        int save($!{domainName} $!{domain});

        int delete($!{domainName} $!{domain});

        int update($!{domainName} $!{domain});

        $!{domainName} findById(int id);
        }
