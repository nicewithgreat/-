//index.js
//获取应用实例
var app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    wechatNO:'',
    team:false
   // avatarUrl:''
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    this.checkproperty()
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true,
        //wechatNO:app.globalData.wechatNO,
        //avatarUrl:app.globalData.avatarUrl
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  //获取用户信息
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  //跳转查看个人信息界面
  me:function(){
    var list=this.data.playlist
        wx.navigateTo({
            url: '/pages/me/me'
        })
  },
  //跳转查看历史订场信息界面
  history:function(){
    this.getMyHistoryInfo()
    var list=this.data.playlist
        wx.navigateTo({
            url: '/pages/history/history'
        })
  },
  //获取用户历史订场信息
  getMyHistoryInfo:function(){
    wx.request({
      url: 'http://127.0.0.1:8080/booking/getMyHistoryInfo',
      data: {
        user_id:getApp().globalData.userInfo.userId,
      },
      header: {
          'content-type': 'application/json'
      },
      success: function (res) {
          console.log(res.data);
          //console.log(res.data[0].bookDate);
          getApp().globalData.history = res.data
          //console.log(getApp().globalData.history[1]);
      }
  });
  },
  //跳转固定场申请界面
  fixcourt:function(){
    this.getMyHistoryInfo()
    var list=this.data.playlist
        wx.navigateTo({
            url: '/pages/fixcourt/fixcourt'
        })
  },
  //验证用户身份是否为球队用户
  checkproperty:function(){
    if(getApp().globalData.userInfo.property=="球队用户"){
      this.setData({
        team:true
      })
    }
  },
  
})
