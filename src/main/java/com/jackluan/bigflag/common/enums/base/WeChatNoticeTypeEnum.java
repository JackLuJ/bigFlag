package com.jackluan.bigflag.common.enums.base;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 0:05
 */
public enum WeChatNoticeTypeEnum implements KeyValueEnum<Integer> {
    /**
     * 提醒打卡
     */
    NOTICE_SIGN(1,"notice sign", "BcSGizAcCDNsjaDwXwzThg8c0LS1aBQOp1zTu_h-WEI", "/pages/detail/index?flagId=${flagId}"),

    /**
     * 凭证被审核
     */
    SIGN_CHECKED(2,"sign checked", "Y6QYNmW_lRqkVBrTrFTUMgYN67i_vHvUVZan0_O9gQs", "/pages/proof-detail/index?flagId=${flagId}&signId=${signId}"),

    /**
     * 凭证待审核提醒
     */
    SERVER_STATUS(3,"server status", "GXHcsyJ_SxYffm20wiADKOALUncT6hIt0-6HE6rmOW0", "/pages/proof-detail/index?flagId=${flagId}&signId=${signId}"),

    /**
     * 邀请监督人成功
     */
    INVITE_SUCCESSFUL(4,"invite successful", "86t1zRoF0Ogad-C1w2vDWUlCYyqBemN6PBDTC9LLz-c", "/pages/detail/index?flagId=${flagId}"),

    /**
     * 审核凭证提醒
     */
    UNDER_APPROVE(5,"under approve", "jjlWZBcXqrpDJgzLTkJuTvx7hgE7QJukYPu5a0Ik6Qg", "/pages/proof-detail/index?flagId=${flagId}&signId=${signId}"),

    /**
     * 监督者绑定成功
     */
    BIND_SUCCESS(6,"bind success", "clGIJAklP3Psn74xLhTCOJrugTBPx7uc4CsXW99he8c", "/pages/detail/index?flagId=${flagId}");

    private Integer code;

    private String desc;

    private String templateId;

    private String path;

    WeChatNoticeTypeEnum(Integer code, String desc, String templateId, String path) {
        this.code = code;
        this.desc = desc;
        this.templateId = templateId;
        this.path = path;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getPath() {
        return path;
    }
}
