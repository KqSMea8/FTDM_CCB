package com.sunyard.sunfintech.billcheck.modulepublic;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.pub.model.TransferMQModel;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 2018/2/3.
 */
@Service
@CacheConfig(cacheNames = "transferService")
public class TransferService extends BaseServiceSimple implements ITransferService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Override
    public void transfer(BaseRequest baseRequest, List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
        if(baseRequest==null || eaccAccountamtlists==null || eaccAccountamtlists.size()<=0 ||
                StringUtils.isBlank(eaccAccountamtlists.get(0).getOrder_no()) || StringUtils.isBlank(eaccAccountamtlists.get(0).getTrans_serial())){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
        }
        TransferMQModel notifyTransferMQModel=new TransferMQModel(baseRequest,eaccAccountamtlists);
        MQUtils.send(amqpTemplate, "ftdm.transfer.direct.exchange", "TransferQueue", notifyTransferMQModel);
    }
    @Override
    public void transfer(BaseRequest baseRequest, EaccAccountamtlist eaccAccountamtlists) throws BusinessException {
        List<EaccAccountamtlist> eaccAccountamtlistList=new ArrayList<>();
        eaccAccountamtlistList.add(eaccAccountamtlists);
        transfer(baseRequest,eaccAccountamtlistList);
    }
}
