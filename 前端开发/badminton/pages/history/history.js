var time = require('../../utils/util.js');
Page({
  data:{
    history: ''
  },
  onLoad:function(){
    this.getMyHistoryInfo()
  },
  //将历史信息数据中的时间戳转为正常日期
  changeDate:function(){

    this.setData({
      history:getApp().globalData.history,
    })
    var data = this.data.history;
    for (var i = 0; i < data.length; i++) {      
        data[i].bookDate = time.formatTimeTwo(data[i].bookDate,'Y/M/D h:m:s')      
    }
    if(data!=''){
      data.reverse()
    }
    this.setData({
      history: data
    })
    
  },
  //获取用户历史订场信息
  getMyHistoryInfo: function () {
    var that=this
    wx.request({
      url: 'http://127.0.0.1:8080/booking/getMyHistoryInfo',
      data: {
        user_id: getApp().globalData.userInfo.userId,
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        getApp().globalData.history = res.data
        that.changeDate()
      }
    });
  },
  
})