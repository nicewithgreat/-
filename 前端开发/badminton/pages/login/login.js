Page({
    data: {
      phone: '',
      password:''
    },
   
  // 获取输入账号
    phoneInput :function (e) {
      this.setData({
        phone:e.detail.value
      })
    },
   
  // 获取输入密码
    passwordInput :function (e) {
      this.setData({
        password:e.detail.value
      })
    },
   
  // 登录
    login: function () {
      if(this.data.phone.length == 0 || this.data.password.length == 0){
        wx.showToast({  
          title: '用户名和密码不能为空',  
          icon: 'none',  
          duration: 2000  
        })  
  }else {
    // 这里修改成跳转的页面
        wx.showToast({  
          title: '登录成功',  
          icon: 'success',  
          duration: 2000  
        })  
        wx.switchTab({
            url: '/pages/index/index'
        })
      }  
    },
    //跳转到微信登录
    changewxlogin:function(){
        var list=this.data.playlist
        wx.navigateTo({
            url: '/pages/wxlogin/wxlogin'
        })
    }
  })