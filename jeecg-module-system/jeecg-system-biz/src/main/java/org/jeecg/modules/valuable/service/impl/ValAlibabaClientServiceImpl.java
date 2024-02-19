package org.jeecg.modules.valuable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import org.jeecg.modules.valuable.entity.ValAlibabaClient;
import org.jeecg.modules.valuable.mapper.ValAlibabaClientMapper;
import org.jeecg.modules.valuable.service.IValAlibabaClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: 阿里客户表
 * @Author: jeecg-boot
 * @Date: 2024-01-19
 * @Version: V1.0
 */
@Service
public class ValAlibabaClientServiceImpl extends ServiceImpl<ValAlibabaClientMapper, ValAlibabaClient> implements IValAlibabaClientService {

    @Autowired
    private ValAlibabaClientMapper valAlibabaClientMapper;

    @Override
    public boolean saveBatch(Collection<ValAlibabaClient> entityList) {
        List<String> accountList = entityList.stream().map(ValAlibabaClient::getAccount).collect(Collectors.toList());
        LambdaQueryWrapper<ValAlibabaClient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ValAlibabaClient::getAccount, accountList);
        List<ValAlibabaClient> repeatData = valAlibabaClientMapper.selectList(queryWrapper);
        List<String> repeatAccount = repeatData.stream().map(ValAlibabaClient::getAccount).collect(Collectors.toList());
        List<ValAlibabaClient> save = entityList.stream().filter(obj -> !repeatAccount.contains(obj.getAccount())).collect(Collectors.toList());
        if (save.isEmpty()) {
            return true;
        }
        String sqlStatement = this.getSqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, save.size(), (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }
}
