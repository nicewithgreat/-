Page({
    data: {
      user:{
        userName:'',
        identity_num:'',
        property:''       
      },
      disabled:false
    },
    onLoad: function () {
      wx.lin.initValidateForm(this)
      //this.queryUsreInfo()
      this.setData({
        user:{
          userName:getApp().globalData.userInfo.userName,
          identity_num:getApp().globalData.userInfo.identityNum,
          property:getApp().globalData.userInfo.property
        }
        
      })
      
    },
    submit:function(e){
      const {detail} = e;
      console.log(detail.values.identity_num)
      var identity_num=detail.values.identity_num
      getApp().globalData.userInfo.identityNum=detail.values.identity_num
      this.setData({ 
        user:{
          userName:getApp().globalData.userInfo.userName,
          identity_num:detail.values.identity_num,
          property:getApp().globalData.userInfo.property
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
      
    }
  
    
  })