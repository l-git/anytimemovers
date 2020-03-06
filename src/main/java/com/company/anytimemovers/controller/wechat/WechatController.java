package com.company.anytimemovers.controller.wechat;


import com.company.anytimemovers.model.ResTextMessage;
import com.company.anytimemovers.utils.WeChatMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by admin
 */

@RestController
public class WechatController {

    private static final Logger logger= LoggerFactory.getLogger(WechatController.class);

    private static String token="token";

    //  curl "localhost/wechat/checkSignature?signature=s&timestamp=t&nonce=n&echostr=e"
    //      /wechat/checkSignature
    @GetMapping("/wechat/checkSignature")
    public Object checkSignature(String signature,String timestamp,String nonce,String echostr){
        logger.info("checkSignature signature {} timestamp {} nonce {} echostr {} ",signature,timestamp,nonce,echostr);
        String returnStr="a";
        try {
            String[] arr = new String[]{token, timestamp, nonce};
            Arrays.sort(arr);
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                content.append(arr[i]);
            }
            MessageDigest md = null;
            String tmpStr = null;

            try {
                md = MessageDigest.getInstance("SHA-1");
                byte[] digest = md.digest(content.toString().getBytes());
                tmpStr = byteToStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if (tmpStr.equals(signature.toUpperCase())) {
                returnStr=echostr;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("returnStr {}",returnStr);
        return returnStr;

    }

    @PostMapping("/wechat/checkSignature")
    public Object post(HttpServletRequest httpServletRequest){
        String body="";
        logger.info("body {}\n",body);

        Map<String,String> map= null;
        try {
            map = WeChatMessageUtils.parseXml(httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ToUserName=map.get("ToUserName");
        String FromUserName=map.get("FromUserName");
        String CreateTime=map.get("CreateTime");
        String MsgType=map.get("MsgType");
        String Content=map.get("Content");
        String MsgId=map.get("MsgId");
        System.out.println("\nreceive from client:");
        logger.info("ToUserName:" + ToUserName);
        logger.info("FromUserName:"+FromUserName);
        logger.info("CreateTime:"+CreateTime);
        logger.info("MsgType:"+MsgType);
        logger.info("Content:"+Content);
        logger.info("MsgId:"+MsgId);
        System.out.println("\nend........................................");


        ResTextMessage resTextMessage = new ResTextMessage();
        resTextMessage.setToUserName(FromUserName);
        resTextMessage.setFromUserName(ToUserName);
        resTextMessage.setCreateTime(new Date().getTime());
        resTextMessage.setMsgType(WeChatMessageUtils.REQ_MESSAGE_TYPE_TEXT);
        resTextMessage.setContent(Content);

        String str = WeChatMessageUtils.textMessageToXml(resTextMessage);

        logger.info("response \n{}",str);
        return str;
    }


    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
