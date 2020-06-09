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
    this.setData({
      user: {
        userName: getApp().globalData.userInfo.userName,
        identity_num: getApp().globalData.userInfo.identityNum,
        property: getApp().globalData.userInfo.property,
        college: ''
      }

    })
    this.checkproperty()
    this.getTeamsList()
  },

  //完善个人信息事件
  submit: function (e) {
    const {
      detail
    } = e;
    console.log(detail.values)
    var identity_num = detail.values.identity_num

    this.improvingInfo(identity_num)
    this.queryUsreInfo(getApp().globalData.userInfo.userName)
    this.setData({
      user: {
        userName: getApp().globalData.userInfo.userName,
        identity_num: getApp().globalData.userInfo.identityNum,
        property: getApp().globalData.userInfo.property,
        college: detail.values.college
      }
    })
    if (identity_num == '') {
      wx.lin.showToast({
        title: '学号不能为空~',
        icon: 'error'
      })
    } else {
      wx.lin.showToast({
        title: '修改成功~',
        icon: 'success'
      })
    }

  },
  //球队用户申请事件
  submitcollege: function (e) {
    const {
      detail
    } = e;
    console.log(detail.values)
    var college = detail.values.college
    if (college == '') {
      wx.lin.showToast({
        title: '学院不能为空~',
        icon: 'error'
      })
    } else {
      wx.lin.showToast({
        title: '提交成功，等待审核~',
        icon: 'success'
      })
    }
  },
  //调用后台提交用户信息函数
  improvingInfo: function (identityNum) {
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

      }
    });
  },
  //调用后台查看用户信息函数
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
  },
  //验证用户身份
  checkproperty: function () {
    if (getApp().globalData.userInfo.property == "普通用户" || getApp().globalData.userInfo.property == "球队用户") {
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
  },
  //验证是否球队用户身份提示信息
  toast: function () {
    if (getApp().globalData.userInfo.property == "球队用户") {
      wx.lin.showToast({
        title: '您已经是球队用户了！',
        icon: 'error'
      })
    } else if (getApp().globalData.userInfo.property != "球队用户" && getApp().globalData.userInfo.property != "普通用户") {
      wx.lin.showToast({
        title: '请您完善好个人信息再进行申请！',
        icon: 'error'
      })
    }
  },
  //调用后台获取学院球队信息函数
  getTeamsList: function () {
    wx.request({
      url: 'http://127.0.0.1:8080/booking/getTeamsList',
      data: {
        //user_id:'6',
        //wechatNO: username
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

})