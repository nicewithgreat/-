Page({
    data: {
      disabled:false,
      commit:true,
      time:'',
      court:'',
      bookid:'',
      back_todaycourtid:''
  
    },
    
    onLoad: function (options) {
      wx.lin.initValidateForm(this)
      var that = this;
      var courtname=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      for(var i=0;i<courtname.length;i++){
        if(options.courtid==i+1){
          that.setData({                             
            bookid: options.bookid,     
            back_todaycourtid: options.todaycourid,
            time:options.timeid,
            court:options.courtid,
            courtname:courtname[i]
          })
        }
      }
      
    }, 
    submit:function(e){
      var that=this
      const {detail} = e;
      console.log(detail.values)
      //判断是几号场，转为数字
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      var Court=''
      for(var j=0;j<courtid.length;j++){
        if(detail.values.court==courtid[j]){
          Court=j+1
        }
      }
      //判断todayCourtId
      var todayCourt=getApp().globalData.todaycourt
      var change_todaycourtid=''
      for(var n=0;n<todayCourt.length;n++){
        if(detail.values.time==todayCourt[n].timeId && Court==todayCourt[n].courtId){
          change_todaycourtid=todayCourt[n].todayCourtId
        }
      }
      var back_todaycourtid=that.data.back_todaycourtid
      var bookId=that.data.bookid
      that.applyChangeTodayCourt(bookId,back_todaycourtid,change_todaycourtid)
    },
    //调用后台换场函数
    applyChangeTodayCourt:function(bookId,back_todaycourtid,change_todaycourtid){
      var that=this
      wx.request({
          url: 'http://127.0.0.1:8080/booking/applyChangeTodayCourt',
          data: {
            user_id:getApp().globalData.userInfo.userId,
            book_id:bookId,
            back_todayCourtId:back_todaycourtid,
            change_todayCourtId:change_todaycourtid
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
                  wx.lin.showDialog({
                    type: "alert",
                    title: "换场",
                    content: "等待审核中~",
                    success: (res) => {
                        if (res.confirm) {
                            console.log('用户点击了确定1')
                        } 
                    }
                })
              }else if(res.data==3){
                  that.setData({
                    back_todaycourtid:change_todaycourtid
                  })
                  wx.lin.showDialog({
                      type: "alert",
                      title: "换场",
                      content: "换场成功~",
                      success: (res) => {
                          if (res.confirm) {
                              console.log('用户点击了确定3')
                          } 
                      }
                  })
              }else if(res.data==-1){
                  wx.lin.showDialog({
                      type: "alert",
                      title: "换场",
                      content: "现在时间超过了所订场的时间",
                      success: (res) => {
                          if (res.confirm) {
                              console.log('用户点击了确定-1')
                          } 
                      }
                  })
              }else{
                  wx.lin.showDialog({
                      type: "alert",
                      title: "换场",
                      content: "该场地可能已被他人预定~",
                      success: (res) => {
                          if (res.confirm) {
                              console.log('用户点击了确定-2')
                          } 
                      }
                  })
              }                      
          }
      });
    },
    setdata:function(){
      this.setdata({
        disabled:true,
        commit:false
      })
    }
  
    
  })