
var app = getApp()

Page({
  data: {
      //判断小程序的API，回调，参数，组件等是否在当前版本可用。
      canIUse: wx.canIUse('button.open-type.getUserInfo'),
      wechatNO:'',
      avatarUrl:''
  },
  onLoad: function () {
      var that = this;
      
      // 查看是否授权
      wx.getSetting({
          success: function (res) {
              if (res.authSetting['scope.userInfo']) {
                  wx.getUserInfo({
                      success: function (res) {
                        var userInfo = res.userInfo
                        var nickName = userInfo.nickName
                        //从数据库获取用户信息
                        that.queryUsreInfo(nickName);
                        //that.getTodayCourt()
                        //用户已经授权过
                        wx.switchTab({
                            url: '/pages/index/index',
                              
                        })
                      }
                  });
              }
          }
      })
  },
  bindGetUserInfo: function (e) {
      if (e.detail.userInfo) {
          //用户按了允许授权按钮
          var that = this;
          //插入登录的用户的相关信息到数据库
          wx.request({
              url: 'http://127.0.0.1:8080/booking/addUser',
              data: {
                  //openid: getApp().globalData.openid,
                  wechatNO: e.detail.userInfo.nickName,
                  //avatarUrl: e.detail.userInfo.avatarUrl,
                  //province:e.detail.userInfo.province,
                  //city: e.detail.userInfo.city
              },
              header: {
                  'content-type': 'application/json'
              },
              success: function (res) {
                  //从数据库获取用户信息
                  that.queryUsreInfo(e.detail.userInfo.nickName);
                  //that.getTodayCourt()
                  console.log(e.detail.userInfo);
                  
                  //app.globalData.wechatNO = e.detail.userInfo.nickName;
                  //app.globalData.avatarUrl = e.detail.userInfo.avatarUrl
              }
          });
          /*that.setData({
            wechatNO:e.detail.userInfo.nickName,
            avatarUrl: e.detail.userInfo.avatarUrl
           });*/
          
          //授权成功后，跳转进入小程序首页
          wx.switchTab({
              url: '/pages/index/index'
          })
      } else {
          //用户按了拒绝按钮
          wx.showModal({
              title:'警告',
              content:'您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
              showCancel:false,
              confirmText:'返回授权',
              success:function(res){
                  if (res.confirm) {
                      console.log('用户点击了“返回授权”')
                  } 
              }
          })
      }
  },
  //获取用户信息接口
  queryUsreInfo: function (username) {
      wx.request({
          url: 'http://127.0.0.1:8080/booking/getMyInfo',
          data: {
            //user_id:'6',
            wechatNO: username
          },
          header: {
              'content-type': 'application/json'
          },
          success: function (res) {
              console.log(res.data);
              getApp().globalData.userInfo = res.data;
          }
      });
  },
  /*获取当日订场信息
  getTodayCourt:function(){
    wx.request({
        url: 'http://127.0.0.1:8080/booking/getTodayCourtWithOtherInfo',
        data: {
          //user_id:'6',
         // wechatNO: username
        },
        header: {
            'content-type': 'application/json'
        },
        success: function (res) {
            console.log(res.data);
            getApp().globalData.todaycourt = res.data;
        }
    });
},*/
  
})