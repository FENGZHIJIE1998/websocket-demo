package com.itaem.dazhi.websocketdemo.modules.websocket.vo;

import lombok.Data;

/**
 * @author Dazhi
 * @date 2019/4/25 20:45
 */
@Data
public class MessageVo {

    /**
     * 消息生产者ID
     */
    private String producerId;
    /**
     * 消息消费者者ID
     */
    private String consumerId;
    /**
     * 消息
     */
    private String message;
    /**
     * 生产者昵称
     */
    private String nickname;
}
