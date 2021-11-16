package com.msc.fix.lisa.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msc.fix.lisa.api.MultiMatchService;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.domain.entity.Book;
import com.msc.fix.lisa.dto.system.SysUserCoooo;
import com.msc.fix.lisa.dto.system.cto.BookCo;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import com.msc.fix.lisa.utils.EsUtil;
import com.msc.fix.lisa.utils.FastJsonTools;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/31
 * Time: 18:44
 * Description: No Description
 */
@Slf4j
@Api(tags = "RestController", hidden = true)
@RestController
@RequestMapping(value = "/api/testEsData")
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private Client client;

    @Autowired
    private MultiMatchService multiMatchService;


    @PostMapping(value = "/register")
    public SingleResponse<SysUserCo> register(@RequestBody SysUserCoooo sysUser){
//        SysUser user = new SysUser();
//        user.setUsername(sysUser.getUsername());
//        String pwd = passwordEncoder.encode(sysUser.getPassword());
//        user.setPassword(pwd);
//        user.setAccount(sysUser.getAccount());
//        user.setEmail(sysUser.getEmail());
//        user.setEnabled(sysUser.getEnabled() ? 0 : 1);
//        user.setPhone(sysUser.getPhone());
//        user.setCreateUser("JD_SCM");
//        user.setCreateTime(new Date());
//        user.setUpdateUser("JD_SCM");
//        user.setUpdateTime(new Date());
//        user.setYn(1);
//        user.initCreate(sysUser.getAccount());
//        SysUserDo userDo = BeanUtils.convert(user, SysUserDo.class);
//        CommonUtil.fillByCreate(new Date(),sysUser.getAccount(),userDo);
//        sysUserMapper.insert(user);
       return SingleResponse.buildSuccess();
    }

    /**
     * 获取所有索引信息
     * @return
     */
    @PostMapping("/findAllIndex2")
    public SingleResponse<List<String>> findAllIndex2() {
        try {
            // 构建请求,注意*的写法
            GetIndexRequest getIndexRequest = new GetIndexRequest();
            getIndexRequest.indices("*");
            // 构建获取所有索引的请求
            GetIndexResponse getIndexResponse = client.admin().indices().prepareGetIndex().get();
            // 获取所有索引
            String[] indices = getIndexResponse.getIndices();
            List<String> asList = Arrays.asList(indices);
            return SingleResponse.of(new ArrayList<>(asList));
        } catch (Exception e) {
            throw new BusinessException("获取所有索引失败");
        }
    }

    @GetMapping("/multiMatch")
    public SingleResponse<List<BookCo>> multiMatch(@RequestParam(value = "indexQry",required = true) String indexQry){
        return multiMatchService.multiMatch(indexQry);
    }

    @GetMapping("/matchAllQuery")
    public SingleResponse<List<Object>> matchAllQuery(@RequestParam(value = "index",required = true) String index){

        MatchAllQueryBuilder allQuery = QueryBuilders.matchAllQuery();
        SearchResponse searchResponse = client.prepareSearch(index).setQuery(allQuery).execute().actionGet();
        List<String> stringList = EsUtil.convertResponses(searchResponse, String.class);
        JSONArray jsonArray = JSONObject.parseArray(stringList.toString());
        List<Object> listArrays = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonIndex = (JSONObject) jsonArray.get(i);
            listArrays.add(jsonIndex);
        }
        return SingleResponse.of(listArrays);
    }
}
