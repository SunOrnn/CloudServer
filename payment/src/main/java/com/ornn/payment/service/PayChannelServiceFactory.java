package com.ornn.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayChannelServiceFactory {
    @Autowired
    private PayChannelService aliPayChannelService;

    public PayChannelService createPayChannelService(int channelName) {
        switch (channelName) {
            case 1 :
                return aliPayChannelService;
            default:
                return null;
        }
    }
}
