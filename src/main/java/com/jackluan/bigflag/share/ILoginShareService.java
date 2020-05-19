package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.LoginShareResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 15:57
 */
@RequestMapping(value = "/base")
public interface ILoginShareService {

    /**
     * login
     *
     * @param loginShareRequestDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResultBase<LoginShareResponseDto> login(@RequestBody LoginShareRequestDto loginShareRequestDto);

    /**
     * refreshToken
     *
     * @param loginShareRequestDto
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    ResultBase<LoginShareResponseDto> refreshToken(@RequestBody LoginShareRequestDto loginShareRequestDto);

    /**
     * listen weChat
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "/listen/wx", method = RequestMethod.GET)
    String validate(@RequestParam(value = "signature") String signature, @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce, @RequestParam(value = "echostr") String echostr);

    /**
     * operate msg
     * @param request
     * @return
     */
    @RequestMapping(value = "/listen/wx", method = RequestMethod.POST)
    String processMsg(HttpServletRequest request);


    @RequestMapping(value = "/listen/wx/app", method = RequestMethod.GET)
    String validateApp(@RequestParam(value = "signature") String signature, @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce, @RequestParam(value = "echostr") String echostr);

    /**
     * operate msg
     * @param request
     * @return
     */
    @RequestMapping(value = "/listen/wx/app", method = RequestMethod.POST)
    String processMsgApp(HttpServletRequest request);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    ResultBase<String> test();
}
