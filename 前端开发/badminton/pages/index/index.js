var time = require('../../utils/util.js');
//获取应用实例
const app = getApp()

Page({
    data:{
        todaycourt:'',
        tcourt1:'',   //塑胶场地数组列表
        tcourt2:'',    //水泥场地数组列表   
        disabled:false ,
        history:'',
        booking:''
    },
    //确认框，跳转订场
    showDialog(e) {
        var that=this
        console.log(e.currentTarget.dataset)
        //根据场地号(courtid)和时间段号(timeid)查找当天场地号(todaycourtid)
        var timeid=e.currentTarget.dataset.timeid
        var courtid=e.currentTarget.dataset.courtid
        var todaycourt=getApp().globalData.todaycourt
        var todaycourtid=''
        for(var i=0;i<todaycourt.length;i++){
            if(timeid==todaycourt[i].timeId && courtid==todaycourt[i].courtId){
                todaycourtid=todaycourt[i].todayCourtId
            }
        }
        console.log(todaycourtid)
        wx.lin.showDialog({
            type: "confirm",
            title: "预定场",
            content: "您确定要预定这个场地吗？",
            success: (res) => {
                if (res.confirm) {
                    //调用后台函数把信息写入数据库
                    console.log('用户点击了确定')
                    that.setData({
                        disabled:true
                    })
                    that.bookTodayFreeCourt(todaycourtid)
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    },
    //下拉刷新
    onPullDownRefresh:function(){
        wx.showNavigationBarLoading() //在标题栏中显示加载
        //模拟加载
        setTimeout(function(){
        // complete
        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
        },1500);
        //this.getTodayCourt() 
        //this.getMyHistoryInfo()
        this.setdata()
        //this.checktime()
    
    },
    onLoad:function(){
        //this.checktime()
        this.getTodayCourt()  
        this.getMyHistoryInfo()
    },
    onShow:function(){
        this.setdata()
    },
    //判断当前时间是否在中午12点之前
    checktime:function(){
        var that =this
        //获取当前时间,判断是否在中午12点之前
        var dayTime = time.formatTime(new Date());
        var d=dayTime.split(" ")[1].split(":")   
        console.log(parseInt(d[0]))
        if(parseInt(d[0])<12){
            that.setData({
                disabled:true
            })
        }
    },
    //判断是否是游客，不能订场
    checktourist:function(){
        var property=getApp().globalData.userInfo.property
        if(property=='游客' ||property=='游客待审核'){
            this.setData({
                disabled:true
            })
            wx.lin.showDialog({
                type: "alert",
                title: "提醒",
                content: "完善好个人信息才能订场哦~",
                success: (res) => {
                    if (res.confirm) {
                        //调用后台函数把信息写入数据库
                        console.log('用户点击了确定')
                    }
                }
            })
        }
    },
    //获取当日订场信息
    getTodayCourt:function(){
        var that=this
        wx.request({
            url: 'http://127.0.0.1:8080/booking/getTodayCourtWithOtherInfo',
            data: {

            },
            header: {
                'content-type': 'application/json'
            },
            success: function (res) {
                console.log(res.data);
                getApp().globalData.todaycourt = res.data;
                that.changeDate()             
            }
        });
    },
    //将当日订场信息数据中的时间戳转为正常日期
    changeDate:function(){
        this.setData({
            todaycourt:getApp().globalData.todaycourt,
        })
        var data = this.data.todaycourt;
        for (var i = 0; i < data.length; i++) {      
            data[i].scheduledTimeTable1.starttime = time.formatTimeTwo(data[i].scheduledTimeTable1.starttime,'h:m') 
            data[i].scheduledTimeTable1.endtime = time.formatTimeTwo(data[i].scheduledTimeTable1.endtime,'h:m')     
        }
        this.setData({
            todaycourt: data
        })
        this.tdcourt()
    },
     //将todaycourt列表转为tcourt1，tcourt2两个列表
     tdcourt:function(){
        var todaycourt=getApp().globalData.todaycourt
        var tcourt1=[
            {   courtname:'塑胶一号场',   courtid:1,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'塑胶二号场',   courtid:2,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'塑胶三号场',   courtid:3,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'塑胶四号场',   courtid:4,   time1:1,   time2:2,   state1:'',   state2:''   }         
        ]
        var tcourt2=[
            {   courtname:'水泥五号场',   courtid:5,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'水泥六号场',   courtid:6,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'水泥七号场',   courtid:7,   time1:1,   time2:2,   state1:'',   state2:''   },
            {   courtname:'水泥八号场',   courtid:8,   time1:1,   time2:2,   state1:'',   state2:''   }
        ]
        for(var i=0;i<4;i++){        
            tcourt1[i].state1=todaycourt[i].courtState
            tcourt1[i].state2=todaycourt[i+8].courtState
            tcourt2[i].state1=todaycourt[i+4].courtState
            tcourt2[i].state2=todaycourt[i+12].courtState
        }
        console.log(tcourt1)
        console.log(tcourt2)
      
        this.setData({
            tcourt1:tcourt1,
            tcourt2:tcourt2,
        })
        this.checktime()
        this.checktourist()
         
        
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
            that.changehistoryDate()
        }
        });
    },
    //将历史信息数据中的时间戳转为正常日期
    changehistoryDate:function(){
        this.setData({
            history:getApp().globalData.history,
        })
        var data = this.data.history;
        for (var i = 0; i < data.length; i++) {      
            data[i].bookDate = time.formatTimeTwo(data[i].bookDate,'Y/M/D h:m:s')      
        }
        
        var dayTime = time.formatTime(new Date());
        var d=dayTime.split(" ")[0].split("/")   
        var book=''   
        for(var i=0;i<data.length;i++){
            var h=data[i].bookDate.split(" ")[0].split("/")
            //console.log(h)
            if(h[0]==d[0] && h[1]==d[1] && h[2]==d[2]){
                
                book=data[i]
                console.log(book)
                //that.book(book)
            }       
        }
        
        if(book!=''){
            if(book.bookState!=1){
                this.setData({
                    history: data,
                    booking:book,
                    disabled:true
                })
            }else{
                this.setData({
                    history: data,
                    booking:book,
                    disabled:false
                })
            }
        }else{
            this.setData({
                history: data,
                booking:book,
                disabled:false
            })
        }
        this.checktime()
        this.checktourist()
        
    },
    //预订当日空闲场地
    bookTodayFreeCourt:function(todaycourtid){
        var that=this
        wx.request({
            url: 'http://127.0.0.1:8080/booking/bookTodayFreeCourt',
            data: {
              user_id:getApp().globalData.userInfo.userId,
              todayCourt_id:todaycourtid
            },
            header: {
                'content-type': 'application/json'
            },
            success: function (res) {
                console.log(res.data);
                that.setdata()             
            }
        });
    },
    //重置数据
    setdata:function(){       
        this.getTodayCourt()
        this.getMyHistoryInfo()   
    },
    //退场事件
    exitcourt:function(e){
        var that=this
        var bookId=e.currentTarget.dataset.bookid
        wx.lin.showDialog({
            type: "confirm",
            title: "退场",
            content: "您确定要退掉这个场地吗？",
            success: (res) => {
                if (res.confirm) {
                    //调用后台函数把信息写入数据库
                    console.log('用户点击了确定')
                    that.toexitcourt(bookId)
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    },
    //跳转退场界面
    toexitcourt:function(bookId){
        wx.navigateTo({
            url: '/pages/exitcourt/exitcourt?bookid='+bookId
        })
    },
    //换场事件
    changecourt:function(e){
        var that=this
        var bookId=e.currentTarget.dataset.bookid
        console.log(bookId)
        var timeId=e.currentTarget.dataset.timeid
        var courtId=e.currentTarget.dataset.courtid
        var todaycourt=getApp().globalData.todaycourt
        var todaycourtid=''
        for(var i=0;i<todaycourt.length;i++){
            if(timeId==todaycourt[i].timeId && courtId==todaycourt[i].courtId){
                todaycourtid=todaycourt[i].todayCourtId
            }
        }
        wx.lin.showDialog({
            type: "confirm",
            title: "换场",
            content: "您确定要换掉这个场地吗？",
            success: (res) => {
                if (res.confirm) {
                    //调用后台函数把信息写入数据库
                    console.log('用户点击了确定')
                    that.tochangecourt(bookId,todaycourtid,timeId,courtId)
                    //that.applyChangeTodayCourt(bookId,todaycourtid)
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    },
    //跳转换场界面
    tochangecourt:function(bookId,todaycourtid,timeId,courtId){
        wx.navigateTo({
            url: '/pages/changecourt/changecourt?bookid='+bookId+"&todaycourid="+todaycourtid+"&timeid="+timeId+"&courtid="+courtId
        })
    },
    
})