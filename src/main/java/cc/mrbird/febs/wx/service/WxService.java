package cc.mrbird.febs.wx.service;

import cc.mrbird.febs.wx.entity.ResultEntity;

/**
 * @author MrBird
 */
public interface WxService {


    ResultEntity payReward(String cId, String openId, String reward,String ip,String userName);
}
