Page({
    data: {
      disabled:false,
      commit:true,
      bookid:''
    },
    onLoad: function (options) {
      wx.lin.initValidateForm(this)
      var that = this;
      that.setData({
        bookid:options.bookid,
      })
      
    }, 
    submit:function(e){
      var that=this
      const {detail} = e;
      console.log(detail.values)
      var bookId=that.data.bookid
      var reason=detail.values.reason
      if(reason!=''){
        wx.lin.showDialog({
          type: "confirm",
          title: "退场",
          content: "您确定要退掉这个场地吗？",
          success: (res) => {
              if (res.confirm) {
                  //调用后台函数把信息写入数据库
                  console.log('用户点击了确定')
                  //等待0.5秒后跳转，防止提示框同时出现
                  setTimeout(function () {                    
                    that.applyUnsubscribeTodayCourt(bookId,reason)
                  }, 500)
                  
              } else if (res.cancel) {
                  console.log('用户点击了取消')
              }
          }
        })      
      }else{
        wx.lin.showDialog({
          type: "alert",
          title: "退场",
          content: "退场原因不能为空！",
          success: (res) => {
              if (res.confirm) {
                  console.log('用户点击了确定')
              } 
          }
      })
      }
    },
    //调用后台退场函数
    applyUnsubscribeTodayCourt:function(bookId,reason){
      var that=this
      wx.request({
          url: 'http://127.0.0.1:8080/booking/applyUnsubscribeTodayCourt',
          data: {
            user_id:getApp().globalData.userInfo.userId,
            book_id:bookId,
            reason:reason
          },
          header: {
              'content-type': 'application/json'
          },
          success: function (res) {
              console.log(res.data);
              if(res.data==1){
                that.setData({
                  disabled:true,
                  commit:false              
                })
                wx.lin.showToast({
                  title: '提交成功，等待审核~',
                  icon: 'success'
                })
              }else if(res.data==-1){
                  wx.lin.showToast({
                    title: '请提前订场时间段两个小时退场',
                    duration:2000,
                    icon: 'error'
                  })
              }else{
                wx.lin.showToast({
                  title: '请求出错',
                  icon: 'error'
                })
              }
                         
          }
      });
  },
  
    
  })