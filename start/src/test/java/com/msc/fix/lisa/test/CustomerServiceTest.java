package com.msc.fix.lisa.test;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msc.fix.lisa.Application;
import com.msc.fix.lisa.api.CustomerServiceI;
import com.msc.fix.lisa.common.BizCode;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.CustomerAddCmd;
import com.msc.fix.lisa.dto.domainmodel.Customer;
import com.msc.fix.lisa.dto.domainmodel.ErrorCode;
import com.msc.fix.lisa.gateway.SysUserGateWayImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is for integration test.
 *
 * Created by fulan.zjf on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CustomerServiceTest extends SysUserGateWayImpl {

    @Autowired
    private CustomerServiceI customerService;


    @Test
    public void fun1(){
        List<Integer> list = new ArrayList<>();
        list.add(25);
        list.add(1);
        list.add(24);
        list.add(21);
        List<Integer> collect = list.stream().sorted().collect(Collectors.toList());
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.in("id",collect);
        List<SysUser> list1 = baseMapper.selectList(wrapper);
        for (SysUser sysUser : list1) {
            System.out.println(sysUser.toString());
        }
    }


    @Before
    public void setUp() {

    }

    @Test
    public void testCustomerAddSuccess(){
        //1.prepare
        CustomerAddCmd customerAddCmd = new CustomerAddCmd();
        Customer customer = new Customer();
        customer.setCompanyName("NormalName");
        customerAddCmd.setCustomer(customer);

        //2.execute
        Response response = customerService.addCustomer(customerAddCmd);

        //3.assert
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testCustomerAddCompanyNameConflict(){
        //1.prepare
        CustomerAddCmd customerAddCmd = new CustomerAddCmd();
        Customer customer = new Customer();
        customer.setCompanyName("ConflictCompanyName");
        customerAddCmd.setCustomer(customer);

        //2.execute
        Response response = customerService.addCustomer(customerAddCmd);

        //3.assert
        Assert.assertEquals(ErrorCode.B_CUSTOMER_companyNameConflict.getErrCode(), response.getErrCode());

    }







}
