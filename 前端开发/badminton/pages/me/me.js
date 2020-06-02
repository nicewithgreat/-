Page({
    data: {
      user:{
        userName:'',
        identity_num:'',
        property:'',
        college:''      
      },
      disabled:false,
      /*picker:{
        arr:['计算机与信息工程学院','外国语学院','2','3','4'],
        college:''
      }*/
    },
    /*picker选择
    pickerChange:function(e){
      this.setData({
        'picker.college':e.detail.value
      })
    },*/
    onLoad: function () {
      wx.lin.initValidateForm(this)
      //this.queryUsreInfo()
      this.setData({
        user:{
          userName:getApp().globalData.userInfo.userName,
          identity_num:getApp().globalData.userInfo.identityNum,
          property:getApp().globalData.userInfo.property,
          college:''
        }
        
      })
      
    },
    submit:function(e){
      const {detail} = e;
      console.log(detail.values)
      var identity_num=detail.values.identity_num
      //getApp().globalData.userInfo.identityNum=detail.values.identity_num
      
      this.improvingInfo(identity_num)
      this.queryUsreInfo(getApp().globalData.userInfo.userName)
      this.setData({ 
        user:{
          userName:getApp().globalData.userInfo.userName,
          identity_num:getApp().globalData.userInfo.identityNum,
          property:getApp().globalData.userInfo.property,
          college:detail.values.college
        }
      })
      if(identity_num==''){
        wx.lin.showToast({
          title: '学号不能为空~',
          icon: 'error'   
        })       
      }else{
        wx.lin.showToast({
        title: '修改成功~',
        icon: 'success'      
      })
      }
      
    },
    submitcollege:function(e){
      const {detail} = e;
      console.log(detail.values)
    },
    improvingInfo:function(identityNum){
      wx.request({
        url: 'http://127.0.0.1:8080/booking/improvingInfo',
        data: {
          user_id:getApp().globalData.userInfo.userId,
          wechatNO: getApp().globalData.userInfo.userName,
          identity_num:identityNum
        },
        header: {
            'content-type': 'application/json'
        },
        success: function (res) {
            console.log(res.data);
           
        }
    });
    },
    queryUsreInfo: function (username) {
      wx.request({
          url: 'http://127.0.0.1:8080/booking/getMyInfo',
          data: {
            //user_id:'6',
            wechatNO: username
          },
          header: {
              'content-type': 'application/json'
          },
          success: function (res) {
              console.log(res.data);
              getApp().globalData.userInfo = res.data;
          }
      });
  }
  
    
  })