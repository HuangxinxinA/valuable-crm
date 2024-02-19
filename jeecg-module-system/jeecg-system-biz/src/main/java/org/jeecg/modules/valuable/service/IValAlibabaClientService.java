package org.jeecg.modules.valuable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.valuable.entity.ValAlibabaClient;

import java.util.Collection;

/**
 * @Description: 阿里客户表
 * @Author: jeecg-boot
 * @Date:   2024-01-19
 * @Version: V1.0
 */
public interface IValAlibabaClientService extends IService<ValAlibabaClient> {

    boolean saveBatch(Collection<ValAlibabaClient> entityList);
}
