Page({
    data: {
      fixcourt:{
        day:'',       //时间周几
        time:'',      //时间段
        court:''     //场地号
      },
      disabled:false,
      disabledC:true
    },
    
    onLoad: function () {
      wx.lin.initValidateForm(this)
      this.getTeamFixedCourt()
    },  
    //获取球队有效固定场申请表
    getTeamFixedCourt:function(){
      var that=this
      var day=''
      var todaycourtid=''
      wx.request({
        url: 'http://127.0.0.1:8080/booking/getTeamFixedCourt',
        data: {
          user_id: getApp().globalData.userInfo.userId,
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          day=res.data.dayOfWeek
          todaycourtid=res.data.timeId
          that.setdata(day,todaycourtid)
          //getApp().globalData.userInfo = res.data;
        }
      });
     
    },
    //页面数据赋值
    setdata:function(Day,Todaycourtid){
      var that=this
      var day=Day
      var time=''
      var court=''
      var todaycourtid=Todaycourtid
      var todayCourt=getApp().globalData.todaycourt
      for(var n=0;n<todayCourt.length;n++){
        if(todaycourtid==todayCourt[n].todayCourtId){
          time=todayCourt[n].timeId
          court=todayCourt[n].courtId
        }
      }
      //console.log(day+","+time+","+court)
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
      //非空setdata
      if(day!='' && time!='' && court!=''){
        that.setData({
          fixcourt:{
            day:day,       //时间周几
            time:time,      //时间段
            court:court     //场地号
          },
          disabled:true,
          disabledC:false
        })
      }
      
    },
    //用户无有效固定场提示
    toast:function(e){   
      var that=this
      var day=e.currentTarget.dataset.day
      //var disabledC=e.currentTarget.dataset.disabledC
      //console.log(day)
      if(day==''){
        wx.lin.showToast({
        title: '请先申请有效固定场~',
        icon: 'error'
      })
      }
      /*
      else if(day!=''&&disabledC==true){
        wx.lin.showToast({
          title: '等待审核中~',
          icon: 'error'
        })
      }*/
      
    },
    //提交固定场申请事件
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
    //调用后台固定场申请函数
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
          today_court_id:today_court_id,
          day_of_week:day_of_week
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res.data);
          that.changedata()
          //getApp().globalData.userInfo = res.data;
        }
      });
    },
    /*数据赋值
    changedata:function(){
      this.setData({
        disabledC:true
      })
    }*/
  
  
    
  })