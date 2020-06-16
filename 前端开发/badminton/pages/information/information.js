//index.js
//获取应用实例
var app = getApp()

Page({
  data: {
    
    wechatNO: '',
    team: false
  },
  onLoad: function () {
    this.getTeamsList()
    
  },
  //跳转查看个人信息界面
  me: function () {
    var list = this.data.playlist
    wx.navigateTo({
      url: '/pages/me/me'
    })
  },
  //跳转查看历史订场信息界面
  history: function () {
    //this.getMyHistoryInfo()
    var list = this.data.playlist
    wx.navigateTo({
      url: '/pages/history/history'
    })
  },
  //跳转固定场申请界面
  fixcourt: function () {
    var property=getApp().globalData.userInfo.property
    if(property=='球队用户'){
      var list = this.data.playlist
      wx.navigateTo({
        url: '/pages/fixcourt/fixcourt'
      })
    }else{
      wx.lin.showMessage({
        type:'error',
        duration:2500,
        content:'仅球队用户可进行申请哦~'
      })
    }
    
  },
  //帮助对话框
  showDialog() {
    wx.lin.showDialog({
      type: "alert",
      title: "韩师校园订场小程序",
      content: "版本：V1.0.0    开发：俊人团队",
      success: (res) => {
        if (res.confirm) {
          console.log('用户点击了确定')
        } 
      }
    })
  },
  //调用后台获取学院球队信息函数
  getTeamsList: function () {
    wx.request({
      url: 'http://127.0.0.1:8080/booking/getTeamsList',
      data: {
        
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        getApp().globalData.teamList = res.data;
      }
    });
  },
})