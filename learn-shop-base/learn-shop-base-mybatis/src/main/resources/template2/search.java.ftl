<#assign SP = table.entityName?substring(0,(table.entityName)?length-2) + "SearchParam">
<#assign Parent = (package.Entity?substring(0,(package.Entity)?length-8))>
package ${Parent}.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ${table.comment!} 信息
 * </p>
 *
 * @author ${author}
 * @version v1.0
 * @since ${date}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ${SP} extends BasePage implements Serializable {

}
