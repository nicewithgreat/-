var time = require('../../utils/util.js');
//获取应用实例
const app = getApp()

Page({
    data:{
        todaycourt:''
    },
    showDialog() {
        wx.lin.showDialog({
            type: "confirm",
            title: "预定场",
            content: "您确定要预定这个场地吗？",
            success: (res) => {
                if (res.confirm) {
                    //调用后台函数把信息写入数据库
                    console.log('用户点击了确定')
                    this.bookTodayCourt()
                } else if (res.cancel) {
                    console.log('用户点击了取消')
                }
            }
        })
    },
    onLoad:function(){
        this.changeDate()
    },

    //当日订场写入数据库
    bookTodayCourt: function () {
        
        
    },
    /*获取当日订场信息   改在wxlogin.js调用获取
    getTodayCourt:function(){
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
                console.log(getApp().globalData.todaycourt);
            }
        });
    },*/
    //将当日订场信息数据中的时间戳转为正常日期
    changeDate:function(){
        //this.getTodayCourt()
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
    },
    //预订当日空闲场地
    bookTodayFreeCourt:function(){
        wx.request({
            url: 'http://127.0.0.1:8080/booking/bookTodayFreeCourt',
            data: {
              user_id:getApp().globalData.userInfo.userId,
              item:getApp().globalData.todaycourt[0]
            },
            header: {
                'content-type': 'application/json'
            },
            success: function (res) {
                console.log(res.data);
                //getApp().globalData.todaycourt = res.data;
                //console.log(getApp().globalData.todaycourt);
            }
        });
    },

})