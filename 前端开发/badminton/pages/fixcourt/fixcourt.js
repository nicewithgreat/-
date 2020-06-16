Page({
    data: {
      applyfixcourt:{
        day:'',       //时间周几
        time:'',      //时间段
        court:''     //场地号      
      },
      changefixcourt:{
        day:'',       //时间周几
        time:'',      //时间段
        court:''     //场地号
      },
      state:'',     //申请状态
      disabled:false,    //申请输入框是否禁用
      disabledC:true,     //更换输入框是否禁用
      exit:false,
      applyFixedId:''
    },
    
    onLoad: function () {
      wx.lin.initValidateForm(this)
      this.ApplyFixedCourtListByUser()
      //this.ApplyChangeFixedCourtList()
    },  
    onShow:function(){
      this.ApplyFixedCourtListByUser()
    },
    //获取球队有效固定场申请表
    ApplyFixedCourtListByUser:function(){
      var that=this
      wx.request({
        url: 'http://127.0.0.1:8080/booking/ApplyFixedCourtListByUser',
        data: {
          user_id: getApp().globalData.userInfo.userId,
          state:-2
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          var applyFixed=res.data
          if(applyFixed!=''){            
            that.setdata(applyFixed)
          }      
        }
      });
     
    },
    //有有效固定场申请记录页面数据赋值
    setdata:function(applyfixed){
      console.log(applyfixed)
      var that=this
     // var applyfixed=applyFixed
      var day=applyfixed[0].dayOfWeek
      var applyFixedid=applyfixed[0].applyFixedId
      var todaycourtid=applyfixed[0].timeId
      var applystate=applyfixed[0].applyState
      var time=''
      var court=''   
      //根据todaycourtid找时间段和场地号
      var todayCourt=getApp().globalData.todaycourt
      for(var n=0;n<todayCourt.length;n++){
        if(todaycourtid==todayCourt[n].todayCourtId){
          time=todayCourt[n].timeId
          court=todayCourt[n].courtId
        }
      }
      console.log(day+","+time+","+court)
      //判断是周几
      var dayOfweek=['周一','周二','周三','周四','周五','周六','周日']
      for(var i=0;i<dayOfweek.length;i++){
        if(day==i+1){
            day=dayOfweek[i]
        }
      }
      //判断是几号场
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      for(var j=0;j<courtid.length;j++){
        if(court==j+1){
          court=courtid[j]
        }
      } 
      if(applystate==0){
        that.setData({
          applyfixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court     //场地号
          },
          disabled:true,
          disabledC:true,
          state:'申请待审核',
          applyFixedId:applyFixedid
        })
      }else if(applystate==1){
        that.setData({
          applyfixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court     //场地号
          },
          changefixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court
          },
          disabled:true,
          disabledC:false,
          state:'已申请成功',
          exit:true,
          applyFixedId:applyFixedid
        })
      }else if(applystate==3){
        that.setData({
          applyfixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court     //场地号
          },
          disabled:true,
          disabledC:true,
          state:'换场待审核',
          applyFixedId:applyFixedid
        })
        that.ApplyChangeFixedCourtList(applyFixedid)
      }else if(applystate==4){
        that.setData({
          applyfixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court     //场地号
          },
          changefixcourt:{
            day:'',       //时间周几
            time:'',      //时间段
            court:''     //场地号
          },
          disabled:true,
          disabledC:true,
          state:'退场待审核',
          applyFixedId:applyFixedid,
          exit:false
        })
      }             
    },
    //用户无有效固定场提示
    toast:function(e){   
      var that=this
      var state=e.currentTarget.dataset.state
      if(state=='申请待审核'){
        wx.lin.showToast({
        title: '请先申请有效固定场~',
        icon: 'error'
        })
      }else if(state=='退场待审核'){
        wx.lin.showToast({
          title: '退场审核中,不能换场~',
          icon: 'error'
        })
      }
    },
    //获取申请固定场更换列表
    ApplyChangeFixedCourtList:function(bookId){
      var that=this
      var ApplyChangeFixedCourt=''
      wx.request({
        url: 'http://127.0.0.1:8080/booking/ApplyChangeFixedCourtByUser',
        data: {
          user_id:getApp().globalData.userInfo.userId,
          book_id:bookId
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          ApplyChangeFixedCourt=res.data
          if(ApplyChangeFixedCourt!=''){
            that.setchangedata(ApplyChangeFixedCourt)
          }
        }
      });
     
    },
    //有换场记录页面赋值
    setchangedata:function(ApplyChangeFixedCourt){
      var that=this
      var applyChangeFixedCourt=ApplyChangeFixedCourt
      for(var i=0;i<applyChangeFixedCourt.length;i++){
        if(applyChangeFixedCourt[i].applyState==3){
          var applystate=applyChangeFixedCourt[i].applyState   //订场状态
          var todaycourtid=applyChangeFixedCourt[i].courtId   //场地编号1~16
          var dayid=applyChangeFixedCourt[i].timeId     //时间周几
          var applyFixedid=applyChangeFixedCourt[i].applyFixedId
        }        
      }
      
      var time=''       //时间段号  
      var court=''      //几号场地

      //判断是周几
      var dayOfweek=['周一','周二','周三','周四','周五','周六','周日']
      for(var i=0;i<dayOfweek.length;i++){
        if(dayid==i+1){
            dayid=dayOfweek[i]
        }
      }
      //判断时间段号、场地号
      var todayCourt=getApp().globalData.todaycourt
      for(var n=0;n<todayCourt.length;n++){
        if(todaycourtid==todayCourt[n].todayCourtId){
          time=todayCourt[n].timeId
          court=todayCourt[n].courtId
        }
      }
      //判断是几号场
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      for(var j=0;j<courtid.length;j++){
        if(court==j+1){
          court=courtid[j]
        }
      }
        
      that.setData({
        changefixcourt:{
          day:dayid,       //时间周几
          time:time,      //时间段
          court:court     //场地号
        },
          disabled:true,
          disabledC:true,
          state:'换场待审核',
          applyFixedId:applyFixedid,
          exit:false
      })
      
      
      
    },
    //提交固定场申请事件
    submit:function(e){
      const {detail} = e;
      console.log(detail.values)
      //判断是周几，转为数字
      var dayOfweek=['周一','周二','周三','周四','周五','周六','周日']
      var day=''
      var dayname=''
      for(var i=0;i<dayOfweek.length;i++){
        if(detail.values.day==dayOfweek[i]){
            day=i+1
            dayname=dayOfweek[i]
        }
      }
      //console.log(day)
      //判断是几号场，转为数字
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      var court=''
      var courtname=''
      for(var j=0;j<courtid.length;j++){
        if(detail.values.court==courtid[j]){
          court=j+1
          courtname=courtid[j]
        }
      }
      //console.log(court)
      //判断todayCourtId
      var todayCourt=getApp().globalData.todaycourt
      var todayCourtId=''
      var time=detail.values.time
      for(var n=0;n<todayCourt.length;n++){
        if(time==todayCourt[n].timeId && court==todayCourt[n].courtId){
          todayCourtId=todayCourt[n].todayCourtId
        }
      }
      //console.log(todayCourtId)
      if(day!='' && court!='' && todayCourtId!=''&&time!=''){
        this.applyFixedCourt(todayCourtId,day,dayname,courtname,time)
      }else{
        wx.lin.showDialog({
          type: "alert",
          title: "固定场申请",
          content: "请输入正确的时间，时间段，场地号~",
          success: (res) => {
              if (res.confirm) {
                  console.log('用户点击了确定')
              } 
          }
        })
      }
      
    },
    //调用后台固定场申请函数
    applyFixedCourt:function(today_court_id,day_of_week,dayname,courtname,time){
      var that=this
      wx.request({
        url: 'http://127.0.0.1:8080/booking/applyFixedCourt',
        data: {
          user_id: getApp().globalData.userInfo.userId,
          today_court_id:today_court_id,
          day_of_week:day_of_week
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          if(res.data!=-1){
            wx.lin.showDialog({
              type: "alert",
              title: "固定场申请",
              content: "提交成功，等待审核~",
              success: (res) => {
                  if (res.confirm) {
                      console.log('用户点击了确定')
                  } 
              }
            })
            that.setData({
              fixcourt:{
                day:dayname,       //时间周几
                time:time,      //时间段
                court:courtname     //场地号
              },
              disabled:true,
              state:'申请待审核'
            })
          }else{
            wx.lin.showDialog({
              type: "alert",
              title: "固定场申请",
              content: "您已有固定场申请在审核中~",
              success: (res) => {
                  if (res.confirm) {
                      console.log('用户点击了确定')
                  } 
              }
            })
          }
          
        }
      });
    },
    //提交固定场更换申请事件
    submitchange:function(e){
      const {detail} = e;
      console.log(detail.values)
      //判断是周几，转为数字
      var dayOfweek=['周一','周二','周三','周四','周五','周六','周日']
      var day=''
      for(var i=0;i<dayOfweek.length;i++){
        if(detail.values.day1==dayOfweek[i]){
            day=i+1
        }
      }
      //console.log(day)
      //判断是几号场，转为数字
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      var court=''
      for(var j=0;j<courtid.length;j++){
        if(detail.values.court1==courtid[j]){
          court=j+1
        }
      }
      //console.log(court)
      //判断todayCourtId
      var todayCourt=getApp().globalData.todaycourt
      var todayCourtId=''
      for(var n=0;n<todayCourt.length;n++){
        if(detail.values.time1==todayCourt[n].timeId && court==todayCourt[n].courtId){
          todayCourtId=todayCourt[n].todayCourtId
        }
      }
      //console.log(todayCourtId)
      if(day!='' && court!='' && todayCourtId!=''&&detail.values.time1!=''){
        this.applyChangeFixedCourt(todayCourtId,day)
      }else{
        wx.lin.showToast({
          title: '请输入正确的时间或场地号或时间段',
          icon: 'error'
        })
      }
    },
    //调用后台固定场更换函数
    applyChangeFixedCourt:function(today_court_id,day_of_week){
      var that=this
      wx.request({
        url: 'http://127.0.0.1:8080/booking/applyChangeFixedCourt',
        data: {
          user_id: getApp().globalData.userInfo.userId,
          change_fixedCourt:that.data.applyFixedId,
          today_court_id:today_court_id,
          day_of_week:day_of_week
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          if(res.data==1){
            wx.lin.showToast({
              title: '提交申请成功~',
              icon: 'success'
            })
            that.ApplyChangeFixedCourtList(that.data.applyFixedId)
          }else{
            wx.lin.showToast({
              title: '提交申请失败~',
              icon: 'error'
            })
          }
         
        }
      });
    }, 
    //退还固定场事件
    exitcourt:function(){
      console.log('退')
      var that=this
      wx.lin.showDialog({
        type: "confirm",
        title: "退还固定场场",
        content: "您确定要退掉这个固定场吗？",
        success: (res) => {
            if (res.confirm) {
                //调用后台函数把信息写入数据库
                console.log('用户点击了确定')                                 
                that.toexitfixcourt(that.data.applyFixedId)               
            } else if (res.cancel) {
                console.log('用户点击了取消')
            }
        }
      })      
    },
    toexitfixcourt:function(applyFixedId){
      wx.navigateTo({
        url: '/pages/exitfixcourt/exitfixcourt?applyFixedId='+applyFixedId,
      })
    }
  })