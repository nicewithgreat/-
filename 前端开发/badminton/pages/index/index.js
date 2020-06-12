var time = require('../../utils/util.js');
//获取应用实例
const app = getApp()

Page({
    data:{
        todaycourt:'',
        tcourt1:'',
        tcourt2:''
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
                    that.bookTodayFreeCourt(todaycourtid)
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    },
    
    onLoad:function(){
        this.getTodayCourt()
        
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
            tcourt2[i].state2=todaycourt[i+4].courtState
        }
        console.log(tcourt1)
        console.log(tcourt2)
        this.setData({
            tcourt1:tcourt1,
            tcourt2:tcourt2
        })

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
    },
    //获取当日订场信息
    getTodayCourt:function(){
        var that=this
        wx.request({
            url: 'http://127.0.0.1:8080/booking/getTodayCourtWithOtherInfo',
            data: {
            //user_id:'6',
            // wechatNO: username
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
})