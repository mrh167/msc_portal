package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.AbstractCommand;
import com.msc.fix.lisa.dto.DataKey;
import com.msc.fix.lisa.dto.DictTypeKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 14:36
 * Description: No Description
 */
@Data
public class SysDictTypeDeleteCmd extends AbstractCommand {
    /**
     * key集合
     */
    @ApiModelProperty(value = "key集合", required = true)
    @NotNull(message = "keyList参数为null")
    @Size(min = 1, message = "keyList参数为空")
    private List<DictTypeKey> keyList;

}
