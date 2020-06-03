Page({
  data:{
    history: ''
    
  },
  onLoad:function(){
    this.setData({
      history:getApp().globalData.history
    })
  }
  
})