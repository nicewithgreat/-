//index.js
//获取应用实例
const app = getApp()
// pages/plan/plan.js
Page({
    onLoad: function () {
        var that = this;
        wx.getSystemInfo({
            success: function (res) {
                console.log(res);
                that.setData({
                    section_Up_Height: res.windowHeight * 0.6,
                    section_Down_Height: res.windowHeight * 0.3
                })
            }
        })
    }
})