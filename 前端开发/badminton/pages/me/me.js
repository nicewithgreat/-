Page({
  data: {
    user: {
      userName: '',
      identity_num: '',
      property: '',
      college: ''
    },
    disabled: false,
    check: true,
    team: false

  },

  onLoad: function () {
    wx.lin.initValidateForm(this)
  
    this.queryUsreInfo()
  },
  onShow:function(){
   
  },

  //完善个人信息事件
  submit: function (e) {
    const {
      detail
    } = e;
    var identity_num = detail.values.identity_num
    console.log(identity_num)
    if (identity_num == ''||identity_num == 0) {
          wx.lin.showMessage({
            type:'error',
            content:'学号不能为空~'
          })
      } else {
        this.improvingInfo(identity_num)
        wx.lin.showMessage({
          type:'success',
          content:'提交成功,等待审核~'
        })
      }
    

  },
  
  //调用后台提交用户信息函数
  improvingInfo: function (identityNum) {
    var that=this
    wx.request({
      url: 'http://127.0.0.1:8080/booking/improvingInfo',
      data: {
        user_id: getApp().globalData.userInfo.userId,
        wechatNO: getApp().globalData.userInfo.userName,
        identity_num: identityNum
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.queryUsreInfo();       
      }
    });
    that.onLoad()
  },
  //调用后台查看用户信息函数
  queryUsreInfo: function () {
    var that=this
    wx.request({
      url: 'http://127.0.0.1:8080/booking/getMyInfo',
      data: {
        wechatNO: getApp().globalData.userInfo.userName
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        getApp().globalData.userInfo = res.data;
        that.getcollege()
      }
    });
  },
  //球队用户申请事件
  submitcollege: function (e) {
    const {
      detail
    } = e;
    console.log(detail.values)
    var college = detail.values.college
    var teamList=getApp().globalData.teamList
    var teamId=''
    if (college == '') {
      wx.lin.showToast({
        title: '学院不能为空~',
        icon: 'error'
      })
    } else{
      for(var i=0;i<teamList.length;i++){
        if(teamList[i].college==college){
          teamId=teamList[i].teamId
        }
      }
      if(teamId==''){
        wx.lin.showToast({
          title: '请输入正确且完整的学院名称~',
          icon: 'error'
        })
      }else{
        this.improvingTeamInfo(teamId)
        wx.lin.showToast({
          title: '提交成功，等待审核~',
          icon: 'success'
        })
      }
    }
    
    
  },
  //调用后台提交球队用户申请信息函数
  improvingTeamInfo:function(teamId){
    var that=this
    wx.request({
      url: 'http://127.0.0.1:8080/booking/improvingTeamInfo',
      data: {
        user_id: getApp().globalData.userInfo.userId,
        wechatNO: getApp().globalData.userInfo.userName,
        team_id: teamId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.queryUsreInfo();
        
      }
    });
    
  },
  //验证用户身份
  checkproperty: function () {
    if (getApp().globalData.userInfo.property == "普通用户" ||getApp().globalData.userInfo.property == "普通用户待审核"|| getApp().globalData.userInfo.property == "球队用户" ||getApp().globalData.userInfo.property == "游客待审核") {
      this.setData({
        check: false,
        disabled: true
      })
    }
    if (getApp().globalData.userInfo.property == "普通用户") {
      this.setData({
        team: true
      })
    }
    if(getApp().globalData.userInfo.property == "普通用户待审核"){
      this.setData({
        team: false
      })
    }
    
  },
  //验证是否球队用户身份提示信息
  toast: function () {
    if(getApp().globalData.userInfo.property == "普通用户待审核") {
      wx.lin.showToast({
        title: '等待审核中~',
        icon: 'error'
      })
    }else if (getApp().globalData.userInfo.property == "游客" || getApp().globalData.userInfo.property == "游客待审核") {
      wx.lin.showToast({
        title: '请您完善好个人信息再进行申请！',
        icon: 'error'
      })
    }
  }, 
  //获取球队用户所属学院
  getcollege:function(){
    var teamList=getApp().globalData.teamList
    var college=''
    for(var i=0;i<teamList.length;i++){
      if(teamList[i].teamId==getApp().globalData.userInfo.teamId){
        college=teamList[i].college
      }
    }
    this.setData({
      user:{
        userName: getApp().globalData.userInfo.userName,
        identity_num: getApp().globalData.userInfo.identityNum,
        property: getApp().globalData.userInfo.property,
        college:college
      }
    })
    this.checkproperty()
  }

})