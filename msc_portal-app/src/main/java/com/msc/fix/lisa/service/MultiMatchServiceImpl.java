package com.msc.fix.lisa.service;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.MultiMatchService;
import com.msc.fix.lisa.domain.entity.Book;
import com.msc.fix.lisa.dto.IndexQry;
import com.msc.fix.lisa.dto.system.cto.BookCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 15:01
 * Description: No Description
 */
@Service
public class MultiMatchServiceImpl implements MultiMatchService {


    @Autowired
    private CommandBusI commandBusI;

    @Override
    public SingleResponse<List<BookCo>> multiMatch(String indexQry) {
        IndexQry qry = new IndexQry();
        qry.setIndexQry(indexQry);
        return (SingleResponse<List<BookCo>>) commandBusI.send(qry);
    }
}
