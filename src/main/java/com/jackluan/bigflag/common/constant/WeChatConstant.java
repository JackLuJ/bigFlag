package com.jackluan.bigflag.common.constant;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: jack.luan
 * @Date: 2020/3/31 0:35
 */
public class WeChatConstant {
    public static final String OA = "OA";
    public static final String APP = "APP";

    public static final Object REQ_MESSAGE_TYPE_EVENT = "event";
    public static final Object EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final Object EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    public static final String FROM_USER_NAME = "FromUserName";
    public static final String TO_USER_NAME = "ToUserName";
    public static final String CREATE_TIME = "CreateTime";
    public static final String MSG_TYPE = "MsgType";
    public static final String CONTENT = "Content";
    public static final String EVENT = "Event";

    public static final String KEY_ALGORITHM = "AES";
    public static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";

    public static final String MSG_TYPE_NAME = "MsgType";
    public static final String APP_ID_NAME = "AppId";
    public static final String APP_FROM_USER_NAME = "FromUserName";

    public static final String APP_TEXT_MSG_DATA_JSON = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"长按扫码关注公众号获得flag打卡提示\"}}";
    public static final String APP_PHOTO_MSG_DATA_JSON = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\": {\"media_id\":\"PuuZPcNJVlbRlBjaqShw1cir3Fj20jM38xX3FFco2eq7aMKkUGcfFIpU6LSh4gqJ\"}}";
    public static final String APP_PHOTO_TEXT_MSG_DATA_JSON = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":[{\"title\":\"长按扫码关注公众号获得flag打卡提示\",\"description\":\"长按扫码关注公众号获得flag打卡提示\",\"url\":\"\",\"picurl\":\"%s\"}]}}";

}
