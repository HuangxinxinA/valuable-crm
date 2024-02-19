package org.jeecg.modules.valuable.vo;

import lombok.Data;
import org.jeecg.modules.valuable.entity.ValAlibabaClient;

@Data
public class ValAlibabaClientVO extends ValAlibabaClient {
    private String managerName;
    private String operatorName;
}