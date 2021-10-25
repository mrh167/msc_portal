package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.AbstractCommand;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/10/11
 * Time: 10:11
 * Description: No Description
 */
@Data
public class UpdateStatusDataTypeCmd extends AbstractCommand {

    private Long id;

    private Integer status;

}
