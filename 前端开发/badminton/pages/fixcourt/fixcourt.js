Page({
    data: {
      fixcourt:{
        day:'',       //时间周几
        time:'',      //时间段
        court:''     //场地号
      },
      
    },
    
    onLoad: function () {
      wx.lin.initValidateForm(this)
    
    },  
    submit:function(e){
      const {detail} = e;
      console.log(detail.values)
      //判断是周几，转为数字
      var dayOfweek=['周一','周二','周三','周四','周五','周六','周日']
      var day=''
      for(var i=0;i<dayOfweek.length;i++){
        if(detail.values.day==dayOfweek[i]){
            day=i+1
        }
      }
      //console.log(day)
      //判断是几号场，转为数字
      var courtid=['一号场','二号场','三号场','四号场','五号场','六号场','七号场','八号场']
      var court=''
      for(var j=0;j<courtid.length;j++){
        if(detail.values.court==courtid[j]){
          court=j+1
        }
      }
      //console.log(court)
      //判断todayCourtId
      var todayCourt=getApp().globalData.todaycourt
      var todayCourtId=''
      for(var n=0;n<todayCourt.length;n++){
        if(detail.values.time==todayCourt[n].timeId && court==todayCourt[n].courtId){
          todayCourtId=todayCourt[n].todayCourtId
        }
      }
      //console.log(todayCourtId)
      if(day!='' && court!='' && todayCourtId!=''&&detail.values.time!=''){
        this.applyFixedCourt(todayCourtId,day)
      }else{
        wx.lin.showToast({
          title: '请输入正确的时间或场地号或时间段',
          icon: 'error'
        })
      }
      
    },
    submitchange:function(e){
      const {detail} = e;
      console.log(detail.values)
      wx.lin.showMessage({
        type:'primary',
        content:'请输入正确的时间或场地号或时间段'
    })
    },
    applyFixedCourt:function(today_court_id,day_of_week){
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
          //getApp().globalData.userInfo = res.data;
        }
      });
    }
  
  
    
  })