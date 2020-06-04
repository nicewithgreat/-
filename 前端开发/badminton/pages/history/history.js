var time = require('../../utils/util.js');
Page({
  data:{
    history: '',
    //time:''
  },
  onLoad:function(){
    this.changeDate()
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
    this.setData({
      history: data
    })
  }
  
})