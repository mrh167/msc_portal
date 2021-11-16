package com.msc.fix.lisa.dto;

import com.msc.fix.lisa.base.AbstractCommand;
import com.msc.fix.lisa.base.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 15:08
 * Description: No Description
 */
@Data
public class IndexQry extends AbstractCommand {

    private String indexQry;
}
