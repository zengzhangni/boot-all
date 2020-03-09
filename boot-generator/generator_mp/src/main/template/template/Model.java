#set($domain=$!domainName.substring(0,1).toLowerCase()+$!domainName.substring(1))
package $!{packageName}.$!{entityPackageName};

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author $!{author}
 * @date $!{date}
 */
@Data
@TableName("$!{tablePrefix}$domain")
public interface $!{domainName} implements Serializable{

    #foreach($element in $list)
/**
     * $element.remarks
     */
    private $element.javaType $element.javaName;
    #end

 }
